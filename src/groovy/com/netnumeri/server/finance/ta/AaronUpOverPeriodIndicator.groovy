package com.netnumeri.server.finance.ta
import com.dtmc.finance.finpojo.Instrument

public class AaronUpOverPeriodIndicator extends Indicator {

    public AaronUpOverPeriodIndicator(Instrument instrument, String name, Integer param1) {
        super(instrument, name);
        this.series = instrument.lowSeries()
        double[] highs = instrument.highSeries().convertToArray();
        double[] ad = Aroon.aroonUpOverPeriod(highs, param1);
        copyBackwords(ad);
    }

}


