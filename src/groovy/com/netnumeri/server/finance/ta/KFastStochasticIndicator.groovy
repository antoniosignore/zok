package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class KFastStochasticIndicator extends Indicator {

    int period;

    public KFastStochasticIndicator(Instrument instrument, String name, Integer param1) {
        super(instrument, name);
        period = param1;
        build();
    }

    public void build() {
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = Stochastics.kFastStochasticPeriod(highs, lows, closes, period);
        copyBackwords(ar);
    }

}


