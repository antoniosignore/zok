package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.utils.IndicatorUtils

public class CommodityChannelIndicator extends Indicator {

    int order

    public CommodityChannelIndicator(TimeSeries series, String name, Integer order) {
        super(series, name);
        this.order = order;

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {
            if (!series.isEmpty(date)) {
                add(date, IndicatorUtils.CCI(series, date, order));
            }
            date = series.nextDate(date)
        }
    }

    public int getFirstIndicatorIndex() {
        return (int) order;
    }
}


