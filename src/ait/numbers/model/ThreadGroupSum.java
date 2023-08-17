package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int numGroups = numberGroups.length;
        OneGroupSum[] tasks = new OneGroupSum[numGroups];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        AtomicInteger sum = new AtomicInteger();
        Thread[] threads = new Thread[numGroups];

        for (int i = 0; i < numGroups; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sum.addAndGet(Arrays.stream(tasks).mapToInt(OneGroupSum::getSum).sum());
        return sum.get();
    }
}