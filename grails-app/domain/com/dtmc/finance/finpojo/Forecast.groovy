package com.dtmc.finance.finpojo

import com.dtmc.club.Member
import com.dtmc.finance.finpojo.asset.Stock

class Forecast extends Persistable implements Serializable {

    static belongsTo = [user: Member]

    Stock ticker
    Double percent
    Date when

    Bet bet
}
