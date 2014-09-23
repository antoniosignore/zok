package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.utils.IndicatorUtils

public class MOMIndicator extends Indicator {

    int order;

    public MOMIndicator(TimeSeries series, String name, Integer param1) {

        super(series, name);
        order = param1;

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {

            if (!series.isEmpty(date)) {

                add(date, IndicatorUtils.MOM(series, date, order));

            }
            date = series.nextDate(date)
        }

    }


    public int getFirstIndicatorIndex() {

        return (int) order + 1;

    }

}


