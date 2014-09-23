package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class ChaikinOscillatorOverPeriodIndicator extends Indicator {

    public ChaikinOscillatorOverPeriodIndicator(Instrument instrument, String name, double period) {
        super(instrument, name);
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] vols = instrument.volumeSeries().convertToArray();
        double[] ad = AccumulateDistribute.chaikinOscillatorOverPeriod(highs, lows, vols, period);
        copyBackwords(ad);
    }

}


