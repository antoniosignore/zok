package com.netnumeri.server.finance.strategy.bullish

import com.dtmc.finance.finpojo.Trade
import com.dtmc.finance.finpojo.asset.Stock
import com.netnumeri.server.finance.beans.TradeEnum
import com.dtmc.finance.finpojo.Bet
import com.dtmc.finance.finpojo.Forecast
import com.netnumeri.server.finance.strategy.ForecastType
import com.netnumeri.server.finance.strategy.OptionStrategy
import com.netnumeri.server.finance.utils.DateUtils

class LongShares implements OptionStrategy {

    @Override
    List<Bet> analyze(Forecast forecast, Stock instrument) {

        List<Bet> bets = new ArrayList<Bet>()

        Date date = DateUtils.today();

        Trade transaction = new Trade(instrument, TradeEnum.BUY, 100, instrument.getPrice(instrument.lastDate()), date);
        Bet bet = new Bet();
        bet.transactions.add(transaction)
        bet.name = "Long Shares"
        bet.description = "Long Shares"
        bets.add(bet)

        return bets

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
        return ForecastType.bullish
    }
}
