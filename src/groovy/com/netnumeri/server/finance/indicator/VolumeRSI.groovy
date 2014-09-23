package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

public class VolumeRSI {

    public static double calculate(TimeSeries opens,
                                   TimeSeries closes,
                                   TimeSeries highs,
                                   TimeSeries lows,
                                   TimeSeries volumes, Date date, int periodLength) {
        int lastBar = opens.matrix.index(date);
        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0;

        for (int bar = firstBar + 1; bar <= lastBar; bar++) {
            double change = closes.getValue(bar) - closes.getValue(bar - 1);
            long volume = volumes.getValue(bar);

            gains += Math.max(0, change * volume);
            losses += Math.max(0, -change * volume);
        }

        double change = gains + losses;

        double rsi = change == 0 ? 50 : 100 * gains / change;
        double value = (rsi - 50) * 2;

        return value;
    }
}
