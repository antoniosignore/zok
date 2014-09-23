package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Portfolio
import com.dtmc.finance.finpojo.Trade
import com.dtmc.trading.Signal
import com.netnumeri.server.enums.PortfolioTypeEnum
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.utils.DateUtils
import dtmc.PortfolioService

public abstract class Strategy {

    String name
    BackTest tester;
    Portfolio portfolio;
    double wealth = 0;
    Instrument asset;
    List<Signal> signals = new ArrayList<>()

    PortfolioService portfolioService = new PortfolioService()

    def final Date firstDate;
    def final Date lastDate;

    abstract public void evaluateInstrumentOnDate(Date date);

    public Strategy(String name, Instrument asset, Date firstDate, Date lastDate, double wealth) {

        portfolio = new Portfolio(name, "Strategy Portfolio", wealth);
        portfolio.portfolioType = PortfolioTypeEnum.Strategy

        this.asset = asset
        this.wealth = wealth;
        this.portfolio = portfolio
        this.name = name
        tester = new BackTest(this, wealth);
        this.firstDate = firstDate
        this.lastDate = lastDate
    }

    public void add(Trade transaction) {
        portfolioService.add(portfolio, transaction);
    }

    public void test() {
        tester.test(firstDate, lastDate, FinConstants.kInvestOnDate);
    }

    public void run() {
        Date day = firstDate
        Date lastDay = lastDate
        while (DateUtils.isLessEqual(day, lastDay)) {
            if (asset.isDataAvailable(day)) {
                println "evaluate day = $day"
                evaluateInstrumentOnDate(day);
            }
            day = DateUtils.nextDay(day);
        }
    }
}
