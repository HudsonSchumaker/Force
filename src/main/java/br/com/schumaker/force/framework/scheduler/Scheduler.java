package br.com.schumaker.force.framework.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The Scheduler class.
 * This class is responsible for scheduling tasks to be executed after a certain delay or at fixed intervals.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Scheduler {
    private static final Scheduler INSTANCE = new Scheduler();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private Scheduler() {}

    public static Scheduler getInstance() {
        return INSTANCE;
    }

    /**
     * Schedules a task to be executed after a specified delay.
     *
     * @param task the task to be executed
     * @param delay the delay before executing the task, in milliseconds
     */
    public void schedule(Runnable task, long delay) {
        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Schedules a task to be executed after a specified delay.
     *
     * @param task the task to be executed
     * @param delay the delay before executing the task
     * @param unit the time unit of the delay
     */
    public void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(task, delay, unit);
    }

    /**
     * Schedules a task to be executed at fixed intervals.
     *
     * @param task the task to be executed
     * @param initialDelay the initial delay before the first execution, in milliseconds
     * @param period the period between successive executions, in milliseconds
     */
    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Shuts down the scheduler.
     */
    public static void shutdown() {
        scheduler.shutdown();
    }
}
