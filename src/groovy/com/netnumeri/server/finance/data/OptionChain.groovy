package com.netnumeri.server.finance.data

import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.finance.finpojo.derivative.equity.Vanilla;


public class OptionChain {
    private Vector options;
    private Stock stock;

    public OptionChain(Stock stock) {
        this.stock = stock;
    }

    public Vector getOptions() {
        return options;
    }

    public void setOptions(Vector options) {
        this.options = options;
    }

    public void addOption(Vanilla option) {
        options.add(option);
    }

}
