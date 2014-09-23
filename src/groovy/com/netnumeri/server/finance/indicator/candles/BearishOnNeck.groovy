package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BearishOnNeck {
    // Padrao Piercing perto do pesco�o


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double SombraSup2 = candle.getCandleSize(1, 2, 1);
        double SombraInf2 = candle.getCandleSize(1, 2, 2);

        /*
         * double CorpoCandle3 = candle.getCandleSize(2, 1, 0); double
         * SombraSup3 = candle.getCandleSize(2 ,2 ,1); double SombraInf3 =
         * candle.getCandleSize(2 ,2 ,2);
         */
        double TendenciaUltimosCandles = candle.getTendencia(1, 3);

        boolean SegundoCandleBaixa = (candle.Open(1) > candle.Close(1)
                && CorpoCandle2 > SombraSup2 + SombraInf2);

        double rangeMaximo = (candle.High(1) - candle.Low(1)) / 100 * 18; // Retorna
        // o
        // valor
        // do
        // pre�o
        // que
        // representa
        // 10%

        boolean PrimeiroCandleAlta = (candle.Close(0) > candle.Open(0)
                && candle.Close(0) < candle.Low(1) + rangeMaximo
                && candle.Close(0) > candle.Low(1) - rangeMaximo
                && CorpoCandle1 > SombraSup1 + SombraInf1);

        boolean BearishOnNeck = (TendenciaUltimosCandles == 0
                && SegundoCandleBaixa && PrimeiroCandleAlta);

        if (BearishOnNeck) {
            result = -15;
        }

        return result;
    }
}
