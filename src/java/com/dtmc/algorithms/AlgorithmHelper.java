

package com.dtmc.algorithms;

import com.dtmc.Value;

import java.util.List;

public final class AlgorithmHelper
        implements IAlgorithm {

    public AlgorithmHelper() {
    }

    public void execute(List data, int index) {
        Value value = (Value) data.get(index);
        Double open = (Double) value.get("OPEN");
        Double close = (Double) value.get("CLOSE");
        value.set("MEDIAN", Double.valueOf((open.doubleValue() + close.doubleValue()) / 2D));
        value.set("TOP", Double.valueOf(Math.max(open.doubleValue(), close.doubleValue())));
        value.set("BOTTOM", Double.valueOf(Math.min(open.doubleValue(), close.doubleValue())));
    }
}
