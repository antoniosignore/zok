package com.dtmc.indicator

import com.dtmc.club.Member
import com.netnumeri.server.enums.IndicatorEnum

public class UserIndicators implements Serializable {

    static belongsTo = [user: Member]

    String name
    IndicatorEnum type

//    static transients = ["indicator"]
//    Indicator indicator;

    Integer integer1
    Integer integer2
    Integer integer3

    String str1

    Double double1
    Double double2

    Date dateCreated
    Date lastUpdated


    static constraints = {
        double1 blank: true, nullable: true
        double2 blank: true, nullable: true
        integer1 blank: true, nullable: true
        integer2 blank: true, nullable: true
        integer3 blank: true, nullable: true
        str1 blank: true, nullable: true
    }

}
