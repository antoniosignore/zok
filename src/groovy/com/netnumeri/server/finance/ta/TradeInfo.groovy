package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.FinConstants
import com.dtmc.finance.finpojo.Instrument

public class TradeInfo {
    double tradeOpen;
    double tradeHigh;
    double tradeLow;
    double tradeClose;
    double tradeResult;
    int tradeDuration;
    FinConstants tradeType;
    Date date;
    Date closeDate;
    Instrument instrument;

    public TradeInfo(double tradeOpen,
                     double tradeHigh,
                     double tradeLow,
                     double tradeClose,
                     double tradeResult,
                     FinConstants tradeType,
                     int tradeDuration,
                     Date tradeDate,
                     Date closeDate,
                     Instrument instrument) {
        this.tradeOpen = tradeOpen;
        this.tradeHigh = tradeHigh;
        this.tradeLow = tradeLow;
        this.tradeClose = tradeClose;
        this.tradeResult = tradeResult;
        this.tradeDuration = tradeDuration;
        this.tradeType = tradeType;
        this.date = tradeDate;
        this.closeDate = closeDate;
        this.instrument = instrument;
    }

    public int compareTo(java.lang.Object obj) {
        String strDate = (String) getKey();
        return strDate.compareTo((String) obj);
    }


}

