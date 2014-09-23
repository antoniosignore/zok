package com.netnumeri.server.finance.ta;


public class BollingerBands implements Serializable {

    public static double lowerBollingerBand(double[] historical, double deviation) {
        int i = historical.length;
        double[] ad1 = new double[1];
        ad1 = lowerBollingerBands(historical, deviation, i);
        return ad1[0];
    }

    public static double[] lowerBollingerBands(double[] historical, double deviation, int movingaverage) {
        validateParameters(historical, deviation, movingaverage);
        double[] ad1 = MovingAverage.simpleMovingAverage(historical, movingaverage);
        for (int j = ad1.length; --j >= 0;) {
            double d1 = sampleStdDeviation(historical, j, movingaverage);
            ad1[j] -= deviation * d1;
        }
        return ad1;
    }

    public static double upperBollingerBand(double[] ad, double d) {
        int i = ad.length;
        double[] ad1 = new double[1];
        ad1 = upperBollingerBands(ad, d, i);
        return ad1[0];
    }

    public static double[] upperBollingerBands(double[] historical, double deviation, int movingaverage) {
        validateParameters(historical, deviation, movingaverage);
        double[] ad1 = MovingAverage.simpleMovingAverage(historical, movingaverage);
        for (int j = ad1.length; --j >= 0;) {
            double d1 = sampleStdDeviation(historical, j, movingaverage);
            ad1[j] += deviation * d1;
        }

        return ad1;
    }

    private static double sampleVariance(double[] ad, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) {
            d += ad[k];
        }

        double d1 = d / (double) (j + 1);
        d = 0.0D;
        for (int l = i + j; --l >= i;) {
            d += (ad[l] - d1) * (ad[l] - d1);
        }

        return d / (double) j;
    }

    private static double sampleStdDeviation(double[] ad, int i, int j) {
        return Math.sqrt(sampleVariance(ad, i, j));
    }

    private static void validateParameters(double[] historial, double deviation, int movingaverage) {
        if (historial == null || historial.length == 0) {
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        }
        if (deviation < 0.0D) {
            throw new IllegalArgumentException("The standard deviation level must be a positive number (" + deviation + " < 0)");
        }
        if (movingaverage < 1) {
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + movingaverage + " < 1)");
        } else {
            return;
        }
    }
}
