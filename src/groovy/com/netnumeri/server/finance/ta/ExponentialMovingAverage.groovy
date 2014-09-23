package com.netnumeri.server.finance.ta;

public class ExponentialMovingAverage {

    private final float weight;
    private double prevEMA;

    /**
     * Create a new exponential moving average which smooths over the
     * given number of periods.
     */
    public ExponentialMovingAverage(final int periods) {
        if (periods < 1) {
            System.out.println("ExponentialMovingAverage:: ERROR: bad periods: " + periods);
        }
        this.weight = 2 / (float) (1 + periods);
        this.prevEMA = 0;
    }

    /**
     * Create a new exponential moving average, using the given
     * period rate weight.
     */
    public ExponentialMovingAverage(final float weight) {
        if ((weight < 0.0) || (weight > 1.0)) {
            System.out.println("ExponentialMovingAverage:: ERROR: bad weight: " + weight);
        }
        this.weight = weight;
        this.prevEMA = 0;
    }

    public void reset() {

        this.prevEMA = 0;
    }

    /**
     * Update average and return average-so-far.
     */
    public double update(final double newValue) {
        prevEMA = (weight * (newValue - prevEMA)) + prevEMA;
        return prevEMA;
    }

    /**
     * Return average-so-far.
     */
    public double getAverage() { return prevEMA; }

}