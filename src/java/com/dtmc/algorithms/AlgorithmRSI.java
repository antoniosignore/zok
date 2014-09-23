
package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmRSI implements IAlgorithm {
    private final String _gain;
    private final String _loss;
    private final String _rsi;
    private final int _depth;

    public AlgorithmRSI(String gain, String loss, String rsi, int depth) {
        _gain = gain;
        _loss = loss;
        _rsi = rsi;
        _depth = depth;
    }

    public void execute(List data, int index) {
        Value current = (Value) data.get(index);
        if (index < _depth) {
            current.set(_rsi, 0.0D);
        } else {
            double gain = (Double) current.get(_gain);
            double loss = (Double) current.get(_loss);
            double rs = gain / loss;
            double rsi = Double.compare(loss, 0.0D) != 0 ? 100D - 100D / (1.0D + rs) : 100D;
            current.set(_rsi, rsi);
        }
    }


}
