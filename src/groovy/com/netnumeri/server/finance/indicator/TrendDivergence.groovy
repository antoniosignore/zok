package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * Trend divergence indicator
 */
public class TrendDivergence {

    public static double calculate(TimeSeries qh, Date date, int shorterPeriod, int longerPeriod) {
        double shorterRSI = calculate(qh, date, shorterPeriod);
        double longerRSI = calculate(qh, date, longerPeriod);
        double value = shorterRSI - longerRSI;
        return value;
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

}
