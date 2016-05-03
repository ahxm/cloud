package registry.util;

/**
 * Created by anzhen on 2016/5/3.
 */
public class ServiceThread implements Runnable {

    protected Service parent;

    protected long interval;

    public ServiceThread(Service parent) {
      this(parent,0);
    }

    public ServiceThread(Service parent, long interval) {
        if (parent == null) {
            throw new IllegalArgumentException("parent can not be null");
        }
        this.parent = parent;
        this.interval = interval;
    }

    /**
     * 是否存活
     *
     * @return
     */
    protected boolean isAlive() {
        return parent.isStarted() && !Thread.currentThread().isInterrupted();
    }

    /**
     * 执行任务
     *
     * @throws Exception
     */
    protected void execute() throws Exception {

    }

    /**
     * 等待时间
     *
     * @param time
     */
    protected void await(final long time) {
        if (time > 0) {
            parent.await(time);
        }
    }

    public long getInterval() {
        return interval;
    }

    /**
     * 线程终止
     */
    protected void onStop() {

    }

    /**
     * 出现异常
     *
     * @param e 异常
     */
    protected void onException(final Throwable e) {

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        long interval;
        while (isAlive()){
            try{
                interval = getInterval();
                if(interval>0){
                    await(interval);
                    if(!isAlive()){
                        break;
                    }
                }
                execute();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (Throwable e){
                onException(e);
            }
        }
        onStop();
    }
}
