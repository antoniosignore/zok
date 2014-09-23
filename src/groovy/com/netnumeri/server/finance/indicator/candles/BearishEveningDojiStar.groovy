package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BearishEveningDojiStar {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        // Doji Estrela da noite (Bearish Evening Doji Star)
        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);

        double CorpoCandle3 = candle.getCandleSize(2, 1, 0);
        double SombraSup3 = candle.getCandleSize(2, 2, 1);
        double SombraInf3 = candle.getCandleSize(2, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(2, 3);

        boolean TerceiroCandleAlta = (candle.Close(2) > candle.Open(2)
                && CorpoCandle3 > SombraSup3 + SombraInf3);

        boolean CandleDoji = candle.Doji(1);

        boolean SegundoCandleGAP = (CandleDoji
                && candle.Open(1) > candle.Close(2)
                && candle.Close(1) > candle.Close(2)
                && candle.Open(1) > candle.Open(0)
                && candle.Close(1) > candle.Open(0)
                && candle.Low(1) > candle.High(2));

        boolean PrimeiroCandleBaixa = (candle.Open(0) > candle.Close(0)
                && candle.Open(0) >= candle.Open(2)
                && candle.Open(0) > candle.Close(2)
                && CorpoCandle1 > CorpoCandle2);

        boolean DojiEstrelaDaNoite = (TerceiroCandleAlta && SegundoCandleGAP
                && PrimeiroCandleBaixa && TendenciaUltimosCandles == 1);

        if (DojiEstrelaDaNoite) {
            result = -8;

            if (DojiEstrelaDaNoite && candle.Close(2) < candle.Open(0)
                    && candle.Close(2) > candle.Close(0)) {
                result = -8.5;
            }
        }
        return result;
    }
}
