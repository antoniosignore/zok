package com.dtmc.finance.finpojo

import com.dtmc.finance.finpojo.asset.Asset
import com.dtmc.finance.finpojo.derivative.Derivative

public class Entry implements Serializable {

    static constraints = {
    }

    static belongsTo = [portfolio: Portfolio, instrument: Instrument]

    Integer amount = 0;

    public Entry() {
    }

    private void init() {
        this.amount = 0;
    }

    public Entry(Instrument instrument, Portfolio portfolio) {
        this.instrument = instrument;
        this.portfolio = portfolio;
    }

    // amount < 0 means taking short position in instrument
    public Entry(Instrument instrument, Integer amount, Portfolio portfolio) {
        this.instrument = instrument;
        this.amount = amount;
        this.portfolio = portfolio;
    }

    public Integer position() {
        if (amount >= 0)
            return +1;
        else
            return -1;
    }

    public Double price() {
        if (instrument instanceof Asset) {
            return instrument.getLast();
        } else if (instrument instanceof Derivative) {
            return instrument.premium();
        }
        return 0;
    }

    public Double value() {
        return price() * amount;
    }

}
