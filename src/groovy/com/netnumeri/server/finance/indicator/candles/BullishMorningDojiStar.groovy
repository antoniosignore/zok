package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bullish Morning Star
 */
public class BullishMorningDojiStar {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        // Doji Estrela da manh� (Bullish Morning Doji Star)
        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);

        double CorpoCandle3 = candle.getCandleSize(2, 1, 0);
        double SombraSup3 = candle.getCandleSize(2, 2, 1);
        double SombraInf3 = candle.getCandleSize(2, 2, 2);

        double rangePercCandle3 = Math.abs(candle.Open(2) - candle.Close(2)) / 100 * 40; // Retorna
        // o
        // valor
        // do
        // pre�o
        // que

        double TendenciaUltimosCandles = candle.getTendencia(2, 4);

        boolean TerceiroCandleBaixa = (candle.Close(2) < candle.Open(2)
                && CorpoCandle3 > SombraSup3 + SombraInf3);

        boolean CandleDoji = candle.Doji(1);

        boolean SegundoCandleGAP = (CandleDoji
                && candle.Open(1) < candle.Close(2)
                && candle.Close(1) < candle.Close(2)
                && candle.Open(1) < candle.Open(0)
                && candle.Close(1) < candle.Open(0));

        boolean PrimeiroCandleAlta = (candle.Close(0) >= candle.Close(2)
                && candle.Open(0) < candle.Close(2)
                && CorpoCandle1 > CorpoCandle2
                && candle.Close(0) > rangePercCandle3 + candle.Close(2));

        boolean DojiEstrelaDaManha = (TerceiroCandleBaixa && SegundoCandleGAP
                && PrimeiroCandleAlta && TendenciaUltimosCandles == 0);

        if (DojiEstrelaDaManha) {
            result = 8;
        }

        return result;
    }
}
