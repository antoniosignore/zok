package com.netnumeri.server.finance.ta;


public class IndicatorsException extends Exception {

    public IndicatorsException() {
    }

    public IndicatorsException(String s) {
        super(s);
    }

    public IndicatorsException(Exception exception) {
        super(exception.toString());
    }
}
