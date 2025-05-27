package br.com.schumaker.force.framework.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Scheduler {
    private static final Scheduler INSTANCE = new Scheduler();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private Scheduler() {}

    public static Scheduler getInstance() {
        return INSTANCE;
    }

    public void schedule(Runnable task, long delay) {
        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(task, delay, unit);
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}
