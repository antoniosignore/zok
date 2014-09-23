package com.dtmc.trading

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Persistable
import com.netnumeri.server.finance.beans.TradeEnum

class Signal extends Persistable {

    Date day
    TradeEnum direction
    final Instrument instrument
    Double value

    static belongsTo = [instrument: Instrument, strategyExecution: StrategyExecution]

}
