package ChatServer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A thread that executes tasks from the tasks queue
 */
public class WorkingThread extends Thread {
    private final boolean shouldPrintTrace;
    private LinkedBlockingQueue<Runnable> tasksQueue;
    private boolean running = false;
    private boolean isDone = true;
    private String name;

    public WorkingThread(LinkedBlockingQueue<Runnable> tasksQueue, boolean shouldPrintTrace, String name) {
        this.shouldPrintTrace = shouldPrintTrace;
        this.tasksQueue = tasksQueue;
        this.name = name;
    }

    public WorkingThread(LinkedBlockingQueue<Runnable> tasksQueue) {
        this(tasksQueue, false, "");
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            internalRun();
        }
    }

    private synchronized void internalRun() {
        try {
            Runnable task = this.tasksQueue.take();
            isDone = false;
            if(running) {
                if(shouldPrintTrace) {
                    System.out.println(String.format("[%s] Items left in queue: %d", name, this.tasksQueue.size()));
                }

                task.run();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            isDone = true;
        }
    }

    /**
     * Is the thread finished its tasks
     * @return true if the thread finished its tasks
     */
    public boolean isDone() {
        return isDone;
    }
    
    /**
     * Terminates the thread
     */
    public synchronized void terminate() {
        this.running = false;
        this.isDone = true;
        this.interrupt();
    }
}