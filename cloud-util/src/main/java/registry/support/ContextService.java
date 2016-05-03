package registry.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.support.builder.ContextBuilder;
import registry.util.Context;
import registry.util.RetryLoop;
import registry.util.RetryPolicy;


/**
 * Created by anzhen on 2016/5/2.
 */
public abstract class ContextService extends LeaderService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ContextBuilder contextBuilder;

    public void setContextBuilder(ContextBuilder contextBuilder) {
        this.contextBuilder = contextBuilder;
    }

    protected Context createContext(){
        if(contextBuilder == null){
            return  null;
        }

        return contextBuilder.createContext();
    }

    protected abstract void startService(final Context context);

    @Override
    protected void onTakeLeader() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = createContextWithRetry();
                if(context == null || !isStarted() || !leader.get()){
                    return;
                }
                mutex.lock();
                try{
                    if(!isStarted() || !leader.get()){
                        return;
                    }
                    startService(context);
                }finally {
                    mutex.unlock();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    protected void onLostLeader() {

    }

    protected Context createContextWithRetry(){

        try{
            RetryPolicy retryPolicy = new RetryPolicy(5000,5000,0,false,2.0,0);
            return RetryLoop.execute(retryPolicy,new RetryLoop.RetryCallable<Context>(){
                /**
                 * 出现异常，是否要继续
                 *
                 * @param e 异常
                 * @return <li>true 继续重试</li>
                 * <li>false 退出重试</li>
                 */
                @Override
                public boolean onException(Exception e) {
                    logger.error("get collect config error.", e);
                    return true;
                }

                /**
                 * 是否要继续
                 *
                 * @return 是否要继续重试
                 */
                @Override
                public boolean shouldContinue() {
                    return isStarted() && leader.get();
                }

                /**
                 * Computes a result, or throws an exception if unable to do so.
                 *
                 * @return computed result
                 * @throws Exception if unable to compute a result
                 */
                @Override
                public Context call() throws Exception {
                    return createContext();
                }
            });

        }catch (Exception e){
            logger.error("get collect config error",e);
        }
        return null;
    }
}
