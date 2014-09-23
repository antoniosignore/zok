package com.dtmc.trading

public class StrategyCatalog {

    static hasMany = [subscriptions: StrategySubscription]

    String name
    Date dateCreated
    Date lastUpdated

    public StrategyCatalog(String name) {
        this.name = name
    }

}
