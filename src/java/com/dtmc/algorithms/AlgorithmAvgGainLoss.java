package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmAvgGainLoss implements IAlgorithm {

    private final String _gain;
    private final String _loss;
    private final String _avgGain;
    private final String _avgLoss;
    private final int _depth;

    public AlgorithmAvgGainLoss(String gain, String loss, String avgGain, String avgLoss, int depth) {
        _gain = gain;
        _loss = loss;
        _avgGain = avgGain;
        _avgLoss = avgLoss;
        _depth = depth;
    }

    public void execute(List data, int index) {
        Value current = (Value) data.get(index);
        if (index < _depth)
            set(current, 0.0D, 0.0D);
        else if (index == _depth) {
            double sumGain = 0.0D;
            double sumLoss = 0.0D;
            for (int i = 1; i <= index; i++) {
                sumGain += (Double) ((Value) data.get(i)).get(_gain);
                sumLoss += (Double) ((Value) data.get(i)).get(_loss);
            }

            double avgGain = sumGain / (double) _depth;
            double avgLoss = sumLoss / (double) _depth;
            set(current, avgGain, avgLoss);
        } else {
            Value previous = (Value) data.get(index - 1);
            double avgGain = ((Double) previous.get(_avgGain) * (double) (_depth - 1) + (Double) current.get(_gain)) / (double) _depth;
            double avgLoss = ((Double) previous.get(_avgLoss) * (double) (_depth - 1) + (Double) current.get(_loss)) / (double) _depth;
            set(current, avgGain, avgLoss);
        }
    }

    private void set(Value current, double gain, double loss) {
        current.set(_avgGain, gain);
        current.set(_avgLoss, loss);
    }


}
