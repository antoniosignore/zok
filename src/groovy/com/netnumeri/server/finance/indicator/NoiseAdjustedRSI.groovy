package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

public class NoiseAdjustedRSI {

    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        int lastBar = qh.matrix.index(date);

        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0;

        for (int bar = firstBar + 1; bar <= lastBar; bar++) {
            double change = qh.getValue(bar); -qh.getValue(bar - 1)
            gains += Math.max(0, change);
            losses += Math.max(0, -change);
        }

        double change = gains + losses;

        double rsi = change == 0 ? 50 : 100 * gains / change;
        double value = (rsi - 50) * Math.sqrt(periodLength - 1);

        return value;
    }
}
