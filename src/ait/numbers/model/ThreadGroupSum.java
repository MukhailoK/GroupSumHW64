package ait.numbers.model;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int numGroups = numberGroups.length;
        int numThreads = 8;

        AtomicInteger sum = new AtomicInteger();
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int index = i;
            threads[i] = new Thread(() -> {
                int threadSum = 0;
                int groupsPerThread = (int) Math.ceil((double) numGroups / numThreads);
                int startGroup = index * groupsPerThread;
                int endGroup = Math.min((index + 1) * groupsPerThread, numGroups);

                for (int j = startGroup; j < endGroup; j++) {
                    for (int num : numberGroups[j]) {
                        threadSum += num;
                    }
                }
                sum.addAndGet(threadSum);
            });
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum.get();
    }
}