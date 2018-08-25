package ChatServer;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a thread pool
 */
public class ThreadPool {
    // A thread safe list of tasks
    private LinkedBlockingQueue<Runnable> tasksQueue;
    // An array of the threads
    private ArrayList<WorkingThread> threadsList;

    public ThreadPool(int maxNumberOfThread) {
        this(maxNumberOfThread, false, "");
    }

    public ThreadPool(int maxNumberOfThread, boolean shouldPrintTrace, String name) {
        tasksQueue = new LinkedBlockingQueue<Runnable>();
        threadsList = new ArrayList<WorkingThread>();

        for(int i = 0; i< maxNumberOfThread; i++) {
            WorkingThread workingThread = new WorkingThread(tasksQueue, shouldPrintTrace, name);
            threadsList.add(workingThread);
            workingThread.start();
        }
    }

    /**
     * Executes a task on an avaliable thread
     * @param task to perform
     */
    public synchronized void executeTask(Runnable task) {
        tasksQueue.add(task);
    }

    /**
     * Stops all the working threads
     */
    public synchronized void stopThreads() {
        for(WorkingThread t : this.threadsList) {
            t.terminate();
        }
    }
}
