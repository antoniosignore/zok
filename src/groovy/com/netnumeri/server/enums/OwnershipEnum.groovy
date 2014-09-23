package com.netnumeri.server.enums

public enum OwnershipEnum {

    EqualShare("Equal Share Ownership"),
    UVS("Unit valuation system")

    /*
    http://en.wikipedia.org/wiki/Unit_Valuation_System
     */

    final String value

    OwnershipEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }
}