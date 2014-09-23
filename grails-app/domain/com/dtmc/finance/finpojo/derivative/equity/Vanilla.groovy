package com.dtmc.finance.finpojo.derivative.equity;

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.derivative.Derivative
import com.netnumeri.server.enums.OptionType

class Vanilla extends Derivative implements Serializable {

    static belongsTo = [underlying: Instrument]

    Double interestRate;
    Date expiration;
    Double strike;
    Double premium;
    Double change;
    Double dividend;
    OptionType type;
    Integer contractSize;
    Integer openInterest;


    public Vanilla() {
    }

}