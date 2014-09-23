package com.netnumeri.server.finance.indicator.candles.singles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Single Candle Black - Continuation
 */
public class BearishCandleBlack {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;
        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraSup2 = candle.getCandleSize(0, 2, 2);

        boolean CorpoCandleMedio = false;

        for (int x = 0; x < 3; x++) {
            CorpoCandleMedio = (candle.getCandleSize(x, 1, 0) > candle.getCandleSize(x + 1, 1, 0)) &&
                    (candle.getCandleSize(x, 1, 0) < candle.getCandleSize(x + 1, 1, 0) * 2);
        }

        boolean MedioCandleBlack = (CorpoCandleMedio) && (candle.Close(0) < candle.Open(0)) &&
                (CorpoCandle1 > SombraSup1) && (CorpoCandle1 > SombraSup2);

        if (MedioCandleBlack)
            result = 0.1;

        return result;
    }
}
