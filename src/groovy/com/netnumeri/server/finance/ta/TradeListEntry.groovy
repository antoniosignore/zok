package com.netnumeri.server.finance.ta

import com.dtmc.finance.finpojo.Instrument

public class TradeListEntry {

    Instrument instrument;
    def tradeInfoList;
    double tradeResult;
    int numberOfTrades;

    public TradeListEntry(Instrument instrument) {
        this.instrument = instrument;
        tradeResult = 0;
        numberOfTrades = 0;
        tradeInfoList = new ArrayList<TradeInfo>();
    }

    public int compareTo(Object o) {
        TradeListEntry obj = (TradeListEntry) o;
        if (this == obj) {
            return 0;
        }
        if (tradeResult == obj.getTradeResult()) {
            return 0;
        }
        if (tradeResult < obj.getTradeResult()) {
            return 1;
        }
        if (tradeResult > obj.getTradeResult()) {
            return -1;
        }
        return 0;
    }

}


