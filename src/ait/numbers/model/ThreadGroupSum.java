package ait.numbers.model;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int numGroups = numberGroups.length;
        int numThreads = Runtime.getRuntime().availableProcessors();

        var ref = new Object() {
            int sum = 0;
        };

        Thread[] threads = new Thread[numThreads];
        AtomicInteger threadIndex = new AtomicInteger(0);

        Runnable task = () -> {
            int currentIndex = threadIndex.getAndIncrement();
            int threadSum = 0;
            int groupsPerThread = (int) Math.ceil((double) numGroups / numThreads);
            int startGroup = currentIndex * groupsPerThread;
            int endGroup = Math.min((currentIndex + 1) * groupsPerThread, numGroups);

            for (int i = startGroup; i < endGroup; i++) {
                for (int num : numberGroups[i]) {
                    threadSum += num;
                }
            }
            synchronized (this) {
                ref.sum += threadSum;
            }
        };

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Handle InterruptedException
                e.printStackTrace();
            }
        }

        return ref.sum;
    }
}
