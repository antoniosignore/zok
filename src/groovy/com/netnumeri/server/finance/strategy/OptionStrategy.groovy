package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.finance.finpojo.Forecast
import com.dtmc.finance.finpojo.Bet

public interface OptionStrategy {

    public List<Bet> analyze(Forecast forecast, Stock stock);

    public String getImage();

    public boolean isUnlimited();

    public Double maximumLoss();

    public boolean isForRetail();

    public Double breakevenPoint();

    public Double profit();

    public ForecastType getType()
}
