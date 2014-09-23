package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument

public class AaronDownOverPeriodIndicator extends Indicator {

    public AaronDownOverPeriodIndicator(Instrument instrument, String name, Integer period) {
        super(instrument, name);
        this.series = instrument.lowSeries()
        double[] lows = instrument.lowSeries().convertToArray();
        double[] ad = Aroon.aroonDownOverPeriod(lows, period);
        copyBackwords(ad);
    }

}


