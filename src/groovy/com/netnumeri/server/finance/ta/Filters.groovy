package com.netnumeri.server.finance.ta

public class Filters implements Serializable {

    public static double typicalPrice(double d, double d1, double d2) {
        if (d1 < 0.0D) {
            throw new IllegalArgumentException("The low price must be a positive number.");
        }
        if (d < 0.0D) {
            throw new IllegalArgumentException("The high price must be a positive number.");
        }
        if (d2 < 0.0D) {
            throw new IllegalArgumentException("The closing price must be a positive number.");
        } else {
            return (d + d1 + d2) / 3D;
        }
    }

    public static double[] typicalPriceOverPeriod(double[] highs, double[] lows, double[] closes) {
        if (highs.length != lows.length || lows.length != closes.length) {
            throw new IllegalArgumentException("The three arrays highs, lows, closes  must have the same length.");
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
                throw new IllegalArgumentException("An element of the closes array is a strictly negative number.");
            }
        }

        double[] ad3 = new double[highs.length];
        for (int l = 0; l < highs.length; l++) {
            ad3[l] = (highs[l] + lows[l] + closes[l]) / 3D;
        }

        return ad3;
    }

    public static double medianPrice(double d, double d1) {
        if (d1 < 0.0D) {
            throw new IllegalArgumentException("The low price must be a positive number.");
        }
        if (d < 0.0D) {
            throw new IllegalArgumentException("The high price must be a positive number.");
        } else {
            return (d + d1) / 2D;
        }
    }

    public static double[] medianPriceOverPeriod(double[] highs, double[] lows) {
        if (highs.length != lows.length) {
            throw new IllegalArgumentException("The two  arrays highs, lows must have the same length.");
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

        double[] ad2 = new double[highs.length];
        for (int k = 0; k < highs.length; k++) {
            ad2[k] = (highs[k] + lows[k]) / 2D;
        }

        return ad2;
    }

    public static double averagePrice(double d, double d1, double d2, double d3) {
        if (d2 < 0.0D || d3 < 0.0D || d < 0.0D || d1 < 0.0D) {
            throw new IllegalArgumentException("All method's parameteres must be positive.");
        } else {
            return (d + d1 + d2 + d3) / 4D;
        }
    }

    public static double[] averagePriceOverPeriod(double[] ad, double[] ad1, double[] ad2, double[] ad3) {
        validateStandardParameters(ad, ad1, ad2, ad3);
        double[] ad4 = new double[ad.length];
        for (int i = 0; i < ad.length; i++) {
            ad4[i] = averagePrice(ad[i], ad1[i], ad2[i], ad3[i]);
        }

        return ad4;
    }

    public static double priceAction(double high, double low, double close, double open) {
        if (close < 0.0D || open < 0.0D || high < 0.0D || low < 0.0D) {
            throw new IllegalArgumentException("All method's parameteres must be positive.");
        } else {
            return ((open - close) + (open - high) + (open - low)) / 2D;
        }
    }


    public static double[] priceActionOverPeriod(double[] highs, double[] lows, double[] closes, double[] opens) {
        validateStandardParameters(highs, lows, closes, opens);
        double[] ad4 = new double[highs.length];
        for (int i = 0; i < highs.length; i++) {
            ad4[i] = priceAction(highs[i], lows[i], closes[i], opens[i]);
        }

        return ad4;
    }

    public static double[] finiteImpulseResponse(double[] closes, double[] weights) {
        if (closes.length - weights.length < 0) {
            throw new IllegalArgumentException("To calculate this indicator the historicalValues array must have a length greater then the length of the weights array ");
        }
        for (int i = 0; i < closes.length; i++) {
            if (closes[i] < 0.0D) {
                throw new IllegalArgumentException("The Close array contains an element which is not positive.");
            }
        }

        double[] ad2 = new double[(closes.length - weights.length) + 1];
        double d = 0.0D;
        for (int j = 0; j < weights.length; j++) {
            d += weights[j];
        }

        for (int k = ad2.length - 1; k >= 0; k--) {
            ad2[k] = 0.0D;
            for (int l = 0; l < weights.length; l++) {
                ad2[k] += closes[k + l] * weights[l];
            }

            ad2[k] /= d;
        }

        return ad2;
    }

    private static void validateStandardParameters(double[] highs, double[] lows, double[] closes, double[] opens) {
        if (highs.length != lows.length || lows.length != opens.length || opens.length != closes.length) {
            throw new IllegalArgumentException("The three arrays high, low, close, open do not have the same length.");
        }
        for (int i = 0; i < highs.length; i++) {
            if (highs[i] < 0.0D) {
                throw new IllegalArgumentException("The High array contains an element which is not positive.");
            }
        }

        for (int j = 0; j < lows.length; j++) {
            if (lows[j] < 0.0D) {
                throw new IllegalArgumentException("The Low array contains an element which is not positive.");
            }
        }

        for (int k = 0; k < opens.length; k++) {
            if (opens[k] < 0.0D) {
                throw new IllegalArgumentException("The Close array contains an element which is not positive.");
            }
        }

        for (int l = 0; l < closes.length; l++) {
            if (closes[l] < 0.0D) {
                throw new IllegalArgumentException("The Open  array contains an element which is not positive.");
            }
        }

        if (highs == null) {
            throw new IllegalArgumentException("The high array is null.");
        }
        if (highs.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        }
        if (lows == null) {
            throw new IllegalArgumentException("The low array is null.");
        }
        if (lows.length == 0) {
            throw new IllegalArgumentException("The low array cannot be empty.");
        }
        if (opens == null) {
            throw new IllegalArgumentException("The close array is null.");
        }
        if (opens.length == 0) {
            throw new IllegalArgumentException("The close array cannot be empty.");
        }
        if (closes == null) {
            throw new IllegalArgumentException("The open array is null.");
        }
        if (closes.length == 0) {
            throw new IllegalArgumentException("The open array cannot be empty.");
        } else {
            return;
        }
    }

}
