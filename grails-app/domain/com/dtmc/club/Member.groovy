package com.dtmc.club

import arrested.ArrestedUser
import com.dtmc.indicator.UserIndicators
import com.dtmc.trading.StrategyCatalog

class Member extends ArrestedUser implements Serializable {

    static belongsTo = [club: Club]

    String firstname
    String lastname
    String address1
    String address2
    String city
    String state
    String country
    String company
    String email
    String phone
    String mobile
    String twitter
    String facebook
    String linkedin
    String timezone

    static hasMany = [followers: Member, indicators: UserIndicators, strategies: StrategyCatalog]

    static constraints = {
        firstname (size: 1..30, blank: true, nullable: true)
        lastname (size: 1..30, blank: true, nullable: true)
        email (email: true, nullable: true)
        address1 (email: true, blank: true, nullable: true)
        address2 (email: true, blank: true, nullable: true)
        phone (size: 6..15, blank: true, nullable: true)
        mobile (size: 6..15, blank: true, nullable: true)
        city (size: 1..20, blank: true, nullable: true)
        state (size: 1..20, blank: true, nullable: true)
        country (size: 1..20, blank: true, nullable: true)
        twitter (size: 5..200, blank: true, nullable: true)
        facebook (size: 5..200, blank: true, nullable: true)
        linkedin (size: 5..200, blank: true, nullable: true)
        company (size: 5..200, blank: true, nullable: true)
        timezone (nullable: true)
    }

    static mapping = {
    }

    @Override
    public String toString() {
        return username
    }

}
