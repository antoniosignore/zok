package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BullishDojiStar {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        // Doji estrela de alta (Bullish Doji Star)

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double SombraSup2 = candle.getCandleSize(1, 2, 1);
        double SombraInf2 = candle.getCandleSize(1, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(1, 3);

        boolean SegundoCandleBaixa = (candle.Open(1) > candle.Close(1)
                && CorpoCandle2 > CorpoCandle1
                && CorpoCandle2 > SombraSup2 + SombraInf2); // AND (Close >=
        // Open[2])

        boolean CandleDoji = candle.Doji(0);

        boolean PrimeiroCandleGAP = (CandleDoji
                && candle.Open(0) < candle.Open(1)
                && candle.Open(0) < candle.Close(1)
                && candle.High(0) < candle.Low(1));

        boolean DojiEstrelaAlta = (SegundoCandleBaixa && PrimeiroCandleGAP
                && TendenciaUltimosCandles == 0);

        if (DojiEstrelaAlta) {
            result = 7;
        }

        return result;
    }
}
