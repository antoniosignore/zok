package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

public class ProbabilisticRSI {

    public static double calculate(TimeSeries closes, Date date, int periodLength) {

        int lastBar = closes.matrix.index(date);
        int firstBar = lastBar - periodLength + 1;

        double gains = 0, losses = 0;

        for (int barIndex = firstBar + 1; barIndex <= lastBar; barIndex++) {
            double bar = closes.getValue(barIndex);
            double prevBar = closes.getValue(barIndex - 1);
            double change = bar - prevBar

            gains += Math.max(0, change);
            losses += Math.max(0, -change);
        }

        double totalChange = gains + losses;

        double trend = totalChange == 0 ? 50 : 100 * gains / totalChange;
        // rescale so that the range is from -100 to +100
        double rsi = (trend - 50) * 2;
        double significance = 1 - probability(gains, totalChange);
        double value = rsi * significance;
        if (trend < 0) {
            value = -value;
        }

        return value;
    }

    /**
     * Stirling gamma function for approximating factorials.
     *
     * @param z
     *            Only works for 1 <= z <= 142 but also defined for non
     *            integers.
     *
     * @returns approximation to (z-1)!
     */
    private static double gamma(double z) {
        return Math.exp(-z) * Math.pow(z, z - 0.5) * Math.sqrt(2 * Math.PI) * (1 + 1 / (12 * z) + 1 / (288 * z * z) - 139 / (51840 * z * z * z) - 571 / (2488320 * z * z * z * z));
    }

    private static double probability(double r, double total) {
        double nom = gamma(total + 1);
        double denom = gamma(r + 1) * gamma(total - r + 1) * Math.pow(2, total);
        return nom / denom;
    }
}
