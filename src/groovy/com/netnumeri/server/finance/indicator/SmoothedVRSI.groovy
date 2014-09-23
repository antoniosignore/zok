package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

public class SmoothedVRSI {
    public static double calculate(TimeSeries opens,
                                   TimeSeries closes,
                                   TimeSeries volumes,
                                   Date date, int lookBackPeriod,
                                   int smoothingPeriod) {
        double multiplier;
        multiplier = 2 / (smoothingPeriod + 1);

        int lastBar = opens.matrix.index(date);
        int firstBar = lastBar - lookBackPeriod + 1;

        double gains = 0, losses = 0;

        for (int bar = firstBar + 1; bar <= lastBar; bar++) {
            double change = closes.getValue(bar) - closes.getValue(bar - 1)
            double volume = volumes.getValue(bar)

            gains += (Math.max(0, change * volume) - gains) * multiplier;
            losses += (Math.max(0, -change * volume) - losses) * multiplier;
        }

        double change = gains + losses;

        double rsi = change == 0 ? 50 : 100 * gains / change;
        double value = rsi - 50;

        return value;
    }
}
