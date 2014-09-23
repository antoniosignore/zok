package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class NormalizedSeriesIndicator extends Indicator {

    public NormalizedSeriesIndicator(TimeSeries series, String name) {
        super(series, name)
        series.normalize()
        copyBackwords(series.convertToArray());
    }
}


