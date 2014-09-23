package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * Weighted Moving Average.
 */
public class WMA {
    private final int length;

    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        int endBar = qh.matrix.index(date);
        int startBar = endBar - periodLength;
        double wma = 0;

        for (int bar = startBar; bar <= endBar; bar++) {
            wma += qh.matrix.value(bar) * (bar + 1);
        }

        return wma / (periodLength * (periodLength + 1) / 2);
    }
}
