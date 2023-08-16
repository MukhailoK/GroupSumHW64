package ait.numbers;

import ait.numbers.model.ExecutorGroupSum;
import ait.numbers.model.GroupSum;
import ait.numbers.model.ParallelStreamGroupSum;
import ait.numbers.model.ThreadGroupSum;
import ait.numbers.test.GroupSumPerfomanceTest;

import java.util.Random;

public class GroupSumAppl {
    private static final int N_GROUPS = 10_000;
    private static final int NUMBERS_PER_GROUP = 10_000;
    private static int[][] arr = new int[N_GROUPS][NUMBERS_PER_GROUP];
    private static Random random = new Random();


    public static void main(String[] args) {
        fillArray();
        GroupSum threadsGroupSum = new ThreadGroupSum(arr);
        GroupSum executorGroupSum = new ExecutorGroupSum(arr);
        GroupSum streamSum = new ParallelStreamGroupSum(arr);
        new GroupSumPerfomanceTest("ParallelStreamGroupSum", streamSum).runTest();
        new GroupSumPerfomanceTest("ExecutorGroupSum", executorGroupSum).runTest();
        new GroupSumPerfomanceTest("ThreadPoolGroupSum", threadsGroupSum).runTest();

    }

    private static void fillArray() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = random.nextInt();
            }
        }

    }
}
