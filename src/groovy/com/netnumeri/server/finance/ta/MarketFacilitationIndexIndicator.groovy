package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class MarketFacilitationIndexIndicator extends Indicator {

    public MarketFacilitationIndexIndicator(Instrument instrument, String name) {
        super(instrument, name);
        double[] highs = instrument.highSeries().convertToArray();
        double[] lows = instrument.lowSeries().convertToArray();
        double[] volumes = instrument.volumeSeries().convertToArray();
        double[] ar = MarketStrength.marketFacilitationIndexOverPeriod(highs, lows, volumes);
        copyBackwords(ar);
    }

}


