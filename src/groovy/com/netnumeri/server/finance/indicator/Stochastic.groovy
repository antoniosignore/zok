package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * Stochastic oscillator %K About:
 * http://en.wikipedia.org/wiki/Stochastic_oscillator
 */
public class Stochastic {


    public static double calculate(TimeSeries opens,
                                   TimeSeries closes,
                                   TimeSeries highs,
                                   TimeSeries lows,
                                   TimeSeries volumes, Date date, int periodLength) {
        int endBar = opens.matrix.index(date);
        int startBar = endBar - periodLength;
        double max = 0;
        double min = lows.getValue(startBar)
        double last;
        for (int bar = startBar; bar <= endBar; bar++) {
            max = Math.max(highs.getValue(bar), max);
            min = Math.min(lows.getValue(bar), min);
        }
        last = closes.getValue(endBar);
        double value = (last - min) / (max - min) * 100;
        return value;
    }
}
