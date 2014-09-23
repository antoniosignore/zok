package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * Regressed value Reference: http://en.wikipedia.org/wiki/Least_squares
 */
public class LeastSquaresValue {

    public static double calculate(TimeSeries qh, Date date, int period) {
        int lastBar = qh.matrix.index(date);
        int firstBar = lastBar - period + 1;

        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        for (int bar = firstBar; bar <= lastBar; bar++) {
            double y = qh.matrix.value(bar);
            sumX += bar;
            sumY += y;
            sumXY += bar * y;
            sumXX += bar * bar;
        }

        double slope = period * sumXY - sumX * sumY;
        slope /= period * sumXX - sumX * sumX;
        double intercept = (sumY - slope * sumX) / period;
        double value = slope * lastBar + intercept;
        return value;
    }

}
