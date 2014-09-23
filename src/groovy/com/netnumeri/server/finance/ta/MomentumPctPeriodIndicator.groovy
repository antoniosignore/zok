package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class MomentumPctPeriodIndicator extends Indicator {

    public MomentumPctPeriodIndicator(TimeSeries series, String name, Integer period) {
        super(series, name);
        this.series = series
        double[] closes = series.convertToArray();
        double[] ar = Momentum.momentumPctPeriod(closes, period);
        copyBackwords(ar);
    }

}


