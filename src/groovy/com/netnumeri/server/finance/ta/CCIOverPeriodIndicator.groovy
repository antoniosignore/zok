package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class CCIOverPeriodIndicator extends Indicator {

    int period;

    public CCIOverPeriodIndicator(Instrument instrument, String name, Integer param1) {
        super(instrument, name);
        period = param1;
        build();
    }

    public void build() {
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] volumes = instrument.volumeSeries().convertToArray();
        double[] ar = MeanReversion.commodityChannelIndexOverPeriod(highs, lows, volumes, period);
        copyBackwords(ar);
    }

}


