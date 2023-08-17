package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

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

        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numberGroups.length);
        for (int i = 0; i < tasks.length; i++) {
            executor.execute(tasks[i]);
//            int groupIndex = i;
//            executor.execute(() -> sum.addAndGet(Arrays.stream(numberGroups[groupIndex]).sum()));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        sum.addAndGet(Arrays.stream(tasks)
                .mapToInt(OneGroupSum::getSum)
                .sum());
        return sum.get();
    }
}
