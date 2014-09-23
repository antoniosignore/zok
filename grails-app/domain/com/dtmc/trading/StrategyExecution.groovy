package com.dtmc.trading

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Persistable

class StrategyExecution extends Persistable {

    static hasMany = [signals: Signal]

    static constraints = {
    }

    static mapping = {
    }

}
