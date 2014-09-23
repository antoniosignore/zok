package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

public class PeriodLow {

    public static double calculate(TimeSeries lows, Date date, int periodLength) {

        int periodEnd = lows.matrix.index(date);
        int periodStart = lows.size() - periodLength;

        double low = lows.getValue(periodStart)

        for (int bar = periodStart + 1; bar <= periodEnd; bar++) {
            double barLow = lows.getValue(bar)
            if (barLow < low) {
                low = barLow;
            }
        }

        double value = low;
        return value;
    }
}
