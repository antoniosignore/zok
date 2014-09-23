package com.dtmc.finance.finpojo

class Bet extends Persistable implements Serializable {

    Double score;

    static hasMany = [trades: Trade]

}
