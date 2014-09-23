package com.netnumeri.server.finance.ta;

public class Aroon {

    public static double aroonUp(double[] ad) {
        if (ad == null)
            throw new IllegalArgumentException("The high array is null.");
        if (ad.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        } else {
            double d = ad.length;
            int i = maxArrayPosition(ad);
            return ((d - (double) i) * 100D) / d;
        }
    }

    public static double[] aroonUpOverPeriod(double[] highs, int period) {
        if (highs == null)
            throw new IllegalArgumentException("The high array is null.");
        if (highs.length == 0)
            throw new IllegalArgumentException("The high array cannot be empty.");
        if (period <= 0 || period > highs.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluatuate" +
                    "d must be a strictly positive integer which is less than or equa" +
                    "l to the length of the array highs"
            );
        double[] ad1 = new double[(highs.length - period) + 1];
        double[] ad2 = new double[period];
        for (int j = 0; j < (highs.length - period) + 1; j++) {
            for (int k = 0; k < period; k++)
                ad2[k] = highs[j + k];

            ad1[j] = aroonUp(ad2);
        }

        return ad1;
    }

    public static double aroonDown(double[] lows) {
        if (lows == null)
            throw new IllegalArgumentException("The low array is null.");
        if (lows.length == 0) {
            throw new IllegalArgumentException("The low array is empty.");
        } else {
            double d = lows.length;
            int i = minArrayPosition(lows);
            return ((d - (double) i) * 100D) / d;
        }
    }

    public static double[] aroonDownOverPeriod(double[] lows, int period) {
        if (period <= 0 || period > lows.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluated " +
                    "must be a strictly positive integer which is less than or equal to the length of the array lows"
            );
        double[] ad1 = new double[(lows.length - period) + 1];
        double[] ad2 = new double[period];
        for (int j = 0; j < (lows.length - period) + 1; j++) {
            for (int k = 0; k < period; k++)
                ad2[k] = lows[j + k];

            ad1[j] = aroonDown(ad2);
        }

        return ad1;
    }

    public static double aroonOscillator(double d, double d1) {
        return d1 - d;
    }

    public static double aroonOscillator(double[] highs, double[] lows) {
        if (highs.length != lows.length)
            throw new IllegalArgumentException("The length of the highs and lows arrays must be the same");
        else
            return aroonUp(highs) - aroonDown(lows);
    }

    public static double[] aroonOscillatorOverPeriod(double[] aroonUpIndicators, double[] aroonDownIndicators) {
        if (aroonUpIndicators.length != aroonDownIndicators.length)
            throw new IllegalArgumentException("The length of the aroonUpIndicators and aroonDownIndicators series must be the same."
            );
        if (aroonUpIndicators.length == 0)
            throw new IllegalArgumentException("The aroonUpIndicators and aroonDownIndicators indicators  must not be empty."
            );
        double[] ad2 = new double[aroonUpIndicators.length];
        for (int i = 0; i < aroonUpIndicators.length; i++)
            ad2[i] = aroonOscillator(aroonUpIndicators[i], aroonDownIndicators[i]);
        return ad2;
    }

    public static double[] aroonOscillatorOverPeriod(double[] highs, double[] lows, int period) {
        if (highs.length != lows.length)
            throw new IllegalArgumentException("The length of the highs and lows series must be the same.");
        if (highs.length == 0) throw new IllegalArgumentException("The highs and lows arrays  must not be empty.");
        else
            return aroonOscillatorOverPeriod(aroonUpOverPeriod(highs, period), aroonDownOverPeriod(lows, period));
    }

    public static int maxArrayPosition(double[] ad)
    throws IllegalArgumentException {
        if (ad.length == 0 || ad == null)
            throw new IllegalArgumentException("The Array cannot be empty.");
        double d = ad[0];
        for (int i = 1; i < ad.length; i++)
            if (ad[i] > d)
                d = ad[i];

        int j = 0;
        for (j = 0; j < ad.length; j++)
            if (d == ad[j])
                break;

        return j;
    }

    public static int minArrayPosition(double[] ad)
    throws IllegalArgumentException {
        if (ad.length == 0 || ad == null)
            throw new IllegalArgumentException("The Array cannot be empty.");
        double d = ad[0];
        for (int i = 1; i < ad.length; i++)
            if (ad[i] < d)
                d = ad[i];

        int j = 0;
        for (j = 0; j < ad.length; j++)
            if (d == ad[j])
                break;

        return j;
    }

}

