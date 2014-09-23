package com.netnumeri.server.finance.strategy.bearish

import com.dtmc.finance.finpojo.Trade
import com.dtmc.finance.finpojo.asset.Stock
import com.netnumeri.server.finance.beans.FinConstants
import com.dtmc.finance.finpojo.Bet
import com.dtmc.finance.finpojo.Forecast
import com.netnumeri.server.finance.strategy.ForecastType
import com.netnumeri.server.finance.strategy.OptionStrategy
import com.netnumeri.server.finance.utils.DateUtils

class ShortShares implements OptionStrategy {

    @Override
    List<Bet> analyze(Forecast forecast, Stock instrument) {
        List<Bet> bets = new ArrayList<Bet>()
        Date date = DateUtils.today();
        Trade transaction = new Trade(instrument, FinConstants.SHORT, 100, instrument.getPrice(instrument.lastDate()), date);
        Bet bet = new Bet();
        bet.transactions.add(transaction)
        bet.name = "Short Shares"
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
        return ForecastType.bearish
    }
}
