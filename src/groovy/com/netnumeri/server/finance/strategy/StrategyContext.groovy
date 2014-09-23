package com.netnumeri.server.finance.strategy;

import com.dtmc.finance.finpojo.Bet
import com.dtmc.finance.finpojo.Forecast
import com.netnumeri.server.finance.strategy.bearish.*

import com.netnumeri.server.finance.strategy.bullish.LongCall
import com.netnumeri.server.finance.strategy.bullish.LongShares

public class StrategyContext {

    static List<OptionStrategy> strategy = new ArrayList<OptionStrategy>();

    static {

        strategy.add(new LongShares())
        strategy.add(new LongCall())

        strategy.add(new ShortShares())
        strategy.add(new LongPut())
    }

    public static List<Bet> evaluateStrategy(Forecast forecast) {

        ForecastType type = ForecastType.neutral
        if (forecast.percent > 0.05) type = ForecastType.bullish
        if (forecast.percent < 0.05) type = ForecastType.bearish

        List<Bet> retBets = new ArrayList<Bet>();

        for (Iterator<OptionStrategy> iterator = strategy.iterator(); iterator.hasNext();) {
            OptionStrategy next = iterator.next();

            if (type == next.getType()) {
//                List<Bet> b = next.analyze(forecast, under);
//                retBets.addAll(b);
            }
        }
        return retBets;
    }
}

