package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BullishPiercingLine {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double SombraSup2 = candle.getCandleSize(1, 2, 1);
        double SombraInf2 = candle.getCandleSize(1, 2, 2);

        double range50percCandle2 = Math.abs(candle.Open(1) - candle.Close(1)) / 100 * 50;

        int TendenciaUltimosCandles = candle.getTendencia(1, 4);

        boolean SegundoCandleAlta = (candle.Open(1) > candle.Close(1)
                && CorpoCandle2 > SombraSup2 + SombraInf2);

        boolean PrimeiroCandleBaixa = (CorpoCandle1 > SombraSup1 + SombraInf1
                && candle.Open(0) < candle.Close(1)
                && candle.Close(0) < candle.Open(1)
                && candle.Open(0) < candle.Close(0)
                && candle.Close(0) >= candle.Open(1) - range50percCandle2); // &&
        // (Low
        // >
        // candle.Open(1))

        boolean BullishPiercing = SegundoCandleAlta && PrimeiroCandleBaixa;

        if (BullishPiercing && TendenciaUltimosCandles == 0) {
            result = 13;
        }

        return result;
    }
}
