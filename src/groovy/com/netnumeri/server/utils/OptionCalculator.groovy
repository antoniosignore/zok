package com.netnumeri.server.utils

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.derivative.equity.Vanilla;
import com.netnumeri.server.enums.OptionType
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.math.FinMath
import com.netnumeri.server.finance.math.FinRecipes

public class OptionCalculator {

    Vanilla option;
    public boolean debug = true;

    public OptionCalculator(Vanilla option) {
        this.option = option;
    }

    public Instrument underlying() {
        return option.underlying();
    }

    public double interestRate() {
        return option.interestRate;
    }

    public Date expiration() {
        return option.expiration;
    }

    public double strike() {
        return option.strike;
    }

    public double premium() {
        return option.premium;
    }

    public int contractSize() {
        return option.contractSize;
    }

    public int openInterest() {
        return option.openInterest;
    }

    public double alpha(int model) {
        return option.alpha(model);
    }

    public double beta(int model) {
        return option.beta(model);
    }

    public double theta(int model) {
        return option.theta(model);
    }

    public double rho(int model) {
        return option.rho(model);
    }

    public double vega(int model) {
        return option.vega(model);
    }

    public int pricingModel;

    public double d() {
        return option.dividend / 100D;
    }

    public double getPrice(String optionType,
                           double spot,
                           double strike,
                           double interestRate,
                           double dividend,
                           double volatility,
                           double expiration) {
        double OptionPrice = 0;
        if (optionType.equalsIgnoreCase("European Call") || optionType.equalsIgnoreCase("European Put")) {
            OptionPrice = FinMath.BlackPrice(optionType, spot, strike, interestRate, dividend, volatility, expiration);
        } else if (optionType.equalsIgnoreCase("American Call (BS)")) {
            OptionPrice = BSCallAmericanApprox(spot, strike, interestRate, dividend, volatility, expiration);
        } else if (optionType.equalsIgnoreCase("American Put (BS)")) {
            OptionPrice = BSPutAmericanApprox(spot, strike, interestRate, dividend, volatility, expiration);
        }
        return OptionPrice;
    }

    public double BSCallAmericanApprox(double d, double d1, double d2, double d3, double d4, double d5) {
        double d6 = 0;
        d6 = FinMath.BSAmericanCallApprox(d, d1, d5, d2, d2 - d3, d4);
        return d6;
    }

    public double BSPutAmericanApprox(double d, double d1, double d2, double d3, double d4, double d5) {
        double d6 = 0;
        d6 = FinMath.BSAmericanPutApprox(d, d1, d5, d2, d2 - d3, d4);
        return d6;
    }


    double modelPrice() {
        if (option.type.equals(OptionType.CALL)) {

            if (pricingModel == FinConstants.kAmerican) {
                return getPrice("American Call (BS)", option.S(), option.X(), option.r(), option.dividend, option.s(), option.t());
            }
            if (debug) {
                System.out.println("FinRecipes.option_price_call_black_scholes() = " +
                        FinRecipes.optionBlackScholesCall(option.S(), option.X(), option.r(), option.s(), option.t()));
            }
            return getPrice("European Call", option.S(), option.X(), option.r(), option.dividend, option.s(), option.t());
        }
        if (pricingModel == FinConstants.kAmerican) {
            return getPrice("American Put (BS)", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
        } else {
            return getPrice("European Put", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
        }
    }

    public double getPayoff(double InstrumentPrice) {
        return getPayoff(InstrumentPrice, false);
    }

    public double getPayoff(double instrumentPrice, boolean withPremium) {
        double payoff = 0;
        if (option.type.equals(OptionType.CALL)) {
            payoff = Math.max(0, instrumentPrice - option.strike);
        } else {
            payoff = Math.max(0, option.strike - instrumentPrice);
        }
        if (withPremium) {
            payoff -= option.premium;
        }
        return payoff;
    }


    public double getDelta() {
        if (option.type.equals(OptionType.CALL)) {
            if (pricingModel == FinConstants.kAmerican) {
                return blackDelta("American Call (BS)", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
            } else {
                return blackDelta("European Call", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
            }
        } else if (pricingModel == FinConstants.kAmerican) {
            return blackDelta("American Put (BS)", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
        } else {
            return blackDelta("European Put", option.S(), option.X(), option.r(), option.d(), option.s(), option.t());
        }
    }


    public double gamma() {
        double d3 = 0.01D * option.S();
        double dv = 0.01D * option.s();
        if (option.type.equals(OptionType.CALL)) {
            if (pricingModel == FinConstants.kAmerican) {
                double d4 = (1 / (d3 * d3)) * ((getPrice("American Call (BS)", option.S() + d3, option.X(), option.r(), option.d(), option.s(), option.t()) - 2D * getPrice("American Call (BS)", option.S(), option.X(), option.r(), option.d(), option.s(), option.t())) + getPrice("American Call (BS)", option.S() - d3, option.X(), option.r(), option.d(), option.s(), option.t()));
                return d4;
            } else {
                double price = (1 / (d3 * d3)) * ((getPrice("European Call", option.S() + d3, option.X(), option.r(), option.d(), option.s(), option.t()) - 2D * getPrice("European Call", option.S(), option.X(), option.r(), option.d(), option.s(), option.t())) + getPrice("European Call", option.S() - d3, option.X(), option.r(), option.d(), option.s(), option.t()));
                return price;
            }
        } else if (pricingModel == FinConstants.kAmerican) {
            double d4 = (1 / (d3 * d3)) * ((getPrice("American Put (BS)", option.S() + d3, option.X(), option.r(), option.d(), option.s(), option.t()) - 2D * getPrice("American Put (BS)", option.S(), option.X(), option.r(), option.d(), option.s(), option.t())) + getPrice("American Put (BS)", option.S() - d3, option.X(), option.r(), option.d(), option.s(), option.t()));
            return d4;
        } else {
            double d4 = (1 / (d3 * d3)) * ((getPrice("European Put", option.S() + d3, option.X(), option.r(), option.d(), option.s(), option.t()) - 2D * getPrice("European Put", option.S(), option.X(), option.r(), option.d(), option.s(), option.t())) + getPrice("European Put", option.S() - d3, option.X(), option.r(), option.d(), option.s(), option.t()));
            return d4;
        }
    }


    public double theta() {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.Theta(option.S(), option.X(), option.t(), option.s(), option.r(), 92);
        } else {
            return FinMath.Theta(option.S(), option.X(), option.t(), option.s(), option.r(), 93);
        }
    }


    public double rho() {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.Rho(option.S(), option.X(), option.t(), option.s(), option.r(), 92);
        } else {
            return FinMath.Rho(option.S(), option.X(), option.t(), option.s(), option.r(), 93);
        }
    }


    public double vega() {
        System.out.println("Vega:" + FinMath.Vega(option.S(), option.X(), option.t(), option.s(), option.r()));
        double dv = 0.01D * option.s();
        if (option.type.equals(OptionType.CALL)) {
            if (pricingModel == FinConstants.kAmerican) {
                return (1 / dv) * (getPrice("American Call (BS)", option.S(), option.X(), option.r(), d(), option.s() + dv, option.t()) - getPrice("American Call (BS)", option.S(), option.X(), option.r(), d(), option.s(), option.t()));
            } else {
                return (1 / dv) * (getPrice("European Call", option.S(), option.X(), option.r(), d(), option.s() + dv, option.t()) - getPrice("European Call", option.S(), option.X(), option.r(), d(), option.s(), option.t()));
            }
        }
        if (pricingModel == FinConstants.kAmerican) {
            return (1 / dv) * (getPrice("American Put (BS)", option.S(), option.X(), option.r(), d(), option.s() + dv, option.t()) - getPrice("American Put (BS)", option.S(), option.X(), option.r(), d(), option.s(), option.t()));
        } else {
            return (1 / dv) * (getPrice("European Put", option.S(), option.X(), option.r(), d(), option.s() + dv, option.t()) - getPrice("European Put", option.S(), option.X(), option.r(), d(), option.s(), option.t()));
        }
    }


    public double blackDelta(String what,
                             double spot,
                             double strike,
                             double rate,
                             double dividend,
                             double volatility,
                             double time) {
        double delta = 0;
        double d6 = (Math.log(spot / strike) + ((rate - dividend) +
                (volatility * volatility) / 2D) * time) / (volatility * Math.sqrt(time));
        if (what.equalsIgnoreCase("European Call")) {
            delta = Math.exp(-dividend * time) * FinMath.CND(d6);
        } else if (what.equalsIgnoreCase("European Put")) {
            delta = Math.exp(-dividend * time) * (FinMath.CND(d6) - 1);
        } else if (what.equalsIgnoreCase("American Call (BS)")) {
            delta = BSCallAmericanApprox(spot + 1, strike, rate, dividend, volatility, time) - BSCallAmericanApprox(spot, strike, rate, dividend, volatility, time);
        } else if (what.equalsIgnoreCase("American Put (BS)")) {
            delta = BSPutAmericanApprox(spot + 1, strike, rate, dividend, volatility, time) - BSPutAmericanApprox(spot, strike, rate, dividend, volatility, time);
        }
        return delta;
    }

//    public MonteCarlo monteCarlo() {
//        if (monteCarlo != null) {
//            monteCarlo = new MonteCarlo(underlying, (int) expirationDays(), 10000);
//        }
//        return monteCarlo;
//    }
//

    public double binomialPrice() {
        return binomialPrice(50);
    }

    public double binomialPrice(int n) {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.BM(option.S(), option.X(), option.t(), option.s(), option.r(), 92, n);
        } else {
            return FinMath.BM(option.S(), option.X(), option.t(), option.s(), option.r(), 93, n);
        }
    }


    public double monteCarloPrice() {
        return monteCarloPrice(10000);
    }

    public double monteCarloPrice(int n) {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.MC(option.S(), option.X(), option.t(), option.s(), option.r(), 92, n);
        } else {
            return FinMath.MC(option.S(), option.X(), option.t(), option.s(), option.r(), 93, n);
        }
    }


    public double blackScholesPrice() {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.BS(option.S(), option.X(), option.t(), option.s(), option.r(), 92);
        } else {
            return FinMath.BS(option.S(), option.X(), option.t(), option.s(), option.r(), 93);
        }
    }

    public double modelPrice(int model) {
        if (model == FinConstants.BlackScholes) {
            return blackScholesPrice();
        }
        if (model == FinConstants.Binomial) {
            return binomialPrice();
        }
        if (model == FinConstants.Montecarlo1) {
            return monteCarloPrice();
        }
//        double OptionPrice = 0;
//        if (model == FinConstants.Montecarlo2) {
//            monteCarlo();
//            double Price = 0;
//            double instrumentPrice = 0;
//            for (int i = 0; i < monteCarlo.getNTries(); i++) {
//                Price = underlying.premium() * monteCarlo.getSimulatedTargetPremium();
//                instrumentPrice += Price;
//                OptionPrice += getPayoff(Price);
//            }
//            instrumentPrice /= monteCarlo.getNTries();
//            OptionPrice /= monteCarlo.getNTries();
//            OptionPrice = FinMath.PV4(OptionPrice, option.r(), option.t());
//        }
//        return OptionPrice;
        return -1;
    }

    public double modelPrice(int model, double price, double days) {
        if (model == FinConstants.BlackScholes) {
            return blackScholesPrice(price, days);
        }
        if (model == FinConstants.Binomial) {
            return binomialPrice(price, days);
        }
        if (model == FinConstants.Montecarlo1) {
            return monteCarloPrice(price, days);
        }
        double OptionPrice = 0;
//        if (model == FinConstants.Montecarlo2) {
//            monteCarlo();
//            double Price = 0;
//            double instrumentPrice = 0;
//            for (int i = 0; i < monteCarlo.getNTries(); i++) {
//                Price = price * monteCarlo.getSimulatedTargetPremium();
//                instrumentPrice += Price;
//                OptionPrice += getPayoff(Price);
//            }
//
//            instrumentPrice /= monteCarlo.getNTries();
//            OptionPrice /= monteCarlo.getNTries();
//            OptionPrice = FinMath.PV4(OptionPrice, option.r(), days);
//        }
        return OptionPrice;
    }

    double monteCarloPrice(double price, double days) {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.MC(price, option.X(), days, option.s(), option.r(), 92, 10000);
        } else {
            return FinMath.MC(price, option.X(), days, option.s(), option.r(), 93, 10000);
        }
    }

    public double binomialPrice(double price, double days) {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.BM(price, option.X(), days, option.s(), option.r(), 92, 500);
        } else {
            return FinMath.BM(price, option.X(), days, option.s(), option.r(), 93, 500);
        }
    }

    public double blackScholesPrice(double spot, double days) {
        if (option.type.equals(OptionType.CALL)) {
            return FinMath.BS(spot, option.X(), days, option.s(), option.r(), 92);
        } else {
            return FinMath.BS(spot, option.X(), days, option.s(), option.r(), 93);
        }
    }

    public double modelPrice(double price, double days) {
        return modelPrice(FinConstants.BlackScholes, price, days);
    }

}
