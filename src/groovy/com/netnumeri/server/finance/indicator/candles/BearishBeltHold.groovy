package com.netnumeri.server.finance.indicator.candles

import com.netnumeri.server.finance.beans.TimeSeries;

import com.netnumeri.server.finance.indicator.utils.CandlestickUtils;

/**
 * Bearish Belt Hold
 *
 * PS: Fiz uma varia�ao desse tipo de candle como documentado no site
 * http://thepatternsite.com/BeltHoldBear.html A diferen�a � que esse candle
 * abre em GAP e fecha proximo ao fechamento(-)/abertura(+) do candle
 * precedente, ele pode assemelhar com um piercing em alguns casos. Seria bom
 * durante os testes de adaptamento dos candles e indicadores, tentar
 * modifica-lo para parecer com aquele documentado no site e ver se o resultado
 * ser� melhor que esse.
 */

public class BearishBeltHold {


    public static double calculate(TimeSeries qh, Date date, int periodLength) {

        CandlestickUtils candle = new CandlestickUtils(qh);

        double result = 0;

        double CorpoCandle1 = candle.getCandleSize(0, 1, 0);
        double SombraSup1 = candle.getCandleSize(0, 2, 1);
        double SombraInf1 = candle.getCandleSize(0, 2, 2);

        double CorpoCandle2 = candle.getCandleSize(1, 1, 0);
        double CorpoCandle3 = candle.getCandleSize(2, 1, 0);
        double CorpoCandle4 = candle.getCandleSize(3, 1, 0);

        double TendenciaCandles = candle.getTendencia(1, 3);
        double range20percCandle2 = Math.abs(candle.Open(1) - candle.Close(1)) / 100 * 20;
        double range10percCandle2 = Math.abs(candle.Open(1) - candle.Close(1)) / 100 * 10;

        boolean LarguraCandle = (CorpoCandle1 > CorpoCandle2 * 1.05
                && CorpoCandle1 > CorpoCandle3 * 1.05
                && CorpoCandle1 > CorpoCandle4 * 1.05
                && SombraInf1 > SombraSup1); // && (CorpoCandle1> CorpoCandle5)

        boolean PrimeiroCandleBaixa = (candle.Open(0) > candle.High(1)
                && CorpoCandle1 > SombraSup1 + SombraInf1
                && candle.Close(0) < candle.Low(0) + range20percCandle2
                && (candle.Close(0) > candle.Open(1)
                && candle.Close(0) > candle.Open(1) + range10percCandle2
                && candle.Close(1) > candle.Open(1) || candle.Close(0) > candle.Close(1)
                && candle.Close(0) > candle.Close(1) + range10percCandle2
                && candle.Close(1) < candle.Open(1))
                && candle.Close(0) < candle.High(1));

        boolean BearishBeltHold = (PrimeiroCandleBaixa && LarguraCandle
                && TendenciaCandles == 1);

        if (BearishBeltHold) {
            result = -16;
        }

        return result;
    }
}
