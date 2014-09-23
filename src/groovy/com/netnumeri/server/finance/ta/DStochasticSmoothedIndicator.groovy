package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class DStochasticSmoothedIndicator extends Indicator {

    int period;
    int smoothing;

    public DStochasticSmoothedIndicator(Instrument instrument, String name, Integer period, Integer smoothing) {
        super(instrument, name);
        this.period = period;
        this.smoothing = smoothing;
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = Stochastics.kFastStochasticPeriod(highs, lows, closes, period);
        copyBackwords(MovingAverage.simpleMovingAverage(ar, smoothing));
    }

}


