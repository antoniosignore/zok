package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.utils.IndicatorUtils

public class TradeOscillatorIndicator extends Indicator {

    int smoothing;
    int smoothing2;

    public TradeOscillatorIndicator(TimeSeries series, String name, Integer param1, Integer param2) {
        super(series, name);
        smoothing = param1;
        smoothing2 = param2;
        build();
    }

    public void build() {
        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {
            if (!series.isEmpty(date)) {
                add(date, IndicatorUtils.TO(series, date, smoothing, smoothing2));

            }
            date = series.nextDate(date)
        }
    }


    public int getFirstIndicatorIndex() {

        return Math.max(smoothing, smoothing2)

    }

}


