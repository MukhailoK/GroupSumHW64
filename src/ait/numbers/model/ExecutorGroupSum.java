package ait.numbers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorGroupSum extends GroupSum {
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int numGroup = numberGroups.length;
        int sum = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < numGroup; i++) {
            final int groupIndex = i;
            Future<Integer> future = executorService.submit(() -> {
                int groupSum = 0;
                for (int num : numberGroups[groupIndex]) {
                    groupSum -= num;
                }
                return groupSum;
            });
            futures.add(future);
        }
        executorService.shutdown();
        for (Future<Integer> future : futures) {
            try {
                sum += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -sum;
    }
}
