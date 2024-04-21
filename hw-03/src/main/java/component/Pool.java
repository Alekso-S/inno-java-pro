package component;

import java.util.ArrayList;
import java.util.LinkedList;

public class Pool {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final ArrayList<Worker> workers;
    private boolean active = true;

    public Pool(int capacity) {
        workers = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable runnable) {
        if (!active) {
            throw new IllegalStateException("Pool is not in active state");
        }
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notifyAll();
        }
    }

    public void shutdown() {
        active = false;
        synchronized (workers) {
            workers.notifyAll();
        }
    }

    public boolean isTerminated() {
        for (Worker worker : workers) {
            if (!worker.getState().equals(Thread.State.TERMINATED)) {
                return false;
            }
        }
        return true;
    }

    class Worker extends Thread {
        @Override
        public void run() {
            while (!tasks.isEmpty() || active) {
                Runnable runnable;
                synchronized (tasks) {
                    if (!tasks.isEmpty()) {
                        runnable = tasks.remove();
                    } else {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }
                }
                runnable.run();
            }
        }
    }
}