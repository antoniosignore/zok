package com.netnumeri.server.finance.indicator;


import com.netnumeri.server.finance.beans.TimeSeries

/**
 * Exponential Moving Average.
 */

public class WilliamPercentR {

    public static double calculate(TimeSeries opens,
                                   TimeSeries closes,
                                   TimeSeries highs,
                                   TimeSeries lows,
                                   TimeSeries volumes, Date date, int periodLength) {

        int lastBar = opens.matrix.index(date);

        double lastMax = highs.getValue(lastBar);
        double lastMin = lows.getValue(lastBar);

        double priceMaxTmp = 0;
        double priceMaxLast = 0;
        double priceMax = 0;

        double priceMinTmp = 0;
        double priceMinLast = 0;
        double priceMin = 1000;

        double priceClose = 0;

        long lastDate = 0L;

        for (int i = lastBar - periodLength + 1; i <= lastBar; i++) {
            priceClose = closes.getValue(i)

            priceMaxLast = priceMax;
            priceMinLast = priceMin;

            priceMaxTmp = highs.getValue(i)
            priceMinTmp = lows.getValue(i)

            if (priceMaxTmp > priceMaxLast) {
                priceMax = priceMaxTmp;
            }

            if (priceMinTmp < priceMinLast) {
                priceMin = priceMinTmp;
            }
        }

        double percR = (priceClose - priceMax) / (priceMax - priceMin) * -100;

        // System.out.println("@percR : " + percR);

        /*
         * %R = (Fech�ltimo - Hn) � (Hn - Ln) x 100
         *
         * Onde:
         *
         * Fech�ltimo = �ltimo pre�o de fechamento; n = N�mero de per�odos; Ln =
         * pre�o de venda mais baixo dos �ltimos n per�odos; Hn = pre�o de
         * compra mais alto dos �ltimos n per�odos.
         */

        /*
         * System.out.println("@Date: " + lastDate);
         *
         *
         * System.out.println("\nlastMax: " + lastMax);
         * System.out.println("lastMin: " + lastMin + "\n");
         */
        return percR;
    }

}
