package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class ChaikinMoneyFlowOverPeriodIndicator extends Indicator {

    public ChaikinMoneyFlowOverPeriodIndicator(Instrument instrument, String name, Integer period) {
        super(instrument, name);
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] vols = instrument.volumeSeries().convertToArray();
        double[] ad = AccumulateDistribute.chaikinMoneyFlowOverPeriod(highs, lows, closes, vols, period);
        copyBackwords(ad);
    }

}


