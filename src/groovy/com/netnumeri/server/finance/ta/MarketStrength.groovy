package com.netnumeri.server.finance.ta;

public class MarketStrength {

    public static double balanceOfPower(double d, double d1, double d2, double d3) {
        if (d < 0.0D || d1 < 0.0D || d2 < 0.0D || d3 < 0.0D) {
            throw new IllegalArgumentException("All method's parameteres must be positive.");
        } else {
            return (d1 - d) / (d2 - d3);
        }
    }

    public static double[] balanceOfPowerOverPeriod(double[] highs, double[] lows, double[] closes, double[] opens) {
        if (closes.length != opens.length || opens.length != lows.length || lows.length != highs.length) {
            throw new IllegalArgumentException("The three arrays high, low, close, open do not have the same length.");
        }
        for (int i = 0; i < closes.length; i++) {
            if (closes[i] < 0.0D) {
                throw new IllegalArgumentException("The High array contains an element which is not positive.");
            }
        }

        for (int j = 0; j < opens.length; j++) {
            if (opens[j] < 0.0D) {
                throw new IllegalArgumentException("The Low array contains an element which is not positive.");
            }
        }

        for (int k = 0; k < lows.length; k++) {
            if (lows[k] < 0.0D) {
                throw new IllegalArgumentException("The Close array contains an element which is not positive.");
            }
        }

        for (int l = 0; l < highs.length; l++) {
            if (highs[l] < 0.0D) {
                throw new IllegalArgumentException("The Open  array contains an element which is not positive.");
            }
        }

        if (closes == null) {
            throw new IllegalArgumentException("The high array is null.");
        }
        if (closes.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        }
        if (opens == null) {
            throw new IllegalArgumentException("The low array is null.");
        }
        if (opens.length == 0) {
            throw new IllegalArgumentException("The low array cannot be empty.");
        }
        if (lows == null) {
            throw new IllegalArgumentException("The close array is null.");
        }
        if (lows.length == 0) {
            throw new IllegalArgumentException("The close array cannot be empty.");
        }
        if (highs == null) {
            throw new IllegalArgumentException("The open array is null.");
        }
        if (highs.length == 0) {
            throw new IllegalArgumentException("The open array cannot be empty.");
        }
        double[] ad4 = new double[closes.length];
        for (int i1 = 0; i1 < closes.length; i1++) {
            ad4[i1] = balanceOfPower(highs[i1], lows[i1], closes[i1], opens[i1]);
        }

        return ad4;
    }

    public static double marketFacilitationIndex(double d, double d1, double d2) {
        if (d < 0.0D || d1 < 0.0D || d2 < 0.0D) {
            throw new IllegalArgumentException("All method's parameters must be positive.");
        } else {
            return ((d - d1) / d2) * 1000000D;
        }
    }

    public static double[] marketFacilitationIndexOverPeriod(double[] highs, double[] lows, double[] volumes) {
        if (highs.length != lows.length || lows.length != volumes.length) {
            throw new IllegalArgumentException("The three arrays high, low and volume do not have the same length.");
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

        for (int k = 0; k < volumes.length; k++) {
            if (volumes[k] < 0.0D) {
                throw new IllegalArgumentException("The Volume array contains an element which is not positive.");
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
        if (volumes == null) {
            throw new IllegalArgumentException("The volume array is null.");
        }
        if (volumes.length == 0) {
            throw new IllegalArgumentException("The volume array cannot be empty.");
        }
        double[] ad3 = new double[highs.length];
        for (int l = 0; l < highs.length; l++) {
            ad3[l] = marketFacilitationIndex(highs[l], lows[l], volumes[l]);
        }

        return ad3;
    }

}

