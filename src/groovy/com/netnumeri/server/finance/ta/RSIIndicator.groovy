package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class RSIIndicator extends Indicator {

    int periodLength

    public RSIIndicator(TimeSeries series, String name, Integer periodLength) {

        super(series, name);
        this.periodLength = periodLength;

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {

            if (!series.isEmpty(date)) {

                add(date, calculate(series, date, periodLength))
            }
            date = series.nextDate(date)
        }

    }

    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        int lastBar = qh.matrix.index(date);
        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0;

        for (int bar = firstBar + 1; bar <= lastBar; bar++) {

            double change = qh.matrix.value(bar) - qh.matrix.value(bar - 1);
            gains += Math.max(0, change);
            losses += Math.max(0, -change);
        }

        double change = gains + losses;
        double value = (change == 0) ? 50 : (100 * gains / change);
        return value;
    }

    public int getFirstIndicatorIndex() {
        return (int) periodLength + 1;
    }

}


