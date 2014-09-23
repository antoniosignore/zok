package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmVolatility implements IAlgorithm {

    public AlgorithmVolatility(String close, String average, String target, int depth, boolean create) {
        _close = close;
        _average = average;
        _target = target;
        _depth = depth;
        _helper = create ? ((IAlgorithm) (new AlgorithmAverage(close, average, depth))) : null;
    }

    public void execute(List data, int index) {
        if (_helper != null)
            _helper.execute(data, index);
        int startIndex = (1 + index) - _depth;
        if (startIndex < 0)
            startIndex = 0;
        Value targetValue = (Value) data.get(index);
        double sumSquared = 0.0D;
        for (int i = startIndex; i <= index; i++) {
            double close = (Double) ((Value) data.get(i)).get(_close);
            double mean = (Double) targetValue.get(_average);
            double deviation = close - mean;
            double squared = Math.pow(deviation, 2D);
            sumSquared += squared;
        }

        int count = (index - startIndex) + 1;
        double avgSquared = sumSquared / (double) count;
        double sqrt = Math.sqrt(avgSquared);
        targetValue.set(_target, sqrt);
    }

    private final String _close;
    private final String _average;
    private final String _target;
    private final int _depth;
    private final IAlgorithm _helper;
}
