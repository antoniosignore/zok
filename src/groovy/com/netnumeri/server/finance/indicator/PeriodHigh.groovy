package com.netnumeri.server.finance.indicator;

import com.netnumeri.server.finance.beans.TimeSeries;

public class PeriodHigh {

    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        int periodStart = qh.size() - periodLength;
        int periodEnd = qh.size() - 1;
        double high = qh.getValue(periodStart)

        for (int bar = periodStart + 1; bar <= periodEnd; bar++) {
            double barHigh = qh.getValue(bar)
            if (barHigh > high) {
                high = barHigh;
            }
        }

        double value = high;
        return value;
    }
}
