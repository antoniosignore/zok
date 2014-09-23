package com.netnumeri.server.enums

public enum PatternTypeEnum {

    Continuation("Continuation"),
    ReversalContinuation("Reversal/Continuation"),
    Reversal("Reversal")

    final String value

    PatternTypeEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }

}