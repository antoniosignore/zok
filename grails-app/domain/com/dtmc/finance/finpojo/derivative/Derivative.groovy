package com.dtmc.finance.finpojo.derivative;

import com.dtmc.finance.finpojo.Instrument

public abstract class Derivative extends Instrument implements Serializable {

    static mapping = {
        tablePerHierarchy false
    }

    protected Double alpha = 0;
    protected Double beta = 0;
    protected Double theta = 0;
    protected Double rho = 0;
    protected Double vega = 0;


    public Derivative() {
    }


}