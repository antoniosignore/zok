package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries;

/**
 * Rate of Price Change.
 */
public class ROC {

    public static double calculate(TimeSeries qh, Date date, int lookBackLength) {
        int thenBar = qh.size - 1 - lookBackLength;

        double priceNow = qh.getLastData();
        double priceThen = qh.getValue(thenBar)

        double value = priceNow - priceThen;
        return value;
    }
}
