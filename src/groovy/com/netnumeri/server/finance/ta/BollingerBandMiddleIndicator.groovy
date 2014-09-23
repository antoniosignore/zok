package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class BollingerBandMiddleIndicator extends Indicator {

    int length
    double deviation

    public BollingerBandMiddleIndicator(TimeSeries series, String name, Integer param1, double deviation) {

        super(series, name);
        length = param1;
        this.deviation = deviation;

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {

            if (!series.isEmpty(date)) {

                add(date, calculate(series, date, length));
            }
            date = series.nextDate(date)
        }

    }

    public static double calculate(TimeSeries qh, Date date, int length) {

        int lastBar = qh.matrix.index(date);

        int firstBar = lastBar - length + 1;
        double sum = 0;
        for (int bar = firstBar; bar <= lastBar; bar++) {
            sum += qh.matrix.value(bar);
        }

        return sum / length;
    }


    public int getFirstIndicatorIndex() {

        return (int) length + 1;

    }

}


