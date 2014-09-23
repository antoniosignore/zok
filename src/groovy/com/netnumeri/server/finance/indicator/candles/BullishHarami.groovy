package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BullishHarami {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        // Doji estrela de alta (Bullish Doji Star)

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        /*
         * double CorpoCandle1 = candle.getCandleSize(0, 1, 0); double
         * SombraSup1 = candle.getCandleSize(0 ,2 ,1); double SombraInf1 =
         * candle.getCandleSize(0 ,2 ,2);
         */
        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        // double SombraSup2 = candle.getCandleSize(1 ,2 ,1);
        // double SombraInf2 = candle.getCandleSize(1 ,2 ,2);

        double CorpoCandle3 = candle.getCandleSize(2, 1, 0);
        double SombraSup3 = candle.getCandleSize(2, 2, 1);
        double SombraInf3 = candle.getCandleSize(2, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(2, 4);

        boolean TerceiroCandleBaixa = (candle.Open(2) > candle.Close(2)
                && CorpoCandle3 > CorpoCandle2
                && CorpoCandle3 > SombraSup3 + SombraInf3); // AND (CorpoCandle3
        // >
        // ((SombraSup2+SombraInf2)*2))

        boolean SegundoCandleGAP = (candle.Close(1) < candle.Open(2)
                && candle.Open(1) > candle.Close(2)
                && candle.High(1) < candle.Open(2)
                && candle.Low(1) > candle.Close(2)
                && candle.Open(1) != candle.Close(1)); // AND (SombraSup1 >=
        // SombraInf1)

        boolean PrimeiroCandleAlta = candle.Close(0) > candle.High(2);

        boolean HaramiAlta = (TerceiroCandleBaixa && SegundoCandleGAP
                && PrimeiroCandleAlta && TendenciaUltimosCandles == 0);

        if (HaramiAlta) {
            result = 11;
        }

        return result;
    }
}
