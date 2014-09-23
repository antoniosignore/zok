package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.indicator.Averages

public class RSI2Indicator extends Indicator {

    int periodLength;

    public RSI2Indicator(TimeSeries series, String name, Integer param1) {

        super(series, name);
        periodLength = param1;
        build();

    }

    public void build() {

        Date date = series.firstDate()
        int index = 0;
        while (index < getFirstIndicatorIndex()) {
            date = series.nextDate(date);
            index++;
        }

        while (date != null) {

            if (!series.isEmpty(date)) {

                add(date, calculate(series, date, periodLength))
            }
            date = series.nextDate(date)
        }

    }

    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        Stack<Averages> avgList;
        avgList = new Stack<Averages>();

        int lastBar = qh.matrix.index(date);
        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0, avgUp = 0, avgDown = 0;

        double delta = qh.getValue(lastBar) - qh.getValue(lastBar - 1)
        gains = Math.max(0, delta);
        losses = Math.max(0, -delta);

        if (avgList.isEmpty()) {
            for (int bar = firstBar + 1; bar <= lastBar; bar++) {
                double change = qh.getValue(bar) - qh.getValue(bar - 1)
                gains += Math.max(0, change);
                losses += Math.max(0, -change);
            }
            avgUp = gains / periodLength;
            avgDown = losses / periodLength;
            avgList.push(new Averages(avgUp, avgDown));

        } else {

            Averages avg = avgList.pop();
            avgUp = avg.getAvgUp();
            avgDown = avg.getAvgDown();
            avgUp = (avgUp * (periodLength - 1) + gains) / periodLength;
            avgDown = (avgDown * (periodLength - 1) + losses) / periodLength;
            avgList.add(new Averages(avgUp, avgDown));
        }
        double value = 100 - 100 / (1 + avgUp / avgDown);

        return value;
    }


    public int getFirstIndicatorIndex() {

        return (int) periodLength + 1;

    }

}


