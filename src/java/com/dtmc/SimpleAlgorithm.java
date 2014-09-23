package com.dtmc;


import com.dtmc.algorithms.IAlgorithm;

import java.util.List;

/**
 * Simple algorithm, that concat OHLC to OHLC field;
 * @author Denis
 */
public class SimpleAlgorithm implements IAlgorithm {

    @Override
    public void execute(List<Value> data, int index) {
        final Value value = data.get(index);
        final Double open = (Double) value.get("OPEN");
        final Double high = (Double) value.get("HIGH");
        final Double low = (Double) value.get("LOW");
        final Double close = (Double) value.get("CLOSE");
        final Double result = open + high + low + close;
        value.set("OHLC", result);
    }
}
