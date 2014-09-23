package com.netnumeri.server.enums

public enum ConfirmationTypeEnum {

    Needed ("Needed"),
    Suggested ("Suggested"),
    Required ("Required"),
    DefinitelyRequired("Definitely Required"),
    StronglySuggested("Strongly Suggested"),
    Recommended("Recommended")

    final String value

    ConfirmationTypeEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }


}