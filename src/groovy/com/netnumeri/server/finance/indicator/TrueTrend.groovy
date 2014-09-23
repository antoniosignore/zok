package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * TrueTrend
 */
public class TrueTrend {

    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        int lastBar = qh.matrix.index(date);

        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0;

        for (int barIndex = firstBar + 1; barIndex <= lastBar; barIndex++) {

            double bar = qh.getValue(barIndex);
            double prevBar = qh.getValue(barIndex - 1);

            // double barMidPoint = (bar.getHigh() + bar.getLow()) / 2;
            // double prevBarMidPoint = (prevBar.getHigh() + prevBar.getLow()) /
            // 2;

            // double midPointChange = barMidPoint - prevBarMidPoint;
            double closeChange = bar - prevBar
            // double volume = bar.getVolume();

            // double trueChange = (closeChange + midPointChange) / 2;
            double change = closeChange;// * volume;// * volume;

            gains += Math.max(0, change);
            losses += Math.max(0, -change);
        }

        double totalChange = gains + losses;

        double trend = totalChange == 0 ? 0 : 100 * gains / totalChange;
        // rescale so that the range is from -100 to +100
        double value = (trend - 50) * 2;

        return value;
    }
}
