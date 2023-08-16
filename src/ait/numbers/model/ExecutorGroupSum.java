package ait.numbers.model;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorGroupSum extends GroupSum {
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        AtomicInteger sum = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (int i = 0; i < numberGroups.length; i++) {
            int groupIndex = i;
            executor.execute(() -> sum.addAndGet(Arrays.stream(numberGroups[groupIndex]).sum()));
        }
        while (!executor.isTerminated()) {
            executor.shutdown();
        }
        return sum.get();
    }
}
