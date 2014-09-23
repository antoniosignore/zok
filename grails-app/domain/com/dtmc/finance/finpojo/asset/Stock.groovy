package com.dtmc.finance.finpojo.asset

class Stock extends Asset implements Serializable {

//    static transients = [
//            "chain",
//            "snapshot"
//    ]

    String description

//    OptionsChain chain;
//    YahooInstantSnapshot snapshot;

    public Stock(String name, String description) {
        this.name = name;
        this.description = description
    }

    public Double modelPrice(Integer model) {
        return 0;
    }

}
