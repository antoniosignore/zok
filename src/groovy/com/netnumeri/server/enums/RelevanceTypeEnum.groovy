package com.netnumeri.server.enums

public enum RelevanceTypeEnum {

    Bullish("Bullish"), Bearish("Bearish")

    final String value

    RelevanceTypeEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }

}