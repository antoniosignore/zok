package com.dtmc.finance.finpojo

class Persistable implements Serializable {

    static mapping = { tablePerHierarchy false }

    String name

    Date dateCreated
    Date lastUpdated

}
