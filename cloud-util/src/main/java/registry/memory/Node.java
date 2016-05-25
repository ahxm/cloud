package registry.memory;

import registry.util.EventManager;
import registry.util.PathUtil;

import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by anzhen on 2016/5/25.
 */
public class Node {

    //名称
    private String name;
    //完整路径
    private String path;
    //数据
    private byte[] data;
    //父节点
    private Node parent;
    //孩子节点
    private ConcurrentNavigableMap<String, Node> children = new ConcurrentSkipListMap();
    //通知器
    private EventManager<NodeEvent> notifier;
    //计数器
    protected AtomicLong counter=new AtomicLong(0);

    public Node(EventManager<NodeEvent> notifier) {
        this(null, null, null, notifier);
    }

    public Node(String name, byte[] data, EventManager<NodeEvent> notifier) {
        this(name, data, null, notifier);
    }

    public Node(String name, byte[] data, Node parent, EventManager<NodeEvent> notifier) {
        this.name = name;
        this.data = data;
        this.notifier = notifier;
        if (parent != null) {
            this.parent = parent;
            this.parent.addChild(this);
        } else {
            this.path = PathUtil.makePath("/", name);
        }
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    protected void setData(byte[] data) {
        this.data = data;
        notify(new NodeEvent(NodeEvent.NodeEventType.UPDATE, parent, this));
    }

    public String getPath() {
        return path;
    }

    public Node getParent() {
        return parent;
    }

    /**
     * 获取孩子节点列表
     *
     * @return 孩子节点列表
     */
    public List<Node> getChildren() {
        //复制一份
        List<Node> result = new ArrayList<Node>();
        result.addAll(children.values());
        return result;
    }

    /**
     * 孩子节点的数量
     *
     * @return 孩子节点的数量
     */
    public int size() {
        return children.size();
    }

    /**
     * 通知消息
     *
     * @param event 事件
     */
    protected void notify(final NodeEvent event) {
        if (event == null || notifier == null) {
            return;
        }
        notifier.add(event);
    }

    /**
     * 获取孩子节点
     *
     * @param child 节点路径
     * @return 孩子节点
     */
    public Node getChild(final String child) {
        if (child == null || child.isEmpty()) {
            return null;
        }
        return children.get(child);
    }

    /**
     * 添加子节点
     *
     * @param child 子节点
     */
    protected void addChild(final Node child) {
        if (child == null || child.getName() == null || child.getName().isEmpty()) {
            throw new IllegalArgumentException("child is null or it's name is empty");
        }
        if (child.parent != this) {
            child.parent = this;
            child.path = PathUtil.makePath(this.path, child.name);
        }
        if (children.putIfAbsent(child.getName(), child) == null) {
            notify(new NodeEvent(NodeEvent.NodeEventType.ADD, this, child));
        }
    }

    /**
     * 删除子节点
     *
     * @param child 子节点
     * @return 删除的节点
     */
    protected Node removeChild(final String child) {
        if (child == null || child.isEmpty()) {
            return null;
        }
        Node node = children.remove(child);
        if (node != null) {
            node.parent = null;
            notify(new NodeEvent(NodeEvent.NodeEventType.DELETE, this, node));
        }
        return node;
    }

    /**
     * 删除子节点
     *
     * @param child 子节点
     * @return 删除的节点
     */
    protected Node removeChild(Node child) {
        if (child == null) {
            return null;
        }
        return removeChild(child.getName());
    }

    /**
     * 获取子节点
     *
     * @param path 全路径
     * @return 子节点
     */
    public Node getDescendant(final String path) {
        PathUtil.validatePath(path);
        List<String> nodes = PathUtil.getNodes(path);
        Node parent = this;
        Node child = parent;
        for (String node : nodes) {
            child = parent.getChild(node);
            if (child == null) {
                break;
            } else {
                parent = child;
            }
        }
        return child;
    }

    /**
     * 递归创建子节点
     *
     * @param path 路径
     * @param data 数据
     * @return 子节点
     */
    protected Node createDescendant(final String path, final byte[] data) {
        PathUtil.validatePath(path);
        List<String> nodes = PathUtil.getNodes(path);
        boolean created = false;
        Node parent = this;
        Node child = parent;
        for (String node : nodes) {
            child = parent.getChild(node);
            if (child == null) {
                created = true;
                child = new Node(node, null, notifier);
                parent.addChild(child);
            }
            parent = child;
        }
        if (!created) {
            child.setData(data);
        } else {
            child.data = data;
        }
        return child;
    }

    /**
     * 递归删除子节点
     *
     * @param path 路径
     * @return 子节点
     */
    protected Node removeDescendant(final String path) {
        PathUtil.validatePath(path);
        List<String> nodes = PathUtil.getNodes(path);
        Node parent = this;
        Node child = parent;
        String node;
        for (int i = 0; i < nodes.size(); i++) {
            node = nodes.get(i);
            if (i == nodes.size() - 1) {
                child = parent.removeChild(node);
            } else {
                child = parent.getChild(node);
            }
            if (child != null) {
                parent = child;
            } else {
                break;
            }
        }
        return child;
    }

    /**
     * 递归删除所有子节点
     */
    protected void removeDescendants() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(this);
        Node node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.children != null && !node.children.isEmpty()) {
                for (Node child : node.children.values()) {
                    queue.add(child);
                }
                node.children.clear();
                notify(new NodeEvent(NodeEvent.NodeEventType.DELETE, node.parent, node));
            }
            node.parent = null;
        }
    }

    /**
     * 返回所有的路径集合
     *
     * @return 路径集合
     */
    public List<String> getPaths() {
        List<String> result = new ArrayList<String>();
        Stack<Node> stack = new Stack<Node>();
        stack.add(this);
        Node node;
        while (!stack.isEmpty()) {
            node = stack.pop();
            result.add(node.getPath());
            if (node.children != null && !node.children.isEmpty()) {
                // 孩子节点
                Collection<Node> nodes = node.children.values();
                // 倒序入堆栈
                Node[] childs = nodes.toArray(new Node[nodes.size()]);
                for (int i = childs.length - 1; i >= 0; i--) {
                    stack.push(childs[i]);
                }
            }
        }
        return result;
    }
}
