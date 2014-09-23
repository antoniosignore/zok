package com.dtmc.finance.finpojo

import com.netnumeri.server.finance.beans.FinConstants

class Daily implements Serializable, Comparable {

    static belongsTo = [instrument: Instrument]

    Date dailydate;
    Integer volume = 0;
    Double high = 0;
    Double low = 0;
    Double openprice = 0;
    Double closeprice = 0;

    Integer openInterest;

    static constraints = {
        dailydate(unique: ['instrument'])
    }

    public Daily() {
    }

    public Daily(Daily daily) {
        this.dailydate = daily.dailydate;
        this.high = daily.high;
        this.low = daily.low;
        this.openprice = daily.openprice;
        this.closeprice = daily.closeprice;
        this.volume = daily.volume;
        this.instrument = daily.instrument;
    }

    public Daily(Instrument instrument,
                 Date d,
                 Double high,
                 Double low,
                 Double open,
                 Double close,
                 Integer volume,
                 Integer openInterest) {
        this.instrument = instrument;
        this.dailydate = d;
        this.high = high;
        this.low = low;
        this.openprice = open;
        this.closeprice = close;
        this.volume = volume;
        openInterest = openInterest;
    }

    public Double price() {
        return price(FinConstants.TYPICALPRICE);
    }

    public Double price(FinConstants Option) {
        switch (Option) {
            case FinConstants.HIGH:
                return high;
            case FinConstants.LOW:
                return low;
            case FinConstants.OPEN:
                return openprice;
            case FinConstants.CLOSE:
                return closeprice;
            case FinConstants.VOLUME:
                return volume;
            case FinConstants.MEDIANPRICE:
                return (high + low) / 2.0;
            case FinConstants.TYPICALPRICE:
                return (high + low + closeprice) / 3.0;
            case FinConstants.WEIGHTEDPRICE:
                return (high + low + 2.0 * closeprice) / 4.0;
            case FinConstants.AVERAGEPRICE:
                return (high + low + closeprice + openprice) / 4.0;
            case FinConstants.LOGAVERAGEPRICE:
                return Math.log((high + low + closeprice + openprice) / 4.0);
        }
        return 0;
    }

    @Override
    int compareTo(obj) {
        dailydate.compareTo(obj.dailydate)
    }

    @Override
    public String toString() {
        return "Daily{" +
                "id=" + id +
                '}';
    }
}
