package com.netnumeri.server.finance.ta

public class MovingAverage implements Serializable {

    public static double[] simpleMovingAverage(double[] series, int order) {
        validateStandardMAParameters(series, order);
        double[] ad1 = new double[(series.length - order) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneSimpleMovingAverage(series, j, order);
        }
        return ad1;
    }

    public static double[] geometricMovingAverage(double[] series, int order) {
        validateStandardMAParameters(series, order);
        double[] ad1 = new double[(series.length - order) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneGeometricMovingAverage(series, j, order);
        }
        return ad1;
    }

    public static double[] linearlyWeightedMovingAverage(double[] series, int i) {
        validateStandardMAParameters(series, i);
        double[] ad1 = new double[(series.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneLinearlyWeightedMovingAverage(series, j, i);
        }
        return ad1;
    }

    public static double[] triangularMovingAverage(double[] series, int i) {
        validateStandardMAParameters(series, i);
        double[] ad1 = new double[(series.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneTriangularMovingAverage(series, j, i);
        }
        return ad1;
    }

    public static double[] exponentiallyWeightedMovingAverage(double[] series, double smoothing, int movingAverage) {
        validateStandardMAParameters(series, movingAverage);
        validateEWMASmoothingFactor(smoothing);
        double[] ad1 = new double[(series.length - movingAverage) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneExponentiallyWeightedMovingAverage(series, smoothing, j, movingAverage);
        }
        return ad1;
    }

    public static double[] weightedXDayMovingAverage(double[] series, double[] ad1) {
        validateWMAWeights(ad1);
        int i = ad1.length;
        validateStandardMAParameters(series, i);
        double d = 0.0D;
        for (int j = 0; j < i; j++) {
            d += ad1[j];
        }

        double[] ad2 = new double[(series.length - i) + 1];
        for (int k = ad2.length; --k >= 0;) {
            double d1 = 0.0D;
            int l = k + i;
            for (int i1 = i - 1; --l >= k; i1--) {
                d1 += ad1[i1] * series[l];
            }

            ad2[k] = d1 / d;
        }

        return ad2;
    }

    public static double[] weightedXDayMovingAverage(double[] series, double[] ad1, int i) {
        validateStandardMAParameters(series, i);
        validateWMAWeights(ad1);
        validateWMAParameters(series, ad1);
        double[] ad2 = new double[(series.length - i) + 1];
        for (int j = ad2.length; --j >= 0;) {
            double d = 0.0D;
            double d1 = 0.0D;
            for (int k = j + i; --k >= j;) {
                d += ad1[k] * series[k];
                d1 += ad1[k];
            }

            ad2[j] = d / d1;
        }

        return ad2;
    }

    public static double[] medianMovingAverage(double[] series, double[] ad1, int i) {
        validateStandardMAParameters(series, i);
        validateStandardMAParameters(ad1, i);
        int j = Math.min(series.length, ad1.length);
        double[] ad2 = new double[(j - i) + 1];
        for (int k = ad2.length; --k >= 0;) {
            ad2[k] = oneMedianMovingAverage(series, ad1, k, i);
        }

        return ad2;
    }

    public static double[] kairi(double[] series, double[] ad1) {
        validateKairiParameters(series, ad1);
        double[] ad2 = new double[series.length];
        int i = ad1.length - 1;
        for (int j = series.length; --j >= 0;) {
            ad2[j] = kairiIndicator(series[j], ad1[i]);
            i--;
        }

        return ad2;
    }

    public static int simpleCrossingSignal(double d, double d1, double d2, double d3) {
        if ((d > d2) & (d1 <= d3)) {
            return 1;
        }
        return !((d < d2) & (d1 >= d3)) ? 0 : -1;
    }

    public static double selectElementOfArray(double[] series, int i) {
        return series[i];
    }

    private static double oneSimpleMovingAverage(double[] series, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) {
            d += series[k];
        }
        return d / (double) j;
    }

    private static double oneGeometricMovingAverage(double[] series, int i, int j) {
        double d = 1.0D;
        for (int k = i + j; --k >= i;) {
            d *= series[k];
        }

        return Math.pow(d, 1.0D / (double) j);
    }

    private static double oneLinearlyWeightedMovingAverage(double[] series, int i, int j) {
        double d = 0.0D;
        int k = (j * (j + 1)) / 2;
        int l = i + j;
        for (int i1 = j; --l >= i; i1--) {
            d += series[l] * (double) i1;
        }

        return d / (double) k;
    }

    private static double oneTriangularMovingAverage(double[] series, int i, int j) {
        double d = 0.0D;
        int k = j / 2;
        int l = k * (k + 1);
        int i1 = 1;
        int j1 = i;
        for (int k1 = (i + j) - 1; i1 <= k; k1--) {
            d += (double) i1 * (series[j1] + series[k1]);
            i1++;
            j1++;
        }

        if (j % 2 == 1) {
            d += (double) (k + 1) * series[i + k];
            l += k + 1;
        }
        return d / (double) l;
    }

    public static double oneExponentiallyWeightedMovingAverage(double[] series, double d, int i, int j) {
        double d1 = 0.0D;
        double d2 = 1.0D - d;
        if (d == 0.0D) {
            return series[0];
        }
        if (d == 1.0D) {
            return series[i];
        }
        int k = i + j;
        for (int l = 0; --k >= i; l++) {
            d1 += series[k] * Math.pow(d2, l);
        }

        return d * d1;
    }

    private static double oneMedianMovingAverage(double[] series, double[] ad1, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) {
            d += (series[k] + ad1[k]) / 2D;
        }
        return d / (double) j;
    }

    public static double kairiIndicator(double d, double d1) {
        return (d - d1) / d1;
    }

    private static void validateStandardMAParameters(double[] series, int order) {
        if (series == null || series.length == 0) {
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        }
        if (order < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + order + " < 1)");
        if (order > series.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the historical prices period (" + order + " > " + series.length + ")");
        else return;
    }

    private static void validateEWMASmoothingFactor(double smoothing) {
        if (smoothing < 0.0D || smoothing > 1.0D)
            throw new IllegalArgumentException("The period factor must not be less than 0 or greater than 1");
        else return;
    }

    private static void validateWMAWeights(double[] weights) {
        if (weights == null || weights.length == 0)
            throw new IllegalArgumentException("The array of weights must contain at least one element");
        else return;
    }

    private static void validateWMAParameters(double[] ad, double[] ad1) {
        if (ad1.length < ad.length)
            throw new IllegalArgumentException("The number of weights must equal or be greater than the number of historical values (" + ad1.length + " < " + ad.length + ")");
        else return;
    }

    private static void validateKairiParameters(double[] historical, double[] movingAverages) {
        if (movingAverages == null || movingAverages.length == 0)
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        if (historical == null || historical.length == 0)
            throw new IllegalArgumentException("The array of moving averages must contain at least one element");
        if (movingAverages.length < historical.length)
            throw new IllegalArgumentException("The number of moving averages must be less than or equal to the number of historical values (" + historical.length + " > " + movingAverages.length + ")");
        else {
            return;
        }
    }

}
