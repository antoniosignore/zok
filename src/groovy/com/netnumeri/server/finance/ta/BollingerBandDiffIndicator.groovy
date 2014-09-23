package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

public class BollingerBandDiffIndicator extends Indicator {

    int length
    double deviation

    public BollingerBandDiffIndicator(TimeSeries series, String name, Integer param1, double deviation) {
        super(series, name);
        length = param1;
        this.deviation = deviation;
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
                add(date, calculate(series, date, length, deviation) - calculateLower(series, date, length, deviation));
            }
            date = series.nextDate(date)
        }
    }

    public static double calculateLower(TimeSeries qh, Date date, int length, double deviations) {

        int lastBar = qh.matrix.index(date);

        double squareSum = 0;
        double sum = 0;

        int firstBar = lastBar - length + 1;

        for (int bar = firstBar; bar <= lastBar; bar++) {
            double barClose = qh.matrix.value(bar);
            sum += barClose;
            squareSum += barClose * barClose;
        }

        double stDev = length * squareSum - sum * sum;
        stDev /= length * (length - 1);
        stDev = Math.sqrt(stDev);

        return sum / length - deviations * stDev;
    }

    public static double calculate(TimeSeries qh, Date date, int length, double deviations) {
        int lastBar = qh.matrix.index(date);

        int firstBar = lastBar - length + 1;
        double squareSum = 0;
        double sum = 0;

        for (int bar = firstBar; bar <= lastBar; bar++) {
            double barClose = qh.matrix.value(bar);
            sum += barClose;
            squareSum += barClose * barClose;
        }

        double stDev = length * squareSum - sum * sum;
        stDev /= length * (length - 1);
        stDev = Math.sqrt(stDev);

        return sum / length + deviations * stDev;
    }

    public int getFirstIndicatorIndex() {

        return (int) length + 1;

    }

}


