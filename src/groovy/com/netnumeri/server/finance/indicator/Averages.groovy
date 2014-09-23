package com.netnumeri.server.finance.indicator

class Averages {

    private final double avgUp;
    private final double avgDown;

    public Averages(double up, double down) {
        this.avgDown = down;
        this.avgUp = up;
    }

    public double getAvgDown() {
        return avgDown;
    }

    public double getAvgUp() {
        return avgUp;
    }
}
