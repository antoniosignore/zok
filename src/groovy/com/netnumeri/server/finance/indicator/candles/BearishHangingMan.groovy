package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Shooting Star
 */
public class BearishHangingMan {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double TendenciaUltimosCandles = candle.getTendencia(0, 1);

        boolean HangingMan = candle.isHangingManORHammer(qh);
        if (HangingMan && TendenciaUltimosCandles > 0) {
            result = 3;
        }

        return result;
    }
}
