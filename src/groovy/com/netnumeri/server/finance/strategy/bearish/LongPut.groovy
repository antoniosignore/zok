package com.netnumeri.server.finance.strategy.bearish

import com.dtmc.finance.finpojo.Trade
import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.finance.finpojo.derivative.equity.Vanilla
import com.netnumeri.server.enums.OptionType
import com.dtmc.finance.finpojo.Bet
import com.dtmc.finance.finpojo.Forecast
import com.netnumeri.server.finance.strategy.ForecastType
import com.netnumeri.server.finance.strategy.OptionStrategy
import com.netnumeri.server.finance.strategy.StrategyHelper

class LongPut implements OptionStrategy {

    @Override
    List<Bet> analyze(Forecast forecast, Stock instrument) {
        List<Bet> bets = new ArrayList<Bet>()

        List<Vanilla> options = StrategyHelper.getAtTheMoneyList(instrument, OptionType.PUT);

        for (int i = 0; i < options.size(); i++) {
            Vanilla option = options.get(i);
            Trade transaction = convertOptionToTransaction(option);
            Bet bet = new Bet();
            bet.name = "Long Put"
            bet.addToTrades(transaction)
            bets.add(bet)
        }
        return bets
    }

    Trade convertOptionToTransaction(Vanilla option) {

    }


    @Override
    String getImage() {
        return "images/long-call.gif"
    }

    @Override
    boolean isUnlimited() {
        return true
    }

    @Override
    Double maximumLoss() {
        return null
    }

    @Override
    boolean isForRetail() {
        return true
    }

    @Override
    Double breakevenPoint() {
        return null
    }

    @Override
    Double profit() {
        return null
    }

    @Override
    ForecastType getType() {
        return ForecastType.bearish
    }
}
