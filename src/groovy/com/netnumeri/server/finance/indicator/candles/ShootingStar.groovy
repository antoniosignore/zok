package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Shooting Star
 */
public class ShootingStar {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;
        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double TendenciaUltimosCandles = candle.getTendencia(1, 3);

        boolean SegundoCandle = candle.Open(1) < candle.Close(1); // AND (
        // CorpoCandle2
        // >
        // (SombraSup2+SombraInf2)
        // )

        boolean PrimeiroCandleGAPAlta = (candle.Close(0) > candle.Close(1)
                && candle.Open(0) > candle.Close(1)
                && SombraSup1 > SombraInf1 * 2.5
                && CorpoCandle1 < SombraSup1 + SombraInf1);

        boolean PrimeiroCandleLungaSombra = SombraSup1 > (CorpoCandle1 + SombraInf1) * 4;

        boolean ShootingStar = SegundoCandle && PrimeiroCandleGAPAlta; // (TendenciaUltimosCandles=1)

        if (ShootingStar) {
            result = 1;
            if (PrimeiroCandleLungaSombra) {
                result = 1.5;
            }
            if (TendenciaUltimosCandles != 0) {
                result = -result;
            }
        }

        return result;
    }
}
