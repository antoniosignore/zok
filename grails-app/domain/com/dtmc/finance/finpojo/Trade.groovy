package com.dtmc.finance.finpojo

import com.netnumeri.server.finance.beans.TradeEnum

public class Trade implements Serializable {

    TradeEnum tradeAction;

    static belongsTo = [portfolio: Portfolio, instrument: Instrument]

    Integer amount = 0;
    Double price = 0.0;
    Double cost = 0;
    Date tradeDate;
    String blog

    public Trade() {
    }

}
