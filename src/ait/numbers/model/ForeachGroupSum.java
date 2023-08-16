package ait.numbers.model;

import java.util.Arrays;

public class ForeachGroupSum extends GroupSum {

    public ForeachGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        return Arrays.stream(numberGroups)
                .flatMapToInt(Arrays::stream).sum();

    }
}
