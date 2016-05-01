package registry.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.annotation.NotEmpty;
import registry.annotation.NotNull;
import registry.listener.LeaderEvent;
import registry.listener.LeaderListener;
import registry.util.Registry;
import registry.util.Service;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by anzhen on 2016/5/1.
 */
public abstract class LeaderService extends Service {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    protected Registry registry;

    @NotEmpty
    protected String leaderPath;

    protected MyLeaderListener listener = new MyLeaderListener();

    protected AtomicBoolean leader = new AtomicBoolean(false);


    protected abstract void onTakeLeader();

    protected abstract void onLostLeader();

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setLeaderPath(String leaderPath) {
        if (leaderPath != null && !leaderPath.isEmpty()) {
            this.leaderPath = leaderPath;
        }
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        leader.set(false);
        registry.open();
        registry.addListener(leaderPath, listener);
    }

    @Override
    protected void doStop() {
        if (null != registry) {
            registry.removeListener(leaderPath, listener);
        }
        if (leader.compareAndSet(true, false)) {
            onLostLeader();
        }

        super.doStop();
    }


    protected class MyLeaderListener implements LeaderListener {
        /**
         * 事件
         *
         * @param leaderEvent
         */
        @Override
        public void onEvent(LeaderEvent leaderEvent) {
            mutex.lock();
            try {
                if (!isStarted()) {
                    return;
                }
                if (leaderEvent.getType() == LeaderEvent.LeaderEventType.TAKE) {
                    if (leader.compareAndSet(false, true)) {
                        logger.info(String.format("I have taken the leadership %s", leaderPath));
                        onTakeLeader();
                    } else if (leaderEvent.getType() == LeaderEvent.LeaderEventType.LOST) {
                        if (leader.compareAndSet(true, false)) {
                            logger.info(String.format("I have lost the leadership", leaderPath));
                            onLostLeader();
                        }
                    }
                }
            } finally {
                mutex.unlock();
            }
        }
    }
}

