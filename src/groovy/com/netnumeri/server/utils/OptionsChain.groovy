package com.netnumeri.server.utils

import com.dtmc.finance.finpojo.derivative.equity.Vanilla


public class OptionsChain implements Serializable {

    String ticker;

    public Map<Date, List<Vanilla>> calls = new HashMap<Date, List<Vanilla>>();
    public Map<Date, List<Vanilla>> puts = new HashMap<Date, List<Vanilla>>();


}
