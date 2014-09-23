package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class GeometricMAIndicator extends Indicator {

    int smoothing;

    public GeometricMAIndicator(TimeSeries series, String name, Integer param1) {
        super(series, name);
        smoothing = param1;
        build();
    }

    public void build() {
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = MovingAverage.geometricMovingAverage(closes, smoothing);
        copyBackwords(ar);
    }

}


