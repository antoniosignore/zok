package com.netnumeri.server.finance.ta;

public class Momentum {

    public static double momentumIndicator(double d, double d1) {
        if (d < 0.0D) throw new IllegalArgumentException("The closing price of an asset must have been positive.");
        if (d1 < 0.0D) throw new IllegalArgumentException("The price n days ago of an asset must have been positive.");
        else return d - d1;
    }

    public static double[] momentumPeriod(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double[] ad1 = new double[ad.length - i];
        for (int j = 0; j < ad.length - i; j++) {
            ad1[j] = momentumIndicator(ad[j], ad[j + i]);
        }
        return ad1;
    }

    public static double momentumPct(double d, double d1) {
        if (d < 0.0D) throw new IllegalArgumentException("The closing price of an asset must have been positive.");
        if (d1 < 0.0D) throw new IllegalArgumentException("The price n days ago of an asset must have been positive.");
        else return 100D * (d / d1);
    }

    public static double[] momentumPctPeriod(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double[] ad1 = new double[ad.length - i];
        for (int j = 0; j < ad.length - i; j++) {
            ad1[j] = momentumPct(ad[j], ad[j + i]);
        }
        return ad1;
    }

    public static double highest(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double d = 0.0D;
        for (int j = 0; j < i; j++) {
            if (ad[j] > d) {
                d = ad[j];
            }
        }
        return d;
    }

    public static double[] highestPeriod(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double[] ad1 = new double[i];
        double[] ad2 = new double[ad.length - i];
        for (int j = 0; j < ad.length - i; j++) {
            for (int k = j; k < j + i; k++) ad1[k - j] = ad[k];
            ad2[j] = highest(ad1, i);
        }
        return ad2;
    }

    public static double lowest(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double d = ad[0];
        for (int j = 1; j < i; j++) {
            if (ad[j] < d) d = ad[j];
        }
        return d;
    }

    public static double[] lowestPeriod(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double[] ad1 = new double[i];
        double[] ad2 = new double[ad.length - i];
        for (int j = 0; j < ad.length - i; j++) {
            for (int k = j; k < j + i; k++) ad1[k - j] = ad[k];
            ad2[j] = lowest(ad1, i);
        }
        return ad2;
    }

    public static int highestPosition(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double d = 0.0D;
        int j = 0;
        for (int k = 0; k < i; k++) {
            if (ad[k] > d) {
                d = ad[k];
                j = k;
            }
        }

        return j;
    }

    public static int lowestPosition(double[] ad, int i) {
        validateMomentumStandardParameters(ad, i);
        double d = ad[0];
        int j = 0;
        for (int k = 1; k < i; k++) {
            if (ad[k] < d) {
                d = ad[k];
                j = k;
            }
        }
        return j;
    }

    public static double trendIntensityIndex(double[] ad, int i) {
        double d;
        double d1;
        validateTTIParameters(ad, i);
        double[] ad1 = simpleMovingAverage(ad, i);
        d = 0.0D;
        d1 = 0.0D;
        for (int j = 0; j < ad1.length; j++) {
            if (ad[j] - ad1[j] > 0.0D) d += ad[j] - ad1[j];
            else d1 += ad1[j] - ad[j];
        }

        return (d / (d + d1)) * 100D;
    }

    private static double[] simpleMovingAverage(double[] ad, int i) {
        validateStandardMAParameters(ad, i);
        double[] ad1 = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) ad1[j] = oneSimpleMovingAverage(ad, j, i);
        return ad1;
    }

    private static double oneSimpleMovingAverage(double[] ad, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) d += ad[k];
        return d / (double) j;
    }

    private static void validateStandardMAParameters(double[] ad, int i) {
        if (ad == null || ad.length == 0)
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        if (i < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + i + " < 1)");
        if (i > ad.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the historical prices period (" + i + " > " + ad.length + ")");
        else return;
    }

    private static void validateTTIParameters(double[] prices, int i) {
        if (prices == null || prices.length == 0)
            throw new IllegalArgumentException("The array of prices must contain at least one element");
        for (int j = 0; j < prices.length; j++)
            if (prices[j] < 0.0D)
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
        if (i < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + i + " < 1)");
        if (i > prices.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the prices period");
        else return;
    }

    private static void validateMomentumStandardParameters(double[] highs, int i) {
        if (highs == null || highs.length == 0)
            throw new IllegalArgumentException("The array of prices must contain at least one element");
        for (int j = 0; j < highs.length; j++)
            if (highs[j] < 0.0D)
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
        if (i < 0)
            throw new IllegalArgumentException("The nDay parameter must me a strictly positive number (" + i + "< 0)");
        if (i > highs.length)
            throw new IllegalArgumentException("The nDay value must not exceed the length of the prices period");
        else return;
    }
}
