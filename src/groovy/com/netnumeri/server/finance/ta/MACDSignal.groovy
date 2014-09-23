package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class MACDSignal extends Indicator {

    int smoothing1;
    int smoothing2;
    int smoothing3;

    public MACDSignal(TimeSeries series, String name, Integer smoothing1, Integer smoothing2, Integer smoothing3) {
        super(series, name);
        this.smoothing1 = smoothing1;
        this.smoothing2 = smoothing2;
        this.smoothing3 = smoothing3;
        build();
    }

    public void build() {

        double[] closes = series.convertToArray();
        double[] ma12 = MovingAverage.simpleMovingAverage(closes, smoothing1);
        double[] ma26 = MovingAverage.simpleMovingAverage(closes, smoothing2);

        double[] macdSignal = new double[ma26.length];
        for (int i = 0; i < ma26.length; i++) {
            macdSignal[i] = ma12[ma26.length - 1 - i] - ma26[ma26.length - 1 - i];
        }

        double[] maSignal = MovingAverage.simpleMovingAverage(macdSignal, smoothing3);
        copyBackwords(maSignal);
    }

}


