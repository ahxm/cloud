package registry.listener;

import registry.util.EventListener;

/**
 * Created by anzhen on 2016/5/1.
 */
public interface ConnectionListener extends EventListener<ConnectionEvent> {

    /**
     * 广播事件
     *
     * @param event
     */
    void onEvent(ConnectionEvent event);

}