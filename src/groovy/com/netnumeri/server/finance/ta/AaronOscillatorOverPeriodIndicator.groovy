package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class AaronOscillatorOverPeriodIndicator extends Indicator {

    public AaronOscillatorOverPeriodIndicator(Instrument instrument, String name, Integer period) {
        super(instrument, name);
        this.series = instrument.lowSeries()
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] ad = Aroon.aroonOscillatorOverPeriod(highs, lows, period);
        copyBackwords(ad);
    }
}


