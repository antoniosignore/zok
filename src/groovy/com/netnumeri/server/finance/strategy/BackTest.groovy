package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Portfolio
import com.dtmc.finance.finpojo.Trade
import com.dtmc.finance.finpojo.asset.Asset
import com.netnumeri.server.finance.matrix.*

import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.beans.TradeEnum
import com.netnumeri.server.finance.data.TransactionSeries
import com.netnumeri.server.finance.ta.TradeInfo
import com.netnumeri.server.finance.ta.TradeListEntry
import com.netnumeri.server.finance.utils.DateUtils
import dtmc.PortfolioService

public class BackTest implements Serializable {

    PortfolioService portfolioService = new PortfolioService();

    double initialWealth;          // initial wealth
    double accountWealth;          // wealth on bank account
    double investedWealth;         // wealth in instruments
    double totalWealth;            // total wealth
    public TimeSeries PnLSeries = null;   // P&L    time series
    public TimeSeries wealthSeries;       // wealth time series
    public TimeSeries costSeries;         // cost   time series
    public TimeSeries tradeSeriesTotal;   // Trade Total time series
    public TimeSeries tradeSeriesLong;    // Trade Long time series
    public TimeSeries tradeSeriesShort;   // Trade Short time series
    public TimeSeries tradeDistributionTotal;  // Tradedistribution Total time series
    public TimeSeries tradeDistributionLong;   // Tradedistribution Long time series
    public TimeSeries tradeDistributionShort;  // Tradedistribution Short time series
    public TimeSeries wealthUnderwaterLine;     // Drawdown-Line
    Asset asset;
    public Portfolio portfolio;

    TransactionSeries transactionSerie;  // trade transaction series
    Double transactionCost;    // transaction cost
    Portfolio benchmarkPortfolio;

    double totalCost;
    public int numberTradesTotal;     // number of trades in all positions
    public int numberTradesLong;      // number of trades in long positions
    public int numberTradesShort;     // number of trades in short positions
    int numberWinTradesTotal;  // number of win trades in all positions
    int numberWinTradesLong;   // number of win trades in long positions
    int numberWinTradesShort;  // number of win trades in short positions
    int numberLossTradesTotal; // number of loss trades in all positions
    int numberLossTradesLong;  // number of loss trades in all positions
    int numberLossTradesShort; // number of loss trades in all positions

    int numberOpenPositionsTotal; // number of all open positions
    int numberOpenPositionsLong;  // number of all long positions
    int numberOpenPositionsShort; // number of all short positions
    long numberTradeDays;
    int numberHoldDays;
    int numberMeanBarsInWinTotal;  // number of average bars in winners total
    int numberMeanBarsInWinLong;  // number of average bars in winners long
    int numberMeanBarsInWinShort;  // number of average bars in winners short
    int numberMeanBarsInLossTotal;  // number of average bars in loosers total
    int numberMeanBarsInLossLong;  // number of average bars in loosers long
    int numberMeanBarsInLossShort;  // number of average bars in loosers short
    double grossProfitTotal; // total amount of wins in all positions
    double grossProfitLong;  // total amount of wins in long positions
    double grossProfitShort; // total amount of wins in short positions
    double grossLossTotal;   // total amount of losses in all positions
    double grossLossLong;    // total amount of losses in long positions
    double grossLossShort;   // total amount of losses in short positions
    double maxWinTotal;      // max win in one trade long or short
    double maxWinLong;       // max win in one trade only long position
    double maxWinShort;      // max win in one trade only short position
    double maxLossTotal;     // max loss in one trade long or short
    double maxLossLong;      // max loss in one trade only long position
    double maxLossShort;     // max loss in one trade only short position
    double openPositionTotal;// amount of all open position
    double openPositionLong;// amount of long open position
    double openPositionShort;// amount of short open position
    double maxIntradayDrawDown;     // max intraday drawdown total
    Date maxIntradayDrawDownDate; // max intraday drawdown date
    double wealth;

    int firstEntry;
    int lastEntry;
    boolean isTested;
    ArrayList<TradeListEntry> tradeList = new ArrayList();

    public BackTest(Strategy strategy, double InitialWealth) {
        super();
        initialWealth = InitialWealth;
//        signalSeries = null;
//        transactionSerie = strategy.transactionSeries
        init();
    }

//    public Backtest(SignalSeries signalSeries, double InitialWealth) {
//        super();
//        this.signalSeries = signalSeries;
//        initialWealth = InitialWealth;
//        transactionSerie = null;
//        portfolio = null;
//        asset = signalSeries.getAsset();
//        init();
//    }

    public BackTest(Portfolio initPortfolio, double InitialWealth) {
        super();
        this.initialWealth = InitialWealth;
//        this.signalSeries = null;
//        this.transactionSerie = transactionSeries;
        this.portfolio = initPortfolio;
        init();
    }

    public void init() {
        benchmarkPortfolio = null;
        transactionCost = null;

        PnLSeries = new TimeSeries("PnL");
        wealthSeries = new TimeSeries("Wealth");
        costSeries = new TimeSeries("Cost");
        tradeSeriesTotal = new TimeSeries("Total Trades");
        tradeSeriesLong = new TimeSeries("Long Trades");
        tradeSeriesShort = new TimeSeries("Short Trades");
        tradeDistributionTotal = new TimeSeries("Distribution");
        tradeDistributionLong = new TimeSeries("Long Distribution");
        tradeDistributionShort = new TimeSeries("Short Distribution");
        wealthUnderwaterLine = new TimeSeries("Underwater");

        numberTradeDays = 0;
        numberHoldDays = 0;
        isTested = false;
    }

    public double getWealthGain() {
        return wealth - initialWealth;
    }

    // net profit
    public double getNetProfitTotal() {
        return grossProfitTotal + grossLossTotal;
    }

    public double getNetProfitLong() {
        return grossProfitLong + grossLossLong;
    }

    public double getNetProfitShort() {
        return grossProfitShort + grossLossShort;
    }

    public void changed() {
        isTested = false;
    }

    public void setBenchmarkPortfolio(Asset benchmarkAsset) {
        benchmarkPortfolio = new Portfolio("Benchmark");
        portfolioService.add(benchmarkPortfolio, benchmarkAsset);
    }

    public double test() {
        return test(null, null, FinConstants.kInvestOnDate);
    }

    public double test(Date firstDate, Date lastDate, FinConstants option) {
        numberTradesTotal = 0;
        numberTradesLong = 0;
        numberTradesShort = 0;
        numberWinTradesTotal = 0;
        numberWinTradesLong = 0;
        numberWinTradesShort = 0;
        numberLossTradesTotal = 0;
        numberLossTradesLong = 0;
        numberLossTradesShort = 0;
        grossProfitTotal = 0;
        grossProfitLong = 0;
        grossProfitShort = 0;
        grossLossTotal = 0;
        grossLossLong = 0;
        grossLossShort = 0;
        maxWinTotal = 0;
        maxWinLong = 0;
        maxWinShort = 0;
        maxLossTotal = 0;
        maxLossLong = 0;
        maxLossShort = 0;
        numberHoldDays = 0;
        totalCost = 0;
        wealth = initialWealth;
        maxIntradayDrawDown = 0;

        PnLSeries.clear();
        wealthSeries.clear();
        costSeries.clear();
        tradeSeriesTotal.clear();
        tradeSeriesLong.clear();
        tradeSeriesShort.clear();
        tradeDistributionTotal.clear();
        tradeDistributionLong.clear();
        tradeDistributionShort.clear();

        // Trade series
        processTransactionSerie(firstDate, lastDate)
        isTested = true;
        return 0;
    }

    private void processTransactionSerie(Date firstDate, Date lastDate) {

        double PnL = 0.0;
        double cost = 0;
        double currentAccount = initialWealth;
        double CurrentWealth = initialWealth;
        double previousWealth = initialWealth;
        double maxWealth = initialWealth;
        double aktDrawDown = 0;

        Trade transaction;
        Trade transactionPair;

        if (firstDate == null) firstDate = portfolio.getFirstDate();
        if (lastDate == null) lastDate = portfolio.getLastDate();
        if (firstDate == null || lastDate == null) throw new RuntimeException("dates cannot be null");

        Date date = firstDate;
        Date last = lastDate;

        while (DateUtils.isLessEqual(date, last)) {

            println "date = $date"

            transactionPair = null;
            cost = 0;

            transaction = transactionSerie.getTransaction(date)

            if (transaction != null) {

                Date transactionDate = transaction.tradeDate;

                println "date = $date"
                println "date = $transactionDate"
                println "transaction = $transaction.tradeAction"

                if (transaction.getTradeAction() == TradeEnum.BUY) {
                    currentAccount -= (transaction.getValue() + transaction.getCost());
                    Instrument instrument = transaction.getInstrument();
                    transactionPair = transactionSerie.getPair(date, transaction.getTradeAction());
                    if (transactionPair == null) {
                        Date operationDate = instrument.lastDate()
                        double operationLastPrice = instrument.close(operationDate);
                        double operationEntryPrice = transaction.getPrice();
                        int operationAmount = transaction.getAmount();
                        openPositionLong += ((operationLastPrice - operationEntryPrice) * operationAmount) - transaction.getCost();
                        numberOpenPositionsLong++;
                    }
                } else if (transaction.getTradeAction() == TradeEnum.SELL) {
                    currentAccount += (transaction.getValue() - transaction.getCost());

                    transactionPair = transactionSerie.getPair(date, transaction.getTradeAction());
                    if (transactionPair != null) {
                        double tradeBuyWealth = (transactionPair.getValue() + transactionPair.getCost());
                        double tradeSellWealth = (transaction.getValue() - transaction.getCost());
                        double wealthDiff = tradeSellWealth - tradeBuyWealth;
                        if (wealthDiff > 0) {
                            grossProfitLong += wealthDiff;
                            numberWinTradesLong++;
                        } else {
                            grossLossLong += wealthDiff;
                            numberLossTradesLong++;
                        }
                        tradeSeriesTotal.add(date, wealthDiff);
                        tradeSeriesLong.add(date, wealthDiff);
                        tradeDistributionTotal.add(date, wealthDiff);
                        tradeDistributionLong.add(date, wealthDiff);
                        addToTradeList(transaction, transactionPair, wealthDiff, FinConstants.LONG);
                    }
                } else if (transaction.getTradeAction() == TradeEnum.SELLSHORT) {
                    currentAccount += (transaction.getValue() - transaction.getCost());

                    Instrument instrument = transaction.getInstrument();
                    transactionPair = transactionSerie.getPair(date, transaction.getTradeAction());
                    if (transactionPair == null) {
                        Date operationDate = instrument.lastDate;
                        double operationLastPrice = instrument.close(operationDate);
                        double entryPrice = transaction.getPrice();
                        int operationAmount = transaction.getAmount();
                        openPositionShort += ((operationLastPrice - entryPrice) * operationAmount) - transaction.getCost();
                        numberOpenPositionsShort++;
                    }
                } else if (transaction.getTradeAction() == TradeEnum.BUYSHORT) {
                    currentAccount -= (transaction.getValue() + transaction.getCost());

                    transactionPair = transactionSerie.getPair(date, transaction.getTradeAction());
                    if (transactionPair != null) {
                        double TradeSellShortWealth = (transactionPair.getValue() - transactionPair.getCost());
                        double TradeBuyShortWealth = (transaction.getValue() + transaction.getCost());
                        double WealthDiff = TradeSellShortWealth - TradeBuyShortWealth;
                        if (WealthDiff > 0) {
                            grossProfitShort += WealthDiff;
                            numberWinTradesShort++;
                        } else {
                            grossLossShort += WealthDiff;
                            numberLossTradesShort++;
                        }
                        tradeSeriesTotal.add(date, WealthDiff);
                        tradeSeriesShort.add(date, WealthDiff);
                        tradeDistributionTotal.add(date, WealthDiff);
                        tradeDistributionShort.add(date, WealthDiff);
                        addToTradeList(transaction, transactionPair, WealthDiff, FinConstants.SHORT);
                    }
                }

                portfolioService.add(portfolio, transaction);
                cost += transaction.getCost();
            }

            double mark2market = 0;
            mark2market = portfolioService.m2m(portfolio, date);
            CurrentWealth = currentAccount + mark2market;
            wealthSeries.add(date, CurrentWealth);
            PnL = CurrentWealth - previousWealth;
            PnLSeries.add(date, PnL);
            previousWealth = CurrentWealth;
            costSeries.add(date, cost);
            totalCost += cost;
            maxWealth = Math.max(maxWealth, CurrentWealth);
            aktDrawDown = CurrentWealth - maxWealth;
            if (aktDrawDown > 0) aktDrawDown = 0;
            wealthUnderwaterLine.add(date, aktDrawDown);
            if (aktDrawDown < maxIntradayDrawDown) {
                maxIntradayDrawDown = aktDrawDown;
                maxIntradayDrawDownDate = date;
            }

            date = DateUtils.nextDay(date);
        }

        numberTradesLong = numberWinTradesLong + numberLossTradesLong;
        numberTradesShort = numberWinTradesShort + numberLossTradesShort;
        numberTradesTotal = numberTradesLong + numberTradesShort;
        numberWinTradesTotal = numberWinTradesLong + numberWinTradesShort;
        numberLossTradesTotal = numberLossTradesLong + numberLossTradesShort;
        grossProfitTotal = grossProfitLong + grossProfitShort;
        grossLossTotal = grossLossLong + grossLossShort;
        wealth = CurrentWealth;
        System.out.println("Wealth      : " + CurrentWealth);
        System.out.println("Account     : " + currentAccount);
        System.out.println("Portfolio   : " + portfolioService.price(portfolio, lastDate));
        println "numberWinTradesTotal = $numberWinTradesTotal"
        println "numberLossTradesTotal = $numberLossTradesTotal"

        if (benchmarkPortfolio != null) {
            System.out.println("print benchmark portfolio");
            System.out.println(benchmarkPortfolio.toString());
        }
    }

    public int getTradeListEntryNumber(Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (tradeList == null) throw new IllegalArgumentException("tradeList cannot be null");
        int NInstruments = tradeList.size();
        for (int i = 0; i < NInstruments; i++)
            if (((TradeListEntry) tradeList.get(i)).getInstrument().equals(instrument)) return i + 1;
        return 0;
    }

    public void addToTradeList(Trade transaction,
                               Trade transactionPair,
                               double WealthDiff,
                               FinConstants transactionType) {
        Instrument instrument;
        int tradeDuration;
        double entryPrice, close, high, low, TradeResult;
        Date entryDate, closeDate;
        int entryNumbers;
        instrument = transaction.getInstrument();
        if ((transactionPair != null) && (transaction != null)) {
            entryNumbers = getTradeListEntryNumber(instrument);
            if (entryNumbers == 0) {
                tradeList.add(new TradeListEntry(instrument));
                entryNumbers = 1;
            }
            ((TradeListEntry) tradeList.get(entryNumbers - 1)).numberOfTrades++;
            ((TradeListEntry) tradeList.get(entryNumbers - 1)).tradeResult += WealthDiff;
            entryPrice = transactionPair.getPrice();
            entryDate = transactionPair.getTradeDate();
            close = transaction.getPrice();
            closeDate = transaction.getTradeDate();

            if (transactionType == FinConstants.LONG)
                TradeResult = close - entryPrice;
            else
                TradeResult = entryPrice - close;

            // todo
            //   Date dateBefore = entryDate.prevDay();
            TimeSeries series = instrument.getSeries(FinConstants.CLOSE, entryDate, closeDate);
            high = series.getMax();
            low = series.getMin();
            tradeDuration = series.getNNonZero();
            TradeInfo tradeInfo = new TradeInfo(entryPrice,
                    high,
                    low,
                    close,
                    TradeResult,
                    transactionType,
                    tradeDuration,
                    entryDate,
                    closeDate,
                    instrument);
            TradeListEntry entry = (TradeListEntry) tradeList.get(entryNumbers - 1);
            ArrayList list = entry.getTradeInfoList();
            list.add(tradeInfo);
        }
    }

    public double getPnL(Date date) {
        if (!isTested) test();
        Date lastDate = PnLSeries.lastDate();
        if (date == null || lastDate == null) return 0;
        if (DateUtils.isGreater(date, lastDate)) return PnLSeries.getData(lastDate);
        else return PnLSeries.getData(date);
    }

    public TimeSeries getPnLSeries() {
        if (!isTested) test();
        return PnLSeries;
    }

    public double getPnLMin() {
        if (!isTested) test();
        return PnLSeries.getMin();
    }

    public double getPnLMax() {
        if (!isTested) test();
        return PnLSeries.getMax();
    }

    public double getPnLMean() {
        if (!isTested) test();
        return PnLSeries.getMean();
    }

    public double getPnLStdDev() {
        if (!isTested) test();
        return PnLSeries.getStandardDeviation();
    }

    public double getPnLVariance() {
        if (!isTested) test();
        return PnLSeries.getVariance();
    }

    public double getPnLSharpeRatio() {
        if (!isTested) test();
        return getPnLSharpeRatio(0);
    }

    public double getPnLSharpeRatio(double InterestRate) {
        if (!isTested) test();
        double Mean = getPnLMean();
        double StdDev = getPnLStdDev();
        if (StdDev != 0) return (Mean - InterestRate) / StdDev;
        else return 0;
    }

    public Date getPnLMinDate() {
        if (!isTested) test();
        Date firstDate = PnLSeries.firstDate();
        Date lastDate = PnLSeries.lastDate();
        double Min = PnLSeries.getData(firstDate);
        Date minDate = firstDate;

        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!PnLSeries.isEmpty(dd)) {
                    double data = PnLSeries.getData(dd);
                    if (data < Min) {
                        Min = data;
                        minDate = dd;
                    }
                }
            }
            return minDate;
        }
        return null;
    }

    public Date getPnLMaxDate() {
        if (!isTested) test();
        Date firstDate = PnLSeries.firstDate();
        Date lastDate = PnLSeries.lastDate();
        double Max = PnLSeries.getData(firstDate);
        Date maxDate = firstDate;
        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!PnLSeries.isEmpty(dd)) {
                    if (PnLSeries.getData(dd) > Max) {
                        Max = PnLSeries.getData(dd);
                        maxDate = dd;
                    }
                }
            }
        }
        return maxDate;
    }

    /*
    TH1* getPnLDistribution(int NBins) {
        // Return distribution histogram of PnL series
        if (!isTested)
            performance();
        return PnLSeries.getDistribution(NBins);
    }
     */

    public double getWealth(Date date) {
        if (!isTested) test();
        Date lastDate;
        lastDate = wealthSeries.lastDate();
        if (date == null || lastDate == null) return 0;
        if (DateUtils.isGreater(date, lastDate)) return wealthSeries.getData(lastDate);
        else return wealthSeries.getData(date);
    }

    public TimeSeries getWealthSeries() {
        if (!isTested) test();
        return wealthSeries;
    }

    public TimeSeries getWealthUnderwaterLineSeries() {
        if (!isTested) test();
        return wealthUnderwaterLine;
    }

    public double getWealthMin() {
        if (!isTested) test();
        return wealthSeries.getMin();
    }

    public double getWealthMax() {
        if (!isTested) test();
        return wealthSeries.getMax();
    }

    public double getWealthMean() {
        if (!isTested) test();
        return wealthSeries.getMean();
    }

    public double getWealthStdDev() {
        if (!isTested) test();
        return wealthSeries.getStandardDeviation();
    }

    public double getWealthVariance() {
        if (!isTested) test();
        return wealthSeries.getVariance();
    }

    public Date getWealthMinDate() {
        if (!isTested) test();
        Date firstDate = wealthSeries.firstDate();
        Date lastDate = wealthSeries.lastDate();
        double Min = wealthSeries.getData(firstDate);
        Date minDate = firstDate;

        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!wealthSeries.isEmpty(dd)) {
                    if (wealthSeries.getData(dd) < Min) {
                        Min = wealthSeries.getData(dd);
                        minDate = dd;
                    }
                }
            }
        }
        return minDate;
    }

    public Date getWealthMaxDate() {
        if (!isTested) test();
        Date firstDate = wealthSeries.firstDate();
        Date lastDate = wealthSeries.lastDate();
        double Max = wealthSeries.getData(firstDate);
        Date maxDate = firstDate;
        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!wealthSeries.isEmpty(dd)) {
                    if (wealthSeries.getData(dd) > Max) {
                        Max = wealthSeries.getData(dd);
                        maxDate = dd;

                    }
                }
            }
        }
        return maxDate;
    }

    public TimeSeries getCostSeries() {
        if (!isTested) test();
        return costSeries;
    }

    public double getCostMin() {
        if (!isTested) test();
        return costSeries.getMin();
    }

    public double getCostMax() {
        if (!isTested) test();
        return costSeries.getMax();
    }

    public double getCostMean() {
        if (!isTested) test();
        return costSeries.getMean();
    }

    public double getCostStdDev() {
        if (!isTested) test();
        return costSeries.getStandardDeviation();
    }

    public double getCostVariance() {
        if (!isTested) test();
        return costSeries.getVariance();
    }

    public Date getCostMinDate() {
        if (!isTested) test();
        Date firstDate = costSeries.firstDate();
        Date lastDate = costSeries.lastDate();
        double Min = costSeries.getData(firstDate);
        Date minDate = firstDate;
        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!costSeries.isEmpty(dd)) {
                    if (costSeries.getData(dd) < Min) {
                        Min = costSeries.getData(dd);
                        minDate = dd;
                    }
                }
            }
        }
        return minDate;
    }

    public Date getCostMaxDate() {
        if (!isTested) test();
        Date firstDate = costSeries.firstDate();
        Date lastDate = costSeries.lastDate();
        double Max = costSeries.getData(firstDate);
        Date maxDate = firstDate;
        if (firstDate != null) {
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if (!costSeries.isEmpty(dd)) {
                    if (costSeries.getData(dd) > Max) {
                        Max = costSeries.getData(dd);
                        maxDate = dd;
                    }
                }
            }
        }
        return maxDate;
    }

    public TimeSeries getTradeSeriesTotal() {
        if (!isTested) test();
        return tradeSeriesTotal;
    }

    public TimeSeries getTradeSeriesLong() {
        if (!isTested) test();
        return tradeSeriesLong;
    }

    public TimeSeries getTradeSeriesShort() {
        if (!isTested) test();
        return tradeSeriesShort;
    }

    public double getTradeMaxWinTotal() {
        if (!isTested) test();

        try {
            return tradeDistributionTotal.getMax();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMaxWinLong() {
        if (!isTested) test();

        try {
            return tradeDistributionLong.getMax();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMaxWinShort() {
        if (!isTested) test();

        try {
            return tradeDistributionShort.getMax();
        } catch (Throwable ex) {
            return Double.NaN;
        }

    }

    public double getTradeMaxLossTotal() {
        if (!isTested) test();
        try {
            return tradeDistributionTotal.getMin();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMaxLossLong() {
        if (!isTested) test();
        try {
            return tradeDistributionLong.getMin();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMaxLossShort() {
        if (!isTested) test();
        try {
            return tradeDistributionShort.getMin();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMeanTotal() {
        if (!isTested) test();
        try {
            return tradeDistributionTotal.getMean();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMeanLong() {
        if (!isTested) test();
        try {
            return tradeDistributionLong.getMean();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeMeanShort() {
        if (!isTested) test();
        try {
            return tradeDistributionShort.getMean();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeStdDevTotal() {
        if (!isTested) test();
        try {
            return tradeDistributionTotal.getStandardDeviation();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeStdDevLong() {
        if (!isTested) test();
        try {
            return tradeDistributionLong.getStandardDeviation();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeStdDevShort() {
        if (!isTested) test();
        try {
            return tradeDistributionShort.getStandardDeviation();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }


    public double getTradeVarianceTotal() {
        if (!isTested) test();
        try {
            return tradeDistributionTotal.getVariance();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeVarianceLong() {
        if (!isTested) test();
        try {
            return tradeDistributionLong.getVariance();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public double getTradeVarianceShort() {
        if (!isTested) test();
        try {
            return tradeDistributionShort.getVariance();
        } catch (Throwable ex) {
            return Double.NaN;
        }
    }

    public Date getTradeMaxWinDateTotal() {
        if (!isTested) test();

        if (tradeDistributionTotal.getSize() > 0) {
            Date firstDate = tradeDistributionTotal.firstDate();
            Date lastDate = tradeDistributionTotal.lastDate();
            double Max = tradeDistributionTotal.getData(firstDate);
            Date maxDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionTotal.isEmpty(dd)) {
                        if (tradeDistributionTotal.getData(dd) > Max) {
                            Max = tradeDistributionTotal.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else return null;
    }

    public Date getTradeMaxWinDateLong() {
        if (!isTested) test();

        if (tradeDistributionLong.getSize() > 0) {
            Date firstDate = tradeDistributionLong.firstDate();
            Date lastDate = tradeDistributionLong.lastDate();

            double Max = tradeDistributionLong.getData(firstDate);
            Date maxDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionLong.isEmpty(dd)) {
                        if (tradeDistributionLong.getData(dd) > Max) {
                            Max = tradeDistributionLong.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else return null;
    }

    public Date getTradeMaxWinDateShort() {
        if (!isTested) test();

        if (tradeDistributionShort.getSize() > 0) {
            Date firstDate = tradeDistributionShort.firstDate();
            Date lastDate = tradeDistributionShort.lastDate();

            double Max = tradeDistributionShort.getData(firstDate);
            Date maxDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionShort.isEmpty(dd)) {
                        if (tradeDistributionShort.getData(dd) > Max) {
                            Max = tradeDistributionShort.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else
            return null;
    }

    public Date getTradeMaxLossDateTotal() {
        if (!isTested) test();
        if (tradeDistributionTotal.getSize() > 0) {
            Date firstDate = tradeDistributionTotal.firstDate();
            Date lastDate = tradeDistributionTotal.lastDate();
            double Min = tradeDistributionTotal.getData(firstDate);
            Date minDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionTotal.isEmpty(dd)) {
                        if (tradeDistributionTotal.getData(dd) < Min) {
                            Min = tradeDistributionTotal.getData(dd);
                            minDate = dd;
                        }
                    }
                }
            }
            return minDate;
        } else return null;
    }

    Date getTradeMaxLossDateLong() {
        if (!isTested) test();

        if (tradeDistributionLong.getSize() > 0) {
            Date firstDate = tradeDistributionLong.firstDate();
            Date lastDate = tradeDistributionLong.lastDate();

            double Min = tradeDistributionLong.getData(firstDate);
            Date minimumDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionLong.isEmpty(dd)) {
                        if (tradeDistributionLong.getData(dd) < Min) {
                            Min = tradeDistributionLong.getData(dd);
                            minimumDate = dd;
                        }
                    }
                }
            }
            return minimumDate;
        } else return null;
    }

    public Date getTradeMaxLossDateShort() {
        if (!isTested) test();

        if (tradeDistributionShort.getSize() > 0) {
            Date firstDate = tradeDistributionShort.firstDate();
            Date lastDate = tradeDistributionShort.lastDate();
            double Min = tradeDistributionShort.getData(firstDate);
            Date minimumDate = firstDate;
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if (!tradeDistributionShort.isEmpty(dd)) {
                        if (tradeDistributionShort.getData(dd) < Min) {
                            Min = tradeDistributionShort.getData(dd);
                            minimumDate = dd;
                        }
                    }
                }
            }
            return minimumDate;
        } else return null;
    }

    public int getNMaxConsecWinTradesTotal() {
        int NConsecWinTradesTotal = 0;
        int NConsecWinTradesTotalReturn = 0;
        if (!isTested) test();

        if (tradeDistributionTotal.getSize() > 0) {
            Date firstDate = tradeDistributionTotal.firstDate();
            Date lastDate = tradeDistributionTotal.lastDate();
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if ((!tradeDistributionTotal.isEmpty(dd))) {
                        if (tradeDistributionTotal.getData(dd) > 0) {
                            NConsecWinTradesTotal++;
                            NConsecWinTradesTotalReturn = Math.max(NConsecWinTradesTotalReturn, NConsecWinTradesTotal);
                        } else NConsecWinTradesTotal = 0;
                    }
                }
            }
            return NConsecWinTradesTotalReturn;
        } else return 0;
    }

    public int getNMaxConsecWinTradesLong() {
        int NConsecWinTradesLong = 0;
        int NConsecWinTradesLongReturn = 0;
        if (!isTested) test();

        if (tradeDistributionLong.getSize() > 0) {
            Date firstDate = tradeDistributionLong.firstDate();
            Date lastDate = tradeDistributionLong.lastDate();
            Date dd = firstDate;
            Date end = lastDate;
            for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                if ((!tradeDistributionLong.isEmpty(dd))) {
                    if (tradeDistributionLong.getData(dd) > 0) {
                        NConsecWinTradesLong++;
                        NConsecWinTradesLongReturn = Math.max(NConsecWinTradesLongReturn, NConsecWinTradesLong);
                    } else NConsecWinTradesLong = 0;
                }
            }
            return NConsecWinTradesLongReturn;
        } else return 0;
    }

    public int getNMaxConsecWinTradesShort() {
        int NConsecWinTradesShort = 0;
        int NConsecWinTradesShortReturn = 0;
        if (!isTested) test();

        if (tradeDistributionShort.getSize() > 0) {
            Date firstDate = tradeDistributionShort.firstDate();
            Date lastDate = tradeDistributionShort.lastDate();

            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if ((!tradeDistributionShort.isEmpty(dd))) {
                        if (tradeDistributionShort.getData(dd) > 0) {
                            NConsecWinTradesShort++;
                            NConsecWinTradesShortReturn = Math.max(NConsecWinTradesShortReturn, NConsecWinTradesShort);
                        } else NConsecWinTradesShort = 0;
                    }
                }
            }
            return NConsecWinTradesShortReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesTotal() {
        int NConsecLossTradesTotal = 0;
        int NConsecLossTradesTotalReturn = 0;
        if (!isTested) test();

        if (tradeDistributionTotal.getSize() > 0) {
            Date firstDate = tradeDistributionTotal.firstDate();
            Date lastDate = tradeDistributionTotal.lastDate();

            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if ((!tradeDistributionTotal.isEmpty(dd))) {
                        if (tradeDistributionTotal.getData(dd) < 0) {
                            NConsecLossTradesTotal++;
                            NConsecLossTradesTotalReturn = Math.max(NConsecLossTradesTotalReturn, NConsecLossTradesTotal);
                        } else NConsecLossTradesTotal = 0;
                    }
                }
            }
            return NConsecLossTradesTotalReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesLong() {
        int NConsecLossTradesLong = 0;
        int NConsecLossTradesLongReturn = 0;
        if (!isTested) test();

        if (tradeDistributionLong.getSize() > 0) {
            Date firstDate = tradeDistributionLong.firstDate();
            Date lastDate = tradeDistributionLong.lastDate();
            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if ((!tradeDistributionLong.isEmpty(dd))) {
                        if (tradeDistributionLong.getData(dd) < 0) {
                            NConsecLossTradesLong++;
                            NConsecLossTradesLongReturn = Math.max(NConsecLossTradesLongReturn, NConsecLossTradesLong);
                        } else NConsecLossTradesLong = 0;
                    }
                }
            }
            return NConsecLossTradesLongReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesShort() {
        // Return number of max. consecutive loosers short
        int NConsecLossTradesShort = 0;
        int NConsecLossTradesShortReturn = 0;
        if (!isTested) test();

        if (tradeDistributionShort.getSize() > 0) {
            Date firstDate = tradeDistributionShort.firstDate();
            Date lastDate = tradeDistributionShort.lastDate();

            if (firstDate != null) {
                Date dd = firstDate;
                Date end = lastDate;
                for (; DateUtils.isLessEqual(dd, end); dd = DateUtils.nextDay(dd)) {
                    if ((!tradeDistributionShort.isEmpty(dd))) {
                        if (tradeDistributionShort.getData(dd) < 0) {
                            NConsecLossTradesShort++;
                            NConsecLossTradesShortReturn = Math.max(NConsecLossTradesShortReturn, NConsecLossTradesShort);
                        } else NConsecLossTradesShort = 0;
                    }
                }
            }
            return NConsecLossTradesShortReturn;
        } else return 0;
    }

    public double getTradesPercentProfitableTotal() {
        if (numberTradesTotal != 0) return ((double) numberWinTradesTotal / numberTradesTotal * 100);
        else return 0;
    }

    public double getTradesPercentProfitableLong() {
        if (numberTradesLong != 0) return ((double) numberWinTradesLong / numberTradesLong * 100);
        else return 0;
    }

    public double getTradesPercentProfitableShort() {
        if (numberTradesShort != 0) return ((double) numberWinTradesShort / numberTradesShort * 100);
        else return 0;
    }

    public double getTradeMeanWinTotal() {
        if (numberWinTradesTotal != 0) return grossProfitTotal / numberWinTradesTotal;
        else return 0;
    }

    public double getTradeMeanWinLong() {
        if (numberWinTradesLong != 0) return grossProfitLong / numberWinTradesLong;
        else return 0;
    }

    public double getTradeMeanWinShort() {
        if (numberWinTradesShort != 0) return grossProfitShort / numberWinTradesShort;
        else return 0;
    }

    public double getTradeMeanLossTotal() {
        if (numberLossTradesTotal != 0) return grossLossTotal / numberLossTradesTotal;
        else return 0;
    }

    public double getTradeMeanLossLong() {
        if (numberLossTradesLong != 0) return grossLossLong / numberLossTradesLong;
        else return 0;
    }

    public double getTradeMeanLossShort() {
        if (numberLossTradesShort != 0) return grossLossShort / numberLossTradesShort;
        else return 0;
    }

    public double getTradeRatioMeanWinLossTotal() {
        if (getTradeMeanLossTotal() != 0) return (getTradeMeanWinTotal() / getTradeMeanLossTotal());
        else return 0;
    }

    public double getTradeRatioMeanWinLossLong() {
        if (getTradeMeanLossLong() != 0) return (getTradeMeanWinLong() / getTradeMeanLossLong());
        else return 0;
    }

    public double getTradeRatioMeanWinLossShort() {
        if (getTradeMeanLossShort() != 0) return (getTradeMeanWinShort() / getTradeMeanLossShort());
        else return 0;
    }

    // profit factor
    public double getProfitFactorTotal() {
        if (Math.abs(grossLossTotal) != 0) return grossProfitTotal / Math.abs(grossLossTotal);
        else return 0;
    }

    public double getProfitFactorLong() {
        if (Math.abs(grossLossLong) != 0) return grossProfitLong / Math.abs(grossLossLong);
        else return 0;
    }

    public double getProfitFactorShort() {
        if (Math.abs(grossLossShort) != 0) return grossProfitShort / Math.abs(grossLossShort);
        else return 0;
    }

    public double getTradeSharpRatioTotal() {
        if (getTradeStdDevTotal() != 0) return (getTradeMeanTotal() / getTradeStdDevTotal());
        else return 0;
    }

    public double getTradeSharpeRatioLong() {
        if (getTradeStdDevLong() != 0) return (getTradeMeanLong() / getTradeStdDevLong());
        else return 0;
    }

    public double getTradeSharpeRatioShort() {
        if (getTradeStdDevShort() != 0) return (getTradeMeanShort() / getTradeStdDevShort());
        else return 0;
    }

    public String toXMLString() {
        if (!isTested) test();
        StringBuffer sb = new StringBuffer("<TABLE>");
        sb.append("<tr><td>PNLMEAN</td><td>" + getPnLMean() + "</td></tr>\n");
        sb.append("<tr><td>PNLVARIANCE</td><td>" + getPnLVariance() + "</td></tr>\n");
        sb.append("<tr><td>PNLSTDDEV</td><td>" + getPnLStdDev() + "</td></tr>\n");
        sb.append("<tr><td>PNLSHARPERATIO</td><td>" + getPnLSharpeRatio() + "</td></tr>\n");
        sb.append("<tr><td>PNLMIN</td><td>" + getPnLMin() + "</td></tr>\n");
        sb.append("<tr><td>PNLMINDATE</td><td>" + getPnLMinDate() + "</td></tr>\n");
        sb.append("<tr><td>PNLMAX</td><td>" + getPnLMax() + "</td></tr>\n");
        sb.append("<tr><td>PNLMAXDATE</td><td>" + getPnLMaxDate() + "</td></tr>\n");

        sb.append("<tr><td>WEALTHMEAN</td><td>" + getWealthMean() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHVARIANCE</td><td>" + getWealthVariance() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHSTDDEV</td><td>" + getWealthStdDev() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHMIN</td><td>" + getWealthMin() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHMINDATE</td><td>" + getWealthMinDate() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHMAX</td><td>" + getWealthMax() + "</td></tr>\n");
        sb.append("<tr><td>WEALTHMAXDATE</td><td>" + getWealthMaxDate() + "</td></tr>\n");

        sb.append("<tr><td>COSTMEAN</td><td>" + getCostMean() + "</td></tr>\n");
        sb.append("<tr><td>COSTVARIANCE</td><td>" + getCostVariance() + "</td></tr>\n");
        sb.append("<tr><td>COSTSTDDEV</td><td>" + getCostStdDev() + "</td></tr>\n");
        sb.append("<tr><td>COSTMIN</td><td>" + getCostMin() + "</td></tr>\n");
        sb.append("<tr><td>COSTMINDATE</td><td>" + getCostMinDate() + "</td></tr>\n");
        sb.append("<tr><td>COSTMAX</td><td>" + getCostMax() + "</td></tr>\n");
        sb.append("<tr><td>COSTMAXDATE</td><td>" + getCostMaxDate() + "</td></tr>\n");

        sb.append("<tr><td>TOTALWL</td><td>" + getTradeMeanTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALMAXWIN</td><td>" + getTradeMaxWinTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALMAXWINDATE</td><td>" + getTradeMaxWinDateTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALMAXLOSS</td><td>" + getTradeMaxLossTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALMAXLOSSDATE</td><td>" + getTradeMaxLossDateTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALNTRADES</td><td>" + this.numberTradesTotal + "</td></tr>\n");
        sb.append("<tr><td>TOTALNWINTRADES</td><td>" + numberWinTradesTotal + "</td></tr>\n");
        sb.append("<tr><td>TOTALNLOSSTRADES</td><td>" + numberLossTradesTotal + "</td></tr>\n");
        sb.append("<tr><td>TOTALPERCENTPROFIT</td><td>" + getTradesPercentProfitableTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALNCONWINTRADES</td><td>" + getNMaxConsecWinTradesTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALNCONLOSSTRADES</td><td>" + getNMaxConsecLossTradesTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALAVGWINTRADE</td><td>" + getTradeMeanWinTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALAVGLOSSTRADE</td><td>" + getTradeMeanLossTotal() + "</td></tr>\n");

        sb.append("<tr><td>TOTALMAXDRAWDOWN</td><td>" + getMaxIntradayDrawDown() + "</td></tr>\n");
        sb.append("<tr><td>TOTALMAXDRAWDOWNDATE</td><td>" + getMaxIntradayDrawDownDate() + "</td></tr>\n");
        sb.append("<tr><td>TOTALGROSSPROFIT</td><td>" + getGrossProfitTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALGROSSLOSS</td><td>" + getGrossLossTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALNETPROFIT</td><td>" + getNetProfitTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALPROFITFACTOR</td><td>" + getProfitFactorTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALSHARPERATIO</td><td>" + getTradeSharpRatioTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALVARIANCE</td><td>" + getTradeVarianceTotal() + "</td></tr>\n");
        sb.append("<tr><td>TOTALSSTDDEV</td><td>" + getTradeStdDevTotal() + "</td></tr>\n");

        sb.append("<tr><td>LONGAVGWL</td><td>" + getTradeMeanLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGMAXWIN</td><td>" + getTradeMaxWinLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGMAXWINDATE</td><td>" + getTradeMaxWinDateLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGMAXLOSS</td><td>" + getTradeMaxLossLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGMAXLOSSDATE</td><td>" + getTradeMaxLossDateLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGNTRADES</td><td>" + numberTradesLong + "</td></tr>\n");
        sb.append("<tr><td>LONGNWINTRADES</td><td>" + numberWinTradesLong + "</td></tr>\n");
        sb.append("<tr><td>LONGNLOSSTRADES</td><td>" + numberLossTradesLong + "</td></tr>\n");
        sb.append("<tr><td>LONGPERCPROFITABLE</td><td>" + getTradesPercentProfitableLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGNCONWINTRADES</td><td>" + getNMaxConsecWinTradesLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGNCONLOSSTRADES</td><td>" + getNMaxConsecLossTradesLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGMEANWINTRADE</td><td>" + getTradeMeanWinLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGAVGLOSSTRADE</td><td>" + getTradeMeanLossLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGGROSSPROFIT</td><td>" + getGrossProfitLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGGROSSLOSS</td><td>" + getGrossLossLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGNETPROFIT</td><td>" + getNetProfitLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGPROFITFACTOR</td><td>" + getProfitFactorLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGSHARPERATIO</td><td>" + getTradeSharpeRatioLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGVARIANCE</td><td>" + getTradeVarianceLong() + "</td></tr>\n");
        sb.append("<tr><td>LONGSTDDEV</td><td>" + getTradeStdDevLong() + "</td></tr>\n");

        sb.append("<tr><td>SHORTAVGWL</td><td>" + getTradeMeanShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTMAXWIN</td><td>" + getTradeMaxWinShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTMAXWINDATE</td><td>" + getTradeMaxWinDateShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTMAXLOSS</td><td>" + getTradeMaxLossShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTMAXLOSSDATE</td><td>" + getTradeMaxLossDateShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTNTRADES</td><td>" + numberTradesShort + "</td></tr>\n");
        sb.append("<tr><td>SHORTNWINTRADES</td><td>" + numberWinTradesShort + "</td></tr>\n");
        sb.append("<tr><td>SHORTNLOSSTRADES</td><td>" + numberLossTradesShort + "</td></tr>\n");
        sb.append("<tr><td>SHORTPERCPROFITABLE</td><td>" + getTradesPercentProfitableShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTNCONWINTRADES</td><td>" + getNMaxConsecWinTradesShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTNCONLOSSTRADES</td><td>" + getNMaxConsecLossTradesShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTAVGWINTRADE</td><td>" + getTradeMeanWinShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTAVGLOSSTRADE</td><td>" + getTradeMeanLossShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTGROSSPROFIT</td><td>" + getGrossProfitShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTGROSSLOSS</td><td>" + getGrossLossShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTNEPROFIT</td><td>" + getNetProfitShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTPROFITFACTOR</td><td>" + getProfitFactorShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTSHARPERATION</td><td>" + getTradeSharpeRatioShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTVARIANCE</td><td>" + getTradeVarianceShort() + "</td></tr>\n");
        sb.append("<tr><td>SHORTSTDDEV</td><td>" + getTradeStdDevShort() + "</td></tr>\n");
        sb.append("</TABLE>\n");

        return sb.toString();
    }
}
