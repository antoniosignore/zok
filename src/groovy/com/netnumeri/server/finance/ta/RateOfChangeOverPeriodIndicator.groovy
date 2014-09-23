package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class RateOfChangeOverPeriodIndicator extends Indicator {

    public RateOfChangeOverPeriodIndicator(TimeSeries series, String name, Integer period) {
        super(series, name);
        this.series = series
        double[] ad = Oscillators.rateOfChangePeriod(series.convertToArray(), (int) period);
        copyBackwords(ad);
    }

}


