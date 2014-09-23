package com.netnumeri.server.finance.ta

public class Oscillators implements Serializable {

    public static double moneyFlowIndex(double[] high, double[] low, double[] close, double[] volume) {
        if (high == null || high.length == 0) throw new IllegalArgumentException("The high array is empty.");
        if (low == null || low.length == 0) throw new IllegalArgumentException("The low array is empty.");
        if (close == null || close.length == 0) throw new IllegalArgumentException("The close array is empty.");
        if (volume == null || volume.length == 0) throw new IllegalArgumentException("The volume array is empty.");
        if (high.length != low.length || low.length != close.length || close.length != volume.length) throw new IllegalArgumentException("The three arrays do not have the same lengths.");
        for (int i = 0; i < high.length; i++) {
            if (high[i] < 0.0D) throw new IllegalArgumentException("The high array contains elements which are not positive.");
            if (low[i] < 0.0D) throw new IllegalArgumentException("The low array contains elements which are not positive.");
            if (close[i] < 0.0D) throw new IllegalArgumentException("The close array contains elements which are not positive.");
            if (volume[i] < 0.0D) throw new IllegalArgumentException("The volume array contains elements which are not positive.");
        }

        double[] ad4 = new double[high.length];
        double[] ad5 = new double[high.length];
        for (int j = 0; j < high.length; j++) {
            ad5[j] = typicalPrice(high[j], low[j], close[j]);
        }

        for (int k = 0; k < high.length; k++) {
            ad4[k] = ad5[k] * volume[k];
        }

        double d = 0.0D;
        double d1 = 0.0D;
        for (int l = 0; l < ad5.length - 1; l++) {
            if (ad5[l] > ad5[l + 1]) {
                d += ad4[l];
            } else if (ad5[l] < ad5[l + 1]) {
                d1 += ad4[l];
            }
        }

        if (d1 == 0.0D) {
            throw new IllegalArgumentException("The total negative money flow is zero.");
        } else {
            return d / d1;
        }
    }

    public static double[] moneyFlowIndexOverPeriod(double[] high, double[] low, double[] close, double[] volume, int period) {
        if (high.length != low.length || low.length != volume.length || volume.length != close.length) {
            throw new IllegalArgumentException("The length of the high, low, close, and volume series must be the same.");
        }
        if (period == 0 || period > high.length) {
            throw new IllegalArgumentException("The length of the period over which the indicatoer is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double[] ad4 = new double[low.length - period];
        double[] ad5 = new double[period];
        double[] ad6 = new double[period];
        double[] ad7 = new double[period];
        double[] ad8 = new double[period];
        for (int j = 0; j < low.length - period; j++) {
            for (int k = 0; k < period; k++) {
                ad5[k] = high[j + k];
                ad6[k] = low[j + k];
                ad7[k] = close[j + k];
                ad8[k] = volume[j + k];
            }
            ad4[j] = moneyFlowIndex(ad5, ad6, ad7, ad8);
        }
        return ad4;
    }

    public static double rateOfChange(double closePrice, double priceNDaysAgo) {
        if (closePrice < 0.0D) {
            throw new IllegalArgumentException("The closing price of an asset must have been positive.");
        }

        if (priceNDaysAgo < 0.0D) {
            throw new IllegalArgumentException("The price n days ago of an asset must have been positive.");
        } else {
            return closePrice / priceNDaysAgo;
        }
    }

    public static double[] rateOfChangePeriod(double[] closes, int noDays) {
        if (noDays > closes.length) {
            throw new IllegalArgumentException("The n-day ago price can not be found in the closingPrices array. The argument nDay must be less than the length of the closingPrices array");
        }
        double[] ad1 = new double[closes.length - noDays];
        for (int j = 0; j < closes.length - noDays; j++) {
            ad1[j] = rateOfChange(closes[j], closes[j + noDays]);
        }
        return ad1;
    }

    public static double[] relativeStrengthIndex(double[] values, int i) {
        validateStandardParameters(values, i);
        double[] ad1 = new double[values.length - i];
        double d = 0;
        double d2 = 0;
        double d4 = 0;
        double d6 = calculateAverageGain(values, ad1.length - 1, i);
        double d7 = calculateAverageLoss(values, ad1.length - 1, i);
        d = d6 / d7;
        ad1[ad1.length - 1] = 100D - 100D / (1.0D + d);
        for (int j = ad1.length - 2; j >= 0; j--) {
            double d3;
            double d5;
            if (values[j] - values[j + 1] > 0.0D) {
                d3 = values[j] - values[j + 1];
                d5 = 0.0D;
            } else {
                d3 = 0.0D;
                d5 = values[j + 1] - values[j];
            }
            d6 = (d6 * 13D + d3) / 14D;
            d7 = (d7 * 13D + d5) / 14D;
            double d1 = d6 / d7;
            ad1[j] = 100D - (100D / (1 + d1));
        }

        return ad1;
    }

    public static double typicalPrice(double d, double d1, double d2) {
        return (d + d1 + d2) / 3D;
    }

    private static void validateStandardParameters(double[] values, int noPeriods) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        }
        if (noPeriods < 1) {
            throw new IllegalArgumentException("The number of periods must be greater than one (" + noPeriods + " < 1)");
        }
        if (noPeriods > values.length) {
            throw new IllegalArgumentException("The number of periods must not exceed the length of the historical prices period (" + noPeriods + " > " + values.length + ")");
        }
    }

    private static double calculateAverageGain(double[] ad, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; k > i; k--) {
            if (ad[k] - ad[k - 1] < 0.0D) {
                d += ad[k - 1] - ad[k];
            }
        }
        return d / (double) j;
    }

    private static double calculateAverageLoss(double[] ad, int i, int j) {
        double d = 0.0D;
        for (int k = i + j; k > i; k--) {
            if (ad[k] - ad[k - 1] > 0.0D) {
                d += ad[k] - ad[k - 1];
            }
        }
        return d / (double) j;
    }

}
