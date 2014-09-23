package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class DStochasticIndicator extends Indicator {

    public DStochasticIndicator(Instrument instrument, String name, Integer period, Integer method, Integer smoothing) {
        super(instrument, name);
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = Stochastics.dStochastic(highs, lows, closes, period, method, smoothing);
        copyBackwords(ar);
    }

}


