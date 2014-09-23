package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BullishInvertedHammer {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double SombraSup2 = candle.getCandleSize(1, 2, 1);
        double SombraInf2 = candle.getCandleSize(1, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(2, 4);

        boolean SegundoCandleBaixa = (candle.Open(1) > candle.Close(1)
                && CorpoCandle2 > CorpoCandle1
                && CorpoCandle2 > SombraSup2 + SombraInf2);

        boolean PrimeiroCandle = (candle.isHangingManORHammer(qh)
                && SombraSup1 > SombraInf1);

        boolean BullishInvertedHammer = (TendenciaUltimosCandles == 0
                && SegundoCandleBaixa && PrimeiroCandle);

        if (BullishInvertedHammer) {
            result = 12;
        }

        return result;
    }
}
