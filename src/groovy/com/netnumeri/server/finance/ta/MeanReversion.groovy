package com.netnumeri.server.finance.ta

public class MeanReversion implements Serializable {

    public static double commodityChannelIndex(double[] ad, double[] ad1, double[] ad2) {
        if (ad.length != ad1.length || ad1.length != ad2.length)
            throw new IllegalArgumentException("The three arrays high, low, close do not have the same length.");
        for (int i = 0; i < ad.length; i++)
            if (ad[i] < 0.0D)
                throw new IllegalArgumentException("The `high' array contains an element which is not positive.");

        for (int j = 0; j < ad1.length; j++)
            if (ad1[j] < 0.0D)
                throw new IllegalArgumentException("The `low' array contains an element which is not positive.");

        for (int k = 0; k < ad2.length; k++)
            if (ad2[k] < 0.0D)
                throw new IllegalArgumentException("The `close' array contains an element which is not positive.");

        double[] ad3 = new double[ad.length];
        for (int l = 0; l < ad.length; l++) {
            ad3[l] = typicalPrice(ad[l], ad1[l], ad2[l]);
        }

        double d = simpleMovingAverage(ad3);
        double[] ad4 = new double[ad.length];
        for (int i1 = 0; i1 < ad3.length; i1++) {
            ad4[i1] = Math.abs(typicalPrice(ad[i1], ad1[i1], ad2[i1]) - d);
        }

        double d1 = 0.015D * simpleMovingAverage(ad4);
        return (typicalPrice(ad[0], ad1[0], ad2[0]) - d) / d1;
    }

    public static double[] commodityChannelIndexOverPeriod(double[] highs, double[] lows, double[] volumes, int period) {
        if (highs.length != lows.length || lows.length != volumes.length)
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        if (period == 0 || period > highs.length)
            throw new IllegalArgumentException("The length of the period over which the indicatoer is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        for (int j = 0; j < highs.length; j++)
            if (highs[j] < 0.0D)
                throw new IllegalArgumentException("The `high' array contains an element which is not positive.");

        for (int k = 0; k < lows.length; k++)
            if (lows[k] < 0.0D)
                throw new IllegalArgumentException("The `low' array contains an element which is not positive.");

        for (int l = 0; l < volumes.length; l++) {
            if (volumes[l] < 0.0D)
                throw new IllegalArgumentException("The `close' array contains an element which is not positive.");
        }

        double[] ad3 = new double[(lows.length - period) + 1];
        double[] ad4 = new double[period];
        double[] ad5 = new double[period];
        double[] ad6 = new double[period];
        for (int i1 = 0; i1 < (lows.length - period) + 1; i1++) {
            for (int j1 = 0; j1 < period; j1++) {
                ad4[j1] = highs[i1 + j1];
                ad5[j1] = lows[i1 + j1];
                ad6[j1] = volumes[i1 + j1];
            }
            ad3[i1] = commodityChannelIndex(ad4, ad5, ad6);
        }
        return ad3;
    }

    public static double simpleMovingAverage(double[] ad) {
        if (ad == null || ad.length == 0) {
            throw new IllegalArgumentException("The array historicalPrice is empty.");
        }
        double d = 0.0D;
        for (int i = 0; i < ad.length; i++) {
            d += ad[i];
        }
        return d / (double) ad.length;
    }

    public static double typicalPrice(double d, double d1, double d2) {
        return (d + d1 + d2) / 3D;
    }
}

