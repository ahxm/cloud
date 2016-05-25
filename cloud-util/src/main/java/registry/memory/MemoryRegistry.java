package registry.memory;

import registry.listener.*;
import registry.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by anzhen on 2016/5/25.
 */
public class MemoryRegistry implements Registry {

    public static final String MEMBER = "member-";
    //URL
    protected URL url;
    //节点事件通知器
    protected EventManager<NodeEvent> nodeEventManager = new EventManager<NodeEvent>(new NodeEventListener());
    //路径事件通知器
    protected EventManager<PathEvent> pathEventManager = new EventManager<PathEvent>();
    //子节点事件通知器
    protected EventManager<ChildrenEvent> childrenEventManager = new EventManager<ChildrenEvent>();
    //选举事件通知器
    protected EventManager<LeaderEvent> leaderEventManager = new EventManager<LeaderEvent>();
    //集群事件通知器
    protected EventManager<ClusterEvent> clusterEventManager = new EventManager<ClusterEvent>();
    //根节点
    protected Node root = new Node(nodeEventManager);
    //状态
    protected Status status = Status.CLOSED;
    //选举节点
    protected ConcurrentMap<String, String> leaders = new ConcurrentHashMap<String, String>();
    //子节点变化监听器
    protected ConcurrentMap<String, ConcurrentMap<ChildrenListener, Node>> childrenListeners =
            new ConcurrentHashMap<String, ConcurrentMap<ChildrenListener, Node>>();
    //子节点数据变化监听器
    protected ConcurrentMap<String, ConcurrentMap<ChildrenDataListener, Node>> childrenDataListeners =
            new ConcurrentHashMap<String, ConcurrentMap<ChildrenDataListener, Node>>();
    //节点变化监听器
    protected ConcurrentMap<String, ConcurrentMap<PathListener, Node>> pathListeners =
            new ConcurrentHashMap<String, ConcurrentMap<PathListener, Node>>();
    //选举监听器
    protected ConcurrentMap<String, ConcurrentMap<LeaderListener, Node>> leaderListeners =
            new ConcurrentHashMap<String, ConcurrentMap<LeaderListener, Node>>();
    //集群监听器
    protected ConcurrentMap<String, ConcurrentMap<ClusterListener, Node>> clusterListeners =
            new ConcurrentHashMap<String, ConcurrentMap<ClusterListener, Node>>();
    //连接监听器
    protected TreeSet<ConnectionListener> connectionListeners = new TreeSet<ConnectionListener>();
    //锁
    protected ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MemoryRegistry() {

    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public void open() throws RegistryException {
        lock.writeLock().lock();
        try {
            if (status == Status.OPENED) {
                return;
            }
            status = Status.OPENED;
            nodeEventManager.start();
            pathEventManager.start();
            childrenEventManager.start();
            leaderEventManager.start();
            clusterEventManager.start();
            doOpen();
        } finally {
            lock.writeLock().unlock();
        }
    }

    protected void doOpen() throws RegistryException {

    }

    @Override
    public boolean isConnected() {
        lock.readLock().lock();
        try {
            return status == Status.OPENED;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void close() throws RegistryException {
        lock.writeLock().lock();
        try {
            status = Status.CLOSING;
            nodeEventManager.stop();
            pathEventManager.stop();
            childrenEventManager.stop();
            leaderEventManager.stop();
            clusterEventManager.stop();
            //不再产生通知事件
            root.removeDescendants();
            status = Status.CLOSED;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 检查状态
     */
    protected void checkState() {
        if (status != Status.OPENED) {
            throw new IllegalStateException("registry has not been opened yet!");
        }
    }

    @Override
    public void create(final String path, final byte[] data) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            root.createDescendant(path, data);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void create(final List<String> paths) throws RegistryException {
        if (paths == null || paths.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            checkState();
            for (String path : paths) {
                root.createDescendant(path, null);
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void createLive(final String path, final byte[] data) {
        lock.readLock().lock();
        try {
            checkState();
            root.createDescendant(path, data);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void deleteLive(final String path) {
        lock.readLock().lock();
        try {
            checkState();
            root.removeDescendant(path);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void update(final String path, final byte[] data) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            root.createDescendant(path, data);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void update(PathData data) throws RegistryException {
        if (data == null) {
            return;
        }
        update(data.getPath(), data.getData());
    }

    @Override
    public void update(final String path, final byte[] data, final byte[] parent) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            Node node = root.createDescendant(path, data);
            if (node.getParent() != root) {
                node.setData(parent);
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void delete(final String path) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            root.removeDescendant(path);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void delete(final List<String> paths) throws RegistryException {
        if (paths == null || paths.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            checkState();
            for (String path : paths) {
                root.removeDescendant(path);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean exists(final String path) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            Node node = root.getDescendant(path);
            return node != null;
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public boolean isLeader(final String path) throws RegistryException {
        return leaders.containsKey(path);
    }

    @Override
    public PathData getData(final String path) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            Node node = root.getDescendant(path);
            if (node != null) {
                return new PathData(node.getName(), node.getData());
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public List<PathData> getChildData(final String path) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            List<PathData> result = new ArrayList<PathData>();
            Node node = root.getDescendant(path);
            if (node != null) {
                for (Node child : node.getChildren()) {
                    result.add(new PathData(child.getName(), child.getData()));
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public List<String> getChildren(final String path) throws RegistryException {
        lock.readLock().lock();
        try {
            checkState();
            List<String> result = new ArrayList<String>();
            Node node = root.getDescendant(path);
            if (node != null) {
                for (Node child : node.getChildren()) {
                    result.add(child.getName());
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 删除节点事件监听器
     *
     * @param listeners 监听器集合
     * @param path      路径
     * @param listener  监听器
     * @param <T>       类型
     * @return 监听的节点
     */
    protected <T> Node removeEventListener(final ConcurrentMap<String, ConcurrentMap<T, Node>> listeners,
                                           final String path, final T listener) {
        Map<T, Node> map = listeners.get(path);
        if (map != null) {
            return map.remove(listener);
        }
        return null;
    }

    /**
     * 添加节点事件监听器
     *
     * @param listeners 监听器集合
     * @param path      路径
     * @param listener  监听器
     * @param <T>       类型
     * @return 监听的节点
     */
    protected <T> boolean addEventListener(ConcurrentMap<String, ConcurrentMap<T, Node>> listeners, String path,
                                           T listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return false;
        }
        ConcurrentMap<T, Node> map = listeners.get(path);
        if (map == null) {
            map = new ConcurrentHashMap<T, Node>();
            ConcurrentMap<T, Node> old = listeners.putIfAbsent(path, map);
            if (old != null) {
                map = old;
            }
        }
        return map.putIfAbsent(listener, null) == null;
    }

    @Override
    public void addListener(final String path, final ChildrenListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        PathUtil.validatePath(path);
        lock.readLock().lock();
        try {
            Node node = root.createDescendant(path, null);
            if (addEventListener(childrenListeners, path, listener)) {
                //初始化事件
                for (Node child : node.getChildren()) {
                    childrenEventManager
                            .add(new ChildrenEvent(ChildrenEvent.ChildrenEventType.CHILD_CREATED, child.getName(),
                                    null), listener);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addListener(final String path, final ChildrenDataListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        PathUtil.validatePath(path);
        lock.readLock().lock();
        try {
            Node node = root.createDescendant(path, null);
            if (addEventListener(childrenDataListeners, path, listener)) {
                //初始化事件
                for (Node child : node.getChildren()) {
                    childrenEventManager
                            .add(new ChildrenEvent(ChildrenEvent.ChildrenEventType.CHILD_CREATED, child.getName(),
                                    child.getData()), listener);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addListener(final String path, final PathListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        PathUtil.validatePath(path);
        lock.readLock().lock();
        try {
            if (addEventListener(pathListeners, path, listener)) {
                Node node = root.getDescendant(path);
                if (node != null) {
                    pathEventManager.add(new PathEvent(PathEvent.PathEventType.CREATED, node.getName(), node.getData()),
                            listener);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addListener(final String path, final LeaderListener listener) {
        //创建选举临时节点
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        PathUtil.validatePath(path);
        lock.readLock().lock();
        try {
            if (addEventListener(leaderListeners, path, listener)) {
                Node node = root.createDescendant(path, null);
                synchronized (node) {
                    // 创建了选举节点
                    Node child = createLeaderNode(node, null);
                    if (child != null) {
                        leaderListeners.get(path).put(listener, child);
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addListener(final String path, final ClusterListener listener) {
        //创建选举临时节点
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        PathUtil.validatePath(path);
        lock.readLock().lock();
        try {
            if (addEventListener(clusterListeners, path, listener)) {
                Node node = root.createDescendant(path, null);
                synchronized (node) {
                    // 确保并发顺序加入
                    Node child = createLeaderNode(node, listener.getNodeName().getBytes());
                    if (child != null) {
                        clusterListeners.get(path).put(listener, child);
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 在当前节点下创建选举节点
     *
     * @param node 节点
     */
    protected Node createLeaderNode(final Node node, final byte[] data) {
        if (node == null) {
            return null;
        }
        Node child;

        //添加选举节点
        long maxSeq = node.counter.incrementAndGet();
        String name = String.format("%s%09d", MEMBER, maxSeq);
        child = new Node(name, data, nodeEventManager);
        node.addChild(child);
        if (node.size() == 1) {
            leaders.put(node.getPath(), name);
        }
        return child;

    }

    @Override
    public void addListener(final ConnectionListener listener) {
        if (listener == null) {
            return;
        }
        connectionListeners.add(listener);
    }

    @Override
    public void removeListener(final String path, final PathListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            removeEventListener(pathListeners, path, listener);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void removeListener(final String path, final ChildrenListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            removeEventListener(childrenListeners, path, listener);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void removeListener(final String path, final ChildrenDataListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            removeEventListener(childrenDataListeners, path, listener);
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void removeListener(final String path, final LeaderListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {

            ConcurrentMap<LeaderListener, Node> listeners = leaderListeners.get(path);
            Node node = null;
            if (listeners != null) {
                node = listeners.remove(listener);
            }
            // 删除该监听器节点
            if (node != null) {
                Node parent = node.getParent();
                if (parent != null) {
                    parent.removeChild(node);
                }
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void removeListener(final String path, final ClusterListener listener) {
        if (listener == null || path == null || path.isEmpty()) {
            return;
        }
        lock.readLock().lock();
        try {
            ConcurrentMap<ClusterListener, Node> listeners = clusterListeners.get(path);
            Node node = null;
            if (listeners != null) {
                node = listeners.remove(listener);
            }
            if (node != null) {
                Node parent = node.getParent();
                if (parent != null) {
                    parent.removeChild(node);
                }
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void removeListener(final ConnectionListener listener) {
        if (listener == null) {
            return;
        }
        connectionListeners.remove(listener);
    }

    @Override
    public String getType() {
        return "memory";
    }

    /**
     * 创建节点通知事件
     *
     * @param parent 父节点
     * @param node   节点
     * @param type   事件类型
     */
    protected void createPathEvent(Node parent, Node node, PathEvent.PathEventType type) {
        if (node == null || parent == null) {
            return;
        }
        Map<PathListener, Node> listeners = pathListeners.get(node.getPath());
        if (listeners != null && !listeners.isEmpty()) {
            for (PathListener listener : listeners.keySet()) {
                pathEventManager.add(new PathEvent(type, node.getName(), node.getData()), listener);
            }
        }
    }

    /**
     * 创建子节点通知事件
     *
     * @param parent 父节点
     * @param node   节点
     * @param type   事件类型
     */
    protected void createChildrenEvent(Node parent, Node node, ChildrenEvent.ChildrenEventType type) {
        if (node == null || parent == null) {
            return;
        }
        Map<ChildrenListener, Node> listeners = childrenListeners.get(parent.getPath());
        if (listeners != null && !listeners.isEmpty()) {
            for (ChildrenListener listener : listeners.keySet()) {
                childrenEventManager.add(new ChildrenEvent(type, node.getName(), null), listener);
            }
        }
    }

    /**
     * 创建子节点数据通知事件
     *
     * @param parent 父节点
     * @param node   节点
     * @param type   事件类型
     */
    protected void createChildrenDataEvent(Node parent, Node node, ChildrenEvent.ChildrenEventType type) {
        if (node == null || parent == null) {
            return;
        }
        Map<ChildrenDataListener, Node> listeners = childrenDataListeners.get(parent.getPath());
        if (listeners != null) {
            for (ChildrenDataListener listener : listeners.keySet()) {
                childrenEventManager.add(new ChildrenEvent(type, node.getName(), node.getData()), listener);
            }
        }
    }

    /**
     * 创建选举数据通知事件
     *
     * @param parent 父节点
     * @param node   节点
     */
    protected void createLeaderEvent(Node parent, Node node) {
        if (node == null || parent == null) {
            return;
        }
        Map<LeaderListener, Node> listeners = leaderListeners.get(parent.getPath());
        if (listeners != null && !listeners.isEmpty()) {
            LeaderEvent.LeaderEventType type =
                    parent.size() == 0 ? LeaderEvent.LeaderEventType.LOST : LeaderEvent.LeaderEventType.TAKE;
            for (LeaderListener listener : listeners.keySet()) {
                leaderEventManager.add(new LeaderEvent(type, parent.getName()), listener);
            }
        }
    }

    /**
     * 创建集群数据通知事件
     *
     * @param parent 父节点
     * @param node   节点
     */
    protected void createClusterEvent(Node parent, Node node) {
        if (node == null || parent == null) {
            return;
        }
        Map<ClusterListener, Node> listeners = clusterListeners.get(parent.getPath());
        if (listeners != null && !listeners.isEmpty()) {
            //集群监听器
            List<PathData> states = new ArrayList<PathData>();
            for (Node child : parent.getChildren()) {
                states.add(new PathData(child.getName(), child.getData()));
            }
            for (ClusterListener listener : listeners.keySet()) {
                clusterEventManager.add(new ClusterEvent(parent.getName(), states), listener);
            }
        }
    }

    /**
     * 状态
     */
    protected enum Status {
        OPENED, CLOSING, CLOSED
    }

    /**
     * 节点事件监听器
     */
    protected class NodeEventListener implements EventListener<NodeEvent> {
        @Override
        public void onEvent(NodeEvent event) {
            Node current = event.getNode();
            //得到父节点
            Node parent = event.getParent();
            if (parent == root) {
                parent = null;
            }

            if (event.type == NodeEvent.NodeEventType.ADD) {
                createPathEvent(parent, current, PathEvent.PathEventType.CREATED);
                createChildrenEvent(parent, current, ChildrenEvent.ChildrenEventType.CHILD_CREATED);
                createChildrenDataEvent(parent, current, ChildrenEvent.ChildrenEventType.CHILD_CREATED);
                createClusterEvent(parent, current);
                createLeaderEvent(parent, current);
            } else if (event.type == NodeEvent.NodeEventType.UPDATE) {
                createPathEvent(parent, current, PathEvent.PathEventType.UPDATED);
                createChildrenDataEvent(parent, current, ChildrenEvent.ChildrenEventType.CHILD_UPDATED);
            } else if (event.type == NodeEvent.NodeEventType.DELETE) {
                createPathEvent(parent, current, PathEvent.PathEventType.REMOVED);
                createChildrenEvent(parent, current, ChildrenEvent.ChildrenEventType.CHILD_REMOVED);
                createChildrenDataEvent(parent, current, ChildrenEvent.ChildrenEventType.CHILD_REMOVED);
                createClusterEvent(parent, current);
                createLeaderEvent(parent, current);
            }

        }
    }
}
