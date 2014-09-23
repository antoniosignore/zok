package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmAverage implements IAlgorithm {

    private final String _source;
    private final String _destination;
    private final int _depth;

    public AlgorithmAverage(String source, String destination, int depth) {
        _source = source;
        _destination = destination;
        _depth = depth;
    }

    public void execute(List data, int index) {
        int startIndex = (1 + index) - _depth;
        if (startIndex < 0)
            startIndex = 0;
        double sum = 0.0D;
        for (int i = startIndex; i <= index; i++)
            sum += (Double) ((Value) data.get(i)).get(_source);

        int count = (index - startIndex) + 1;
        ((Value) data.get(index)).set(_destination, sum / (double) count);
    }

}
