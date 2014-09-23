package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmTrendNormalized implements IAlgorithm {

    public AlgorithmTrendNormalized(String source, String target, int depth) {
        _source = source;
        _target = target;
        _depth = depth;
        _x = new double[_depth];
        for (int index = 0; index < _depth; index++)
            _x[index] = index + 1;

        _avgX = average(_x);
        for (int index = 0; index < _depth; index++)
            _x[index] = _avgX - _x[index];

    }

    public void execute(List data, int index) {
        Value current = (Value) data.get(index);
        if (index < _depth - 1) {
            current.set(_target, Double.valueOf(0.0D));
        } else {
            double y[] = new double[_depth];
            int counter = 0;
            for (int i = (1 + index) - _depth; i <= index; i++)
                y[counter++] = ((Double) ((Value) data.get(i)).get(_source)).doubleValue();

            double avgY = average(y);
            for (int i = 0; i < _depth; i++)
                y[i] = (y[i] / avgY) * 200D;

            for (int i = 0; i < _depth; i++)
                y[i] = avgY - y[i];

            double mUp = 0.0D;
            double mDown = 0.0D;
            for (int i = 0; i < _depth; i++) {
                mUp += _x[i] * y[i];
                mDown += Math.pow(_x[i], 2D);
            }

            double m = mUp / mDown;
            current.set(_target, Double.valueOf(m));
        }
    }

    private double average(double source[]) {
        double sum = 0.0D;
        double arr$[] = source;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            double value = arr$[i$];
            sum += value;
        }

        return sum / (double) source.length;
    }

    private final String _source;
    private final String _target;
    private final int _depth;
    private final double _x[];
    private final double _avgX;
}
