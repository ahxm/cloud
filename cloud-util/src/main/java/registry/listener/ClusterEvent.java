package registry.listener;

import registry.util.PathData;

import java.util.List;

/**
 * Created by anzhen on 2016/5/1.
 */
public class ClusterEvent {

    private String path;
    private List<PathData> clusterState;

    public ClusterEvent(String path, List<PathData> clusterState) {
        this.path = path;
        this.clusterState = clusterState;
    }

    public List<PathData> getClusterState() {
        return clusterState;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ClusterEvent [path = " + path + ", clusterState = " + clusterState.toString() + "]";
    }
}
