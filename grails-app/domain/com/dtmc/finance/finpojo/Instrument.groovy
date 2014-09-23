package com.dtmc.finance.finpojo

class Instrument extends Persistable implements Serializable {

    static hasMany = [dailyArray: Daily]

    static mapping = {
        tablePerHierarchy false
    }

}
