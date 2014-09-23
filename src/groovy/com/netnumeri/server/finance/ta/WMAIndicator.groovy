package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class WMAIndicator extends Indicator {

    public WMAIndicator(TimeSeries series, String name, Integer smoothing) {
        super(series, name);

        double[] ad = MovingAverage.weightedXDayMovingAverage(series.convertToArray(), smoothing);
        copyBackwords(ad);
    }
}


