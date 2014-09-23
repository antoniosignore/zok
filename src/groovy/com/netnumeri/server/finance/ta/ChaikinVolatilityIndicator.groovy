package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class ChaikinVolatilityIndicator extends Indicator {

    int period
    int smoothing

    public ChaikinVolatilityIndicator(Instrument instrument, String name, Integer smoothing, Integer period) {
        super(instrument, name);
        this.smoothing = smoothing;
        this.period = period;
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] ad = Volatility.chaikinVolatility(highs, lows, (int) smoothing, period);
        copyBackwords(ad);
    }

}


