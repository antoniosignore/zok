package com.dtmc.trading

import com.dtmc.club.Member

class StrategySubscription {

    static belongsTo = [user: Member, strategy: StrategyCatalog]

    Date dateCreated
    Date lastUpdated

}
