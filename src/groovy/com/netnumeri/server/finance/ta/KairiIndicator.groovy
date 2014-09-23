package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class KairiIndicator extends Indicator {

    public KairiIndicator(Instrument instrument, String name, Integer smoothing) {
        super(instrument, name);
        double[] closes = instrument.closeSeries().convertToArray();
        double[] ar = MovingAverage.kairi(closes, (int) smoothing);
        copyBackwords(ar);
    }

}


