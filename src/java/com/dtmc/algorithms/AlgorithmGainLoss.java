package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmGainLoss implements IAlgorithm {

    private final String _source;
    private final String _gain;
    private final String _loss;

    public AlgorithmGainLoss(String source, String gain, String loss) {
        _source = source;
        _gain = gain;
        _loss = loss;
    }

    public void execute(List data, int index) {
        int previousIndex = index - 1;
        if (previousIndex < 0)
            previousIndex = 0;
        Value previous = (Value) data.get(previousIndex);
        Value current = (Value) data.get(index);
        double change = (Double) current.get(_source) - (Double) previous.get(_source);
        int compare = Double.compare(change, 0.0D);
        if (compare > 0) {
            current.set(_gain, Math.abs(change));
            current.set(_loss, 0.0D);
        } else if (compare < 0) {
            current.set(_gain, 0.0D);
            current.set(_loss, Math.abs(change));
        } else {
            current.set(_gain, 0.0D);
            current.set(_loss, 0.0D);
        }
    }


}
