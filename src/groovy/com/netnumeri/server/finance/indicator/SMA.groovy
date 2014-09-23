package com.netnumeri.server.finance.indicator;

/**
 * Simple Moving Average.
 */
public class SMA {
    private final int length;

    public SMA(double[] qh, int length) {

        this.length = length;
    }


    public static double calculate(double[] qh, int length) {
        int endBar = qh.size() - 1;
        int startBar = endBar - length;
        double sma = 0;

        for (int bar = startBar; bar <= endBar; bar++) {
            sma += qh[bar];
        }

        double value = sma / (endBar - startBar + 1);
        return value;
    }
}
