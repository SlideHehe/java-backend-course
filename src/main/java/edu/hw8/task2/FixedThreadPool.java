package edu.hw8.task2;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    public static FixedThreadPool create(int numOfThreads) {
        if (numOfThreads < 1) {
            throw new IllegalArgumentException("You can't create ThreadPool with less than 1 threads");
        }

        return new FixedThreadPool(numOfThreads);
    }

    private final BlockingQueue<Runnable> blockingQueue;
    private final Thread[] threads;
    private boolean isRunning;

    private FixedThreadPool(int numOfThreads) {
        blockingQueue = new LinkedBlockingQueue<>();

        threads = new Thread[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Worker();
        }
    }

    @Override
    public void start() {
        isRunning = true;
        for (Thread worker : threads) {
            worker.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        Objects.requireNonNull(runnable);

        try {
            blockingQueue.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        isRunning = false;

        for (Thread ignored : threads) {
            blockingQueue.put(() -> {
            }); // Пустая лямба для выхода из бесконечно цикла
            // не хотелось использовать thread.interrupt() т.к. тогда все потоки прерываются и существующие таски
            // стопаются. В том же ExecutorService все таски из очереди завершаются, прежде чем сработает shutdown
        }

        for (Thread worker : threads) {
            worker.join();
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    blockingQueue.take().run();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
