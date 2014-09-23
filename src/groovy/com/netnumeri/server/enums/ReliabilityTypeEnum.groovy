package com.netnumeri.server.enums

public enum ReliabilityTypeEnum {

    Low("Low"), High("High"), Medium("medium")

    final String value

    ReliabilityTypeEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }
}