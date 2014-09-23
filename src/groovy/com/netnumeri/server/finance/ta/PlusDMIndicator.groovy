package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class PlusDMIndicator extends Indicator {

    public PlusDMIndicator() {
    }

    public PlusDMIndicator(Instrument instrument, String name) {
        super(instrument, name);
        build();
    }

    public void build() {
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = DirectionalMovementIndicator.plusDirectionalMovementPeriod(highs, lows, closes);
        copyBackwords(ar);
    }

}


