package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Lower Bollinger Band
 */
public class BullishThreeStarSouth {
    private int length;


    public static double calculate(TimeSeries qh, Date date, int periodLength) {
        // ThreeStatusSouth - Esse candle precisa de confirma�ao antes de operar
        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;
        double myMediaMinima = candle.getVolumeMedioMinimo(5);

        boolean TerceiroCandleBaixa = candle.Open(2) > candle.Close(2);
        boolean SegundoCandleBaixa = (candle.Open(1) > candle.Close(1)
                && candle.High(1) < candle.High(2)
                && candle.Low(1) > candle.Low(2));
        boolean PrimeiroCandleBaixa = (candle.Open(0) > candle.Close(0)
                && candle.High(0) < candle.High(1)
                && candle.Low(0) > candle.Low(1));
        boolean ThreeStartSouth = (TerceiroCandleBaixa && SegundoCandleBaixa
                && PrimeiroCandleBaixa); // && (TendenciaUltimosCandles=0)
        if (ThreeStartSouth && myMediaMinima <= candle.Volume(0)) {
            result = 4;
        }

        return result;
    }
}
