package com.netnumeri.server.finance.ta

public class AccumulateDistribute implements Serializable {

    public static double accumulationDistribution(double high, double low, double volume) {
        if (high < 0.0D || low < 0.0D || volume < 0.0D) {
            throw new IllegalArgumentException("Each of the three parameters high, low, volume must be strictly positive.");
        } else {
            return ((high - low) * volume) / high;
        }
    }

    public static double accumulationDistributionPeriod(double[] highs, double[] lows, double[] volume) {
        if (highs.length != lows.length || lows.length != volume.length) {
            throw new IllegalArgumentException("The three arrays highs, lows, volume must have the same length.");
        }
        for (int i = 0; i < highs.length; i++) {
            if (highs[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
            }
        }

        for (int j = 0; j < lows.length; j++) {
            if (lows[j] < 0.0D) {
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
            }
        }

        for (int k = 0; k < volume.length; k++) {
            if (volume[k] < 0.0D) {
                throw new IllegalArgumentException("An element of the volume array is a strictly negative number.");
            }
        }

        double d = 0.0D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        for (int l = 0; l < highs.length - 1; l++) {
            d = Math.max(highs[l], highs[l + 1]);
        }

        for (int i1 = 0; i1 < lows.length - 1; i1++) {
            d1 = Math.min(lows[i1], lows[i1 + 1]);
        }

        for (int j1 = 0; j1 < volume.length; j1++) {
            d2 += volume[j1];
        }

        return accumulationDistribution(d, d1, d2);
    }

    public static double[] accumulateDistributionOverPeriod(double[] highs, double[] lows, double[] volumes, int period) {
        if (highs.length != lows.length || lows.length != volumes.length) {
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        }
        if (period == 0 || period > highs.length) {
            throw new IllegalArgumentException("The length of the period over which the indicatoer is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double[] ad3 = new double[(lows.length - period) + 1];
        double[] ad4 = new double[period];
        double[] ad5 = new double[period];
        double[] ad6 = new double[period];
        for (int j = 0; j < (lows.length - period) + 1; j++) {
            for (int k = 0; k < period; k++) {
                ad4[k] = highs[j + k];
                ad5[k] = lows[j + k];
                ad6[k] = volumes[j + k];
            }

            ad3[j] = accumulationDistributionPeriod(ad4, ad5, ad6);
        }

        return ad3;
    }

    public static double accumulationDistributionChange(double d, double d1) {
        return d1 - d;
    }

    public static double accumulationDistributionPeriodChange(double d, double d1) {
        return d1 - d;
    }

    public static double chaikinOscillator(double[] highs, double[] lows, double[] volume, double smoothing) {
        if (highs.length != lows.length || lows.length != volume.length)
            throw new IllegalArgumentException("The three arrays high, low, volume must has the same length");
        if (smoothing < 0.0D || smoothing > 1.0D)
            throw new IllegalArgumentException("The value given for the period factor must lie within the closed interval [0,1].");
        if (highs.length < 10)
            throw new IllegalArgumentException("The high parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (lows.length < 10)
            throw new IllegalArgumentException("The low parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (volume.length < 10)
            throw new IllegalArgumentException("The volume parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        double[] ad3 = new double[3];
        double[] ad4 = new double[3];
        double[] ad5 = new double[3];
        for (int i = 0; i < 3; i++) {
            ad3[i] = highs[i];
            ad4[i] = lows[i];
            ad5[i] = volume[i];
        }

        double[] ad6 = new double[10];
        double[] ad7 = new double[10];
        double[] ad8 = new double[10];
        for (int j = 0; j < 10; j++) {
            ad6[j] = highs[j];
            ad7[j] = lows[j];
            ad8[j] = volume[j];
        }

        double[] ad9 = new double[10];
        double[] ad10 = new double[3];
        for (int k = 0; k < 3; k++) {
            ad10[k] = accumulationDistribution(ad3[k], ad4[k], ad5[k]);
        }

        for (int l = 0; l < 10; l++) {
            ad9[l] = accumulationDistribution(ad6[l], ad7[l], ad8[l]);
        }

        return exponentiallyWeightedMovingAverage(ad10, smoothing) - exponentiallyWeightedMovingAverage(ad9, smoothing);
    }

    public static double[] chaikinOscillatorOverPeriod(double[] highs, double[] lows, double[] volumes, double period) {
        if (period < 0.0D || period > 1.0D)
            throw new IllegalArgumentException("The value given for the period factor must lie within the closed interval [0,1].");
        if (highs.length < 10)
            throw new IllegalArgumentException("The high parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (lows.length < 10)
            throw new IllegalArgumentException("The low parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (volumes.length < 10)
            throw new IllegalArgumentException("The volume parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (highs.length != lows.length || lows.length != volumes.length)
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        byte byte0 = 10;
        if (byte0 == 0 || byte0 > highs.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        double[] ad3 = new double[(lows.length - byte0) + 1];
        double[] ad4 = new double[byte0];
        double[] ad5 = new double[byte0];
        double[] ad6 = new double[byte0];
        for (int i = 0; i < (lows.length - byte0) + 1; i++) {
            for (int j = 0; j < byte0; j++) {
                ad4[j] = highs[i + j];
                ad5[j] = lows[i + j];
                ad6[j] = volumes[i + j];
            }
            ad3[i] = chaikinOscillator(ad4, ad5, ad6, period);
        }
        return ad3;
    }

    public static double chaikinMoneyFlow(double[] highs, double[] lows, double[] closes, double[] volumes) {
        if (highs.length != lows.length || lows.length != closes.length || closes.length != volumes.length) {
            throw new IllegalArgumentException("The three arrays high, low, close, volume do not have the same length");
        }
        for (int i = 0; i < highs.length; i++) {
            if (highs[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
            }
        }

        for (int j = 0; j < lows.length; j++) {
            if (lows[j] < 0.0D) {
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
            }
        }

        for (int k = 0; k < closes.length; k++) {
            if (closes[k] < 0.0D) {
                throw new IllegalArgumentException("An element of the close array is a strictly negative number.");
            }
        }

        for (int l = 0; l < volumes.length; l++) {
            if (volumes[l] < 0.0D) {
                throw new IllegalArgumentException("An element of the volume array is a strictly negative number.");
            }
        }

        double[] ad4 = new double[highs.length];
        for (int i1 = 0; i1 < highs.length; i1++) {
            ad4[i1] = ((closes[i1] - lows[i1] - (highs[i1] - closes[i1])) / (highs[i1] - lows[i1])) * volumes[i1];
        }

        double d = 0.0D;
        double d1 = 0.0D;
        for (int j1 = 0; j1 < highs.length; j1++) {
            d += ad4[j1];
            d1 += volumes[j1];
        }

        return d / d1;
    }

    public static double[] chaikinMoneyFlowOverPeriod(double[] highs, double[] lows, double[] closes, double[] volumes, int period) {
        if (highs.length != lows.length || lows.length != volumes.length) {
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        }
        if (period == 0 || period > highs.length) {
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double[] ad4 = new double[(lows.length - period) + 1];
        double[] ad5 = new double[period];
        double[] ad6 = new double[period];
        double[] ad7 = new double[period];
        double[] ad8 = new double[period];
        for (int j = 0; j < (lows.length - period) + 1; j++) {
            for (int k = 0; k < period; k++) {
                ad5[k] = highs[j + k];
                ad6[k] = lows[j + k];
                ad7[k] = closes[j + k];
                ad8[k] = volumes[j + k];
            }

            ad4[j] = chaikinMoneyFlow(ad5, ad6, ad7, ad8);
        }

        return ad4;
    }

    private static double exponentiallyWeightedMovingAverage(double[] series, double smoothing) {
        if (series.length == 0 || series == null) throw new IllegalArgumentException("The timeSeries array is empty.");
        if (smoothing < 0.0D || smoothing > 1.0D)
            throw new IllegalArgumentException("The period factor does not lie in the closed range [0.1].");
        double d1 = 0.0D;
        for (int i = 0; i < series.length; i++) {
            d1 += smoothing * Math.pow(1.0D - smoothing, i) * series[(-1 + series.length) - i];
        }
        return d1;
    }
}