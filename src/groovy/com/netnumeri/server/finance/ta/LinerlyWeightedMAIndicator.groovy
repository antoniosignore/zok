package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class LinerlyWeightedMAIndicator extends Indicator {

    int smoothing;

    public LinerlyWeightedMAIndicator(TimeSeries series, String name, Integer param1) {
        super(series, name);
        smoothing = param1;
        build();
    }

    public void build() {
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = MovingAverage.linearlyWeightedMovingAverage(closes, smoothing);
        copyBackwords(ar);
    }

}


