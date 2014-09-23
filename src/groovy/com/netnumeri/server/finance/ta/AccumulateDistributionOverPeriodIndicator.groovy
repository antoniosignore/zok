package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument


public class AccumulateDistributionOverPeriodIndicator extends Indicator {

    int smoothing;

    public AccumulateDistributionOverPeriodIndicator(Instrument instrument, String name, Integer smoothing) {
        super(instrument, name);
        this.smoothing = smoothing;
        double[] ad = AccumulateDistribute.accumulateDistributionOverPeriod(
                instrument.highSeries().convertToArray(),
                instrument.lowSeries().convertToArray(),
                instrument.volumeSeries().convertToArray(), smoothing);
        copyBackwords(ad);
    }

}


