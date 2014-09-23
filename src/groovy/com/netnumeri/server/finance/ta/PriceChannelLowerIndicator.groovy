package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.utils.IndicatorUtils

public class PriceChannelLowerIndicator extends Indicator {

    int order
    double k

    public PriceChannelLowerIndicator(TimeSeries series, String name, int order, double k) {
        super(series, name);
        this.order = order;
        this.k = k;

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {
            if (!series.isEmpty(date)) {
                add(date, IndicatorUtils.PCL(series, date, order, k));
            }
            date = series.nextDate(date)
        }
    }

    public int getFirstIndicatorIndex() {

        return (int) order;

    }

}


