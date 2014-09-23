package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;



public class BearishDarkCloudCover {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double SombraSup2 = candle.getCandleSize(1, 2, 1);
        double SombraInf2 = candle.getCandleSize(1, 2, 2);

        double CorpoCandle3 = candle.getCandleSize(2, 1, 0);
        double SombraSup3 = candle.getCandleSize(2, 2, 1);
        double SombraInf3 = candle.getCandleSize(2, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(1, 4);

        double range50percCandle1 = Math.abs(candle.Close(1) - candle.Open(1)) / 100 * 50;

        boolean SegundoCandleAlta = (candle.Open(1) < candle.Close(1)
                && CorpoCandle2 > SombraSup2 + SombraInf2);

        boolean PrimeiroCandleBaixa = (CorpoCandle1 > SombraSup1 + SombraInf1
                && candle.Open(0) > candle.Close(1)
                && candle.Close(0) > candle.Open(1)
                && candle.Open(0) > candle.Close(0)
                && candle.Close(0) < candle.Close(1) - range50percCandle1);

        boolean NuvensNegras = (TendenciaUltimosCandles == 1
                && SegundoCandleAlta && PrimeiroCandleBaixa);

        if (NuvensNegras) {
            result = -11;
        }

        return result;
    }
}
