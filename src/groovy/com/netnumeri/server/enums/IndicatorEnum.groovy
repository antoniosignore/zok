package com.netnumeri.server.enums

enum IndicatorEnum {

    SimpleMovingAverage("Simple Moving Average"),
    WeightedMovingAverage("Weighted Moving Average"),
    SingularSpectrumFirstComponent("Singular Spectrum First Component"),
    Normalized("Normalized"),
    SingularSpectrumThirdComponent("Singular Spectrum Third Component"),
    SingularSpectrumSecondComponent("Singular Spectrum Second Component"),
    SingularSpectrumFirstSecondComponent("Singular Spectrum First Second Component"),
    SingularSpectrumSecondThirdComponent("Singular Spectrum Second third Component"),
    SingularSpectrumPrediction("Singular Spectrum Prediction"),
    PriceChannelUpper("Price Channel Upper"),
    PriceChannelLower("Price Channel Lower"),
    UpperBollingerBand("Upper Bollinger Band"),
    LowerBollingerBand("Lower Bollinger Band"),
    SimpleMovingVariance("Simple Moving Variance"),
    SimpleMovingDivergence("Simple Moving Divergence"),
    SimpleMovingCovariance("Simple Moving Covariance"),
    Momentum("Momentum"),
    MACD("MACD"),
    RateOfChange("Rate of Change"),
    RelativeStrengthIndex("Relative Strength Index"),
    RelativeStrengthIndex2("Relative Strength Index 2"),
    CommodityChannelIndicator("CCI"),
    Oscillator("Oscillator"),
    TrueChange("True Change"),
    AverageTrueChange("Average True Change"),
    MoneyFlowOverPeriod("Money Flow Over Period"),
    AccumulateDistributionOverPeriod("Accumulated Distribution Over Period"),
    ChaikinOscillatorOverPeriod("Chaikin Oscillator Over Period"),
    ChaikinMoneyFlowOverPeriod("Chaikin Money Flow Over Period"),
    AroonOscillatorOverPeriod("Aroon Oscillator Over Period"),
    AroonDownOverPeriod("Aroon Down Over Period"),
    AroonUpOverPeriod("Simple Moving Average"),
    TrueRangePeriod("Aroon Up Over Period"),
    kFastStochasticPeriod("k Fast Stochastic Period"),
    dStochastic("d Stochastic"),
    DStochasticSmoothed("D Stochastic Smoothed"),
    ChaikinVolatility("Chaikin Volatility"),
    MACDSignal("MACD Signal"),
    PlusDirectionalMovementPeriod("Plus Directional Movement Period"),
    MinusDirectionalMovementPeriod("Minus Directional Movement Period"),
    PriceActionOverPeriod("Price Action Over Period"),
    BalanceOfPowerOverPeriod("Balance Of Power Over Period"),
    MarketFacilitationIndexOverPeriod("Market Facilitation Index Over Period"),
    CommodityChannelIndexOverPeriod("Commodity Channel Index Over Period"),
    MomentumPctPeriod("Momentum Pct Period"),
    GeometricMovingAverage("Geometric Moving Average"),
    LinearlyWeightedMovingAverage("Linearly Weighted Moving Average"),
    TriangularMovingAverage("Triangular Moving Average"),
    ExponentiallyWeightedMovingAverage("Exponentially Weighted Moving Average"),
    Kairi("Kairi"),
    RateOfChangePeriod("Rate Of Change Period");

    final String value

    IndicatorEnum(String value) { this.value = value }

    String toString() { value }

    String getKey() { name() }

}
