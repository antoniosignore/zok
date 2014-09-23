package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * MACD
 */
public class MACD {

    public static double calculate(TimeSeries qh, Date date, int fastLength, int slowLength) {
        double fastEMA = calculate(qh, date, fastLength)
        double slowEMA = calculate(qh, date, slowLength)
        double value = fastEMA - slowEMA;

        return value;
    }

    public static double calculate(TimeSeries qh, Date date, int length) {
        double multiplier = 2 / (length + 1);
        int lastBar = qh.matrix.index(date);
        int firstBar = lastBar - 2 * length + 1;
        double ema = qh.matrix.value(firstBar)
        for (int bar = firstBar; bar <= lastBar; bar++) {
            double barClose = qh.matrix.value(bar)
            ema += (barClose - ema) * multiplier;
        }
        return ema;
    }
}
