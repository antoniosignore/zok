package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class MinusDMIndicator extends Indicator {

    public MinusDMIndicator(Instrument instrument, String name) {
        super(instrument, name);
        build();
    }

    public void build() {
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = DirectionalMovementIndicator.minusDirectionalMovementPeriod(highs, lows, closes);
        copyBackwords(ar);
    }

}


