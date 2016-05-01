package registry.listener;

import registry.util.EventListener;

/**
 * Created by anzhen on 2016/5/1.
 * 集群选举
 */
public interface ClusterListener extends EventListener<ClusterEvent> {

    /**
     * 返回当前节点名称
     *
     * @return 节点名称
     */
    String getNodeName();

}
