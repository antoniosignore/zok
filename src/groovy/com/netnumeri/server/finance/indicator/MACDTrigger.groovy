package com.netnumeri.server.finance.indicator

import com.netnumeri.server.finance.beans.TimeSeries;

public class MACDTrigger {

    public static double calculate(TimeSeries qh, Date date, int triggerLength, double multiplier) {

        int lastBar = qh.matrix.index(date);
        multiplier = 2 / (triggerLength + 1);
        int firstBar = lastBar - 2 * triggerLength;

        double trigger = qh.matrix.value(firstBar);
        for (int bar = firstBar + 1; bar <= lastBar; bar++) {
            double macd = qh.matrix.value(bar);
            trigger += (macd - trigger) * multiplier;
        }

        double value = trigger;
        return value;
    }
}
