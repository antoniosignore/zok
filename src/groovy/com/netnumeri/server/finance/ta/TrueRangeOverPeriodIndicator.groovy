package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument

public class TrueRangeOverPeriodIndicator extends Indicator {

    public TrueRangeOverPeriodIndicator() {
    }

    public TrueRangeOverPeriodIndicator(Instrument instrument, String name) {
        super(instrument, name);

        //         this.series = instrument.lowSeries()

        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = DirectionalMovementIndicator.trueRangePeriod(highs, lows, closes);
        copyBackwords(ar);
    }

}


