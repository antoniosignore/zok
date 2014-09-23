package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.dtmc.finance.finpojo.Instrument


public class MoneyFlowOverPeriodIndicator extends Indicator {

    int period

    public MoneyFlowOverPeriodIndicator(Instrument instrument, String name, Integer period) {
        super(instrument, name);
        this.instrument = instrument;
        this.period = period;

        TimeSeries series = instrument.highSeries()

        double[] ad = Oscillators.moneyFlowIndexOverPeriod(
                series.convertToArray(),
                instrument.lowSeries().convertToArray(),
                instrument.closeSeries().convertToArray(),
                instrument.volumeSeries().convertToArray(),
                (int) period);
        copyBackwords(ad);
    }

}


