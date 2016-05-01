package registry.util;

/**
 * Created by anzhen on 2016/5/1.
 */
public interface EventListener<Event> {

    /**
     * 事件
     *
     * @param event
     */
    void onEvent(Event event);
}
