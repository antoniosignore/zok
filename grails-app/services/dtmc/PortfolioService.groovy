package dtmc

import com.dtmc.finance.finpojo.*
import com.dtmc.finance.finpojo.asset.Asset
import com.dtmc.finance.finpojo.derivative.Derivative
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.beans.TradeEnum
import com.netnumeri.server.finance.data.TransactionSeries
import com.netnumeri.server.finance.matrix.Matrix
import com.netnumeri.server.finance.utils.DateUtils


class PortfolioService {

    List<Portfolio> listPortfolio() {
        return Portfolio.list()
    }

    void clear(Portfolio portfolio) {
        portfolio.items.clear();
        portfolio.trades.clear();
    }

    boolean isEmpty(Portfolio portfolio) {
        if (portfolio.items.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Date firstDay(Portfolio portfolio) {
        if (portfolio.firstDate != null) {
            return portfolio.firstDate;
        }
        Instrument instrument
        portfolio.items.each {
            instrument = it.instrument
            if (instrument instanceof Asset) {
                if (instrument.firstDay() != null) {
                    portfolio.firstDate =
                            DateUtils.max(portfolio.firstDate, instrument.firstDay());
                }
            }
        }
        return portfolio.firstDate;
    }

    public Date latestDay(Portfolio portfolio) {
        Instrument instrument;
        if (portfolio.lastDate != null) {
            return portfolio.lastDate;
        }

        portfolio.items.each {
            instrument = it.instrument
            if (instrument.lastDay() != null) {
                portfolio.lastDate = DateUtils.min(portfolio.lastDate,
                        instrument.lastDay());
            }
        }
        return portfolio.lastDate;
    }

    public void add(Portfolio portfolio, Entry item) {

        if (entry(portfolio, item.instrument) != null) {
            System.out.println("addEntry. Instrument: " + item.instrument.name + " already exists in portfolio " + portfolio.getName());
            return;
        }

        if (item.instrument instanceof Asset)
            portfolio.setRangeBounds(item.instrument.lowerRangeDate, item.instrument.getUpperRangeDate());

        portfolio.addToItems(item);
    }


    public void add(Portfolio portfolio, Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        add(portfolio, instrument, 0);
    }

    public void add(Portfolio portfolio, Instrument instrument, int Amount) {
        Entry item = new Entry(instrument, Amount, portfolio);

        add(portfolio, item);
    }

    public void add(Portfolio portfolio,Trade transaction) {

        if (transaction == null) throw new IllegalArgumentException("trades cannot be null");

        if (transaction.portfolio.id) transaction.save(flush: true)

        Instrument instrument = transaction.instrument;
        Entry entry = entry(transaction.portfolio, instrument);

        if (entry == null) {
            entry = new Entry(instrument, transaction.portfolio);
            if (transaction.tradeAction == TradeEnum.BUY) {
                entry.setAmount(transaction.amount);
            } else if (transaction.tradeAction == TradeEnum.SELL) {
                System.out.println("addTransaction. No long position on sell for " + transaction.instrument.name + " in " + transaction.portfolio.getName());
                return;
            } else if (transaction.tradeAction == TradeEnum.SELLSHORT) {
                entry.setAmount(-transaction.amount);
            } else if (transaction.tradeAction == TradeEnum.BUYSHORT) {
                System.out.println("addTransaction. No short position on buy short for " + transaction.instrument.name + " in " + transaction.portfolio.getName());
                return;
            }
            transaction.portfolio.addToTrades(transaction);
            add(transaction.portfolio, entry);
        } else {
            int amount = 0;
            if (transaction.tradeAction == TradeEnum.BUY) {
                if (entry.amount < 0) {
                    System.out.println("addTransaction. Short position on buy for " + transaction.instrument.name + " in " + transaction.portfolio.getName());
                    return;
                }
                amount = entry.amount + transaction.amount;
            } else if (transaction.tradeAction == TradeEnum.SELL) {
                amount = entry.amount - transaction.amount;
                if (amount < 0) {
                    System.out.println("addTransaction. Sell amount larger than long position for" + transaction.instrument.name + " in " + transaction.portfolio.getName());
                    return;
                }
            } else if (transaction.tradeAction == TradeEnum.SELLSHORT) {
                if (entry.amount > 0) {
                    System.out.println("addTransaction. Long position in instrument on sell short: " + transaction.portfolio.getName());
                    return;
                }
                amount = entry.amount - transaction.amount;
            } else if (transaction.tradeAction == TradeEnum.BUYSHORT) {
                if (entry.amount > 0) {
                    System.out.println("addTransaction. Long position on buy short for " + transaction.instrument.name + " in " + portfolio.getName());
                    return;
                }
                amount = entry.amount + transaction.amount;
                if (amount > 0) {
                    System.out.println("addTransaction. Buy short amount larger than short position: " + transaction.portfolio.getName());
                    return;
                }
            }
            transaction.portfolio.addToTrades(transaction);

            if (amount == 0) {
                remove(transaction.portfolio, instrument);
            } else {
                entry.setAmount(amount);
            }
        }
    }

    // add series of trade transactions
    public void add(Portfolio portfolio, TransactionSeries series) {
        if (series == null) throw new IllegalArgumentException("series cannot be null");
        int N = series.getN();
        for (int i = 0; i < N; i++) {
            add(portfolio, series.getTransaction(i));
        }
    }

    // Return pointer to portfolio entry holding instrument
    // Return null if there is no such entry in portfolio
    public Entry entry(Portfolio portfolio, Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        Entry entry;
        if (portfolio.items != null)

            portfolio.items.each {
                entry = it
                if (entry.instrument.id == instrument.id) {
                    return entry;
                }
            }

        return null;
    }

    public Entry entryByName(Portfolio portfolio, String name) {
        Entry entry;
        portfolio.items.each {
            entry = it

            println "entry.instrument.name = $entry.instrument.name"
            println "name = $name"

            if (entry.instrument.name.equalsIgnoreCase(name)) {

                println "returning = " + entry.instrument.name

                return entry;
            }
        }
        return null;
    }

    public void invest(Portfolio portfolio, double wealth) {
        invest(portfolio, wealth, null);
    }

    // Invest wealth into portfolio according to current portfolio weights
    public void invest(Portfolio portfolio, double wealth, Date date) {

        if (portfolio.items == null || portfolio.items.isEmpty())
            throw new RuntimeException("no instruments to invest money into");

        portfolio.items.eachWithIndex { item, count ->
            Instrument asset = item.instrument
            double price = 0;
            if (asset.isDataAvailable(date)) {
                price = asset.getLast();
            } else {
                price = YahooUtils.getLastTradedValue(asset.name)
            }
            setAmount(portfolio, count, (int) (getItemAmount(item) + wealth * getWeight(portfolio, item) / price));
        }
    }

    public Trade buy(Portfolio portfolio, Instrument instrument, int amount) {
        return buy(portfolio, instrument, amount, null);
    }

    public Trade buy(Portfolio portfolio, Instrument instrument, int amount, Date date) {
        if (date == null) date = new Date();
        Trade transaction = new Trade(instrument, TradeEnum.BUY, amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public Trade sell(Portfolio portfolio, Instrument instrument, int amount) {
        return sell(portfolio, instrument, amount, new Date());
    }

    public Trade sell(Portfolio portfolio, Instrument instrument, int amount, Date date) {
        if (date == null) date = new Date();
        Trade transaction = new Trade(instrument, TradeEnum.SELL, amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public Trade sellShort(Portfolio portfolio, Instrument instrument, int amount) {
        return sellShort(portfolio, instrument, amount, null);
    }

    public Trade sellShort(Portfolio portfolio, Instrument instrument, int Amount, Date date) {
        if (date == null) date = new Date();
        Trade transaction = new Trade(instrument, TradeEnum.SELLSHORT, Amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public Trade buyShort(Portfolio portfolio, Instrument instrument, int Amount) {
        return buyShort(portfolio, instrument, Amount, new Date());
    }

    // Buy short
    public Trade buyShort(Portfolio portfolio, Instrument instrument, int Amount, Date date) {
        if (date == null) date = new Date();
        Trade transaction = new Trade(instrument, TradeEnum.BUYSHORT, Amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public Trade sell(Portfolio portfolio, Instrument instrument) {
        return sell(portfolio, instrument, new Date());
    }

    // Sell everything - todo
    public Trade sell(Portfolio portfolio, Instrument instrument, Date date) {
        int amount;
        Entry entry = entry(portfolio, instrument)
        if (entry != null) {
            amount = entry.amount
        } else {
            return null;
        }
        if (date == null) date = new Date();
        Trade transaction = new Trade(instrument, TradeEnum.SELL, amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    // delete instrument from portfolio
    public void remove(Portfolio portfolio, Instrument instrument) {

        portfolio.items.each {
            Entry entry = it
            if (entry.instrument == null) throw new RuntimeException("entry cannot have null name");
            if (entry.instrument.id == instrument.id) {
                portfolio.items.remove(entry);
//                normalizeWeights(portfolio);
            }
        }
    }

    // Return weight of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio
    public double getWeight(Portfolio portfolio, Instrument instrument) {
        Entry entry = entry(portfolio, instrument);
        if (entry != null) {
            return entry.amount;
        } else {
            return 0;
        }
    }

    // Return position of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public FinConstants position(Portfolio portfolio, Instrument instrument) {
        Entry entry = entry(portfolio, instrument);
        if (entry != null) {
            return entry.position()
        } else {
            return 0;
        }
    }

    // Return amount of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio
    public int amount(Portfolio portfolio, Instrument instrument) {
        Entry entry = entry(portfolio, instrument);
        if (entry != null) {
            return entry.amount;
        } else {
            return 0;
        }
    }


    public double amount(Portfolio portfolio) {
        // Return amount of all items in portfolio
        int Amount = 0;

        portfolio.items.each {
            Amount += it.amount
        }

        return Amount;
    }

//    public double wealth(Portfolio portfolio, int i) {
//        return wealth(portfolio, i, null);
//    }

    // Return wealth for i-th asset in portfolio
    public  double wealth(Portfolio portfolio, Instrument asset, Date date) {
        double price = 0;
        if (asset.isDataAvailable(date)) {
            price = asset.premium();
        } else {
            price = YahooUtils.getLastTradedValue(asset.name)
        }

        println "asset.name = $asset.name"

        Entry portfolioEntry = entryByName(portfolio, asset.name)

        println "************** portfolioEntry.amount = " + portfolioEntry.amount

        return price * portfolioEntry.amount;
    }


    public  double wealth(Portfolio portfolio) {
        return wealth(portfolio, null);
    }

    // Return wealth of portfolio
    public  double wealth(Portfolio portfolio, Date date) {
        double Wealth = 0;

        portfolio.items.each {
            Wealth += wealth(portfolio, it.instrument, date);

        }

        return Wealth;
    }

    // Normalize weigts of all stock in portfolio to keep
    // weight sum equal to unity and satisfy boundary conditions
    // Note that we exclude stock with zero weights from the portfolio,
    // meaning that such stock will have zero weight after normalization
//    public void normalizeWeights(Portfolio portfolio) {
//        double WeightSum = 0;
//        int i = 0;
//        for (i = 0; i < portfolio.items.size(); i++) {
//            WeightSum += getWeight(portfolio, i);
//        }
//        for (i = 0; i < portfolio.items.size(); i++) {
//            setWeight(portfolio, i, getWeight(portfolio, i) / WeightSum);
//        }
//        WeightSum = 1;
//        boolean InBounds = true;
//        for (i = 0; i < portfolio.items.size(); i++) {
//            if (getWeight(portfolio, i) != 0) {
//                if (getWeight(portfolio, i) < lowerBound(portfolio, i)) {
//                    InBounds = false;
//                    break;
//                }
//            }
//        }
//        if (!InBounds) {
//            double LowerBoundSum = 0;
//            for (i = 0; i < portfolio.items.size(); i++) {
//                if (getWeight(portfolio, i) != 0) {
//                    LowerBoundSum += lowerBound(portfolio, i);
//                }
//            }
//            for (i = 0; i < portfolio.items.size(); i++) {
//                if (getWeight(portfolio, i) != 0) {
//                    setWeight(portfolio, i, lowerBound(portfolio, i) + getWeight(portfolio, i) * (1 - LowerBoundSum) / WeightSum);
//                }
//            }
//        }
//    }

//    public void normalize(Portfolio portfolio, int Option) {
//        if (Option == FinConstants.kFixedWeight) {
//            normalizeWeights(portfolio);
//        } else {
//            double Wealth = getWealth(portfolio);
//            for (int i = 0; i < portfolio.items.size(); i++) {
//                setWeight(portfolio, i, wealth(portfolio, i) / Wealth);
//            }
//        }
//    }

//    // Check boundary conditions. Return true if feasible
//    public boolean checkBounds(Portfolio portfolio) {
//        double lowsum = 0;
//        double upsum = 0;
//        for (int i = 0; i < portfolio.items.size(); i++) {
//            lowsum += lowerBound(portfolio, i);
//            upsum += upperBound(portfolio, i);
//            if (lowerBound(portfolio, i) >= upperBound(portfolio, i)) {
//                System.out.println("CheckBounds LowerBound >= UpperBound for parameter " + i);
//                return false;
//            }
//        }
//        if (lowsum >= 1) {
//            System.out.println("CheckBounds LowerBoundSum >= 1");
//            return false;
//        }
//        if (upsum <= 1) {
//            System.out.println("CheckBounds UpperBoundSum <= 1");
//            return false;
//        }
//        return true;
//    }

    // Calculate models premium of portfolio

    public double getModelPrice(Portfolio portfolio, String Model, String printMode) {
        double price = 0;
        portfolio.items.each {
            price += it.instrument.premium() * it.amount * it.position();
        }
        return price;
    }

    // Return marked to market portfolio premium
    // If we consider a portfolio as one
    // financial instrument, its premium is
    // equal to its value
    public double price(Portfolio portfolio, int Entry) {
        return m2m(portfolio, Entry);
    }

    // Return marked to market portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value
    public double price(Portfolio portfolio, Date date) {
        return m2m(portfolio, date);
    }

    public double price(Portfolio portfolio) {
        return m2m(portfolio, new Date());
    }

    // Return portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value
    public double premium(Portfolio portfolio) {
        return m2m(portfolio);
    }

    // Return marked to market portfolio value
//    public double m2m(Portfolio portfolio, int index) {
//        Instrument instrument;
//        Daily daily;
//        int amount;
//        double value = 0;
//        portfolio.items.each {
//            amount = it.amount
//            instrument = it.instrument
//            daily = instrument.getDaily();
//            if (!daily.valid()) {
//                daily = instrument.getPrevDaily(index);
//            }
//            if (daily != null) {
//                value += daily.getCloseprice() * amount;
//            } else {
//                System.out.println("getName. Out of data range :" + index);
//                return 0;
//            }
//        }
//        return value;
//    }

    /**
     * Mark 2 Market portfolio value
     *
     * @param date
     */
    public double m2m(Portfolio portfolio, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        Instrument instrument;
        Daily daily;
        int amount;
        double value = 0;
        portfolio.items.each {
            amount = it.amount
            instrument = it.instrument
            if (instrument instanceof Asset) {
                daily = instrument.getDaily(date);
                if (daily == null) {
                    daily = instrument.getPrevDaily(date);
                }
                if (daily != null) {
                    value += daily.closeprice * amount;
                } else {
                    throw new IllegalArgumentException("date "+ date.toString()+ "not valid");
                }
            }
            if (instrument instanceof Derivative) {
                value += instrument.premium() * amount;
            }
        }
        return value;
    }

    // Return portfolio value
    public double m2m(Portfolio portfolio) {
        double Value = 0;
        portfolio.items.each {
            Value += it.value()
        }
        return Value;
    }

    public double getReturn(Portfolio portfolio, Date date) {
        // getAndRemove marked to market daily return
        double price = price(portfolio, date);
        double previousPrice;
        if (price == 0) {
            return 1;
        }
        Date previousDate = getInstrument(portfolio, 0).prevDate(date);
        if (previousDate == null) {
            return 1;
        } else {
            previousPrice = price(portfolio, previousDate);
            if (previousPrice == 0) {
                return 1;
            } else {
                return price / previousPrice;
            }
        }
    }

    public double getLogReturn(Portfolio portfolio, Date date) {
        return Math.log(getReturn(portfolio, date));
    }

    public TimeSeries logReturnSeries(Portfolio portfolio) {
        TimeSeries logReturnSeries = new TimeSeries();
        logReturnSeries.setOption(FinConstants.LOGRETURN);
        Date firstDate = getInstrument(portfolio, 0).firstDate();
        Date lastDate = getInstrument(portfolio, 0).lastDate();
        for (Date date = firstDate;
             DateUtils.isLessEqual(date, lastDate);
             date = DateUtils.nextDay(date)) {
            logReturnSeries.add(date, getLogReturn(portfolio, date));
        }
        return logReturnSeries;
    }

    public double minReturn(Portfolio portfolio) {
        // Return min return
        double MinReturn = getInstrument(portfolio, 0).expectedReturn();
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio, i).expectedReturn() < MinReturn) {
                MinReturn = getInstrument(portfolio, i).expectedReturn();
            }
        }

        return MinReturn;
    }

    public double maxReturn(Portfolio portfolio) {
        // Return max return

        double MaxReturn = getInstrument(portfolio, 0).expectedReturn();
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio, i).expectedReturn() > MaxReturn) {
                MaxReturn = getInstrument(portfolio, i).expectedReturn();
            }
        }

        return MaxReturn;
    }

    public double minVariance(Portfolio portfolio) {
        // Return min variance
        double MinVariance = getInstrument(portfolio, 0).getVariance(FinConstants.LOGRETURN);
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio, i).getVariance(FinConstants.LOGRETURN) < MinVariance) {
                MinVariance = getInstrument(portfolio, i).getVariance(FinConstants.LOGRETURN);
            }
        }
        return MinVariance;
    }

    public double maxVariance(Portfolio portfolio) {
        // Return max variance
        double MaxVariance = getInstrument(portfolio, 0).getVariance(FinConstants.LOGRETURN);
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio, i).getVariance(FinConstants.LOGRETURN) > MaxVariance) {
                MaxVariance = getInstrument(portfolio, i).getVariance(FinConstants.LOGRETURN);
            }
        }
        return MaxVariance;
    }

    public double expectedReturn(Portfolio portfolio) {
        // Calculate portfolio expected return
        double expectedReturn = 0;
        double weight = 0;
        portfolio.items.each {
            weight = it.amount
            if (weight != 0) {
                expectedReturn += it.instrument.expectedReturn() * weight;
            }
        }
        return expectedReturn;
    }

    public void setNHoldAsset(Portfolio portfolio, int NHoldAsset) {
        portfolio.assetsToHold = NHoldAsset;
    }

    public void setInstrument(Portfolio portfolio, Entry entry, Instrument instrument) {
        entry.setInstrument(instrument);
    }

    public void setAmount(Portfolio portfolio, Entry entry, int amount) {
        entry.setAmount(amount);
    }


    public void setWealth(Portfolio portfolio, double Wealth) {
        portfolio.wealth = Wealth;
    }

    public double variance(Portfolio portfolio) {
        // Calculate portfolio variance
        Matrix matrix = covarianceMatrix(portfolio);
        double Variance = 0;
        double Weight1 = 0;
        double Weight2 = 0;

        int i1 = 0
        int i2 = 0

        portfolio.items.each {
            Weight1 = it.amount
            if (Weight1 != 0) {
                portfolio.items.each { item ->
                    Weight2 = item.amount
                    if (Weight2 != 0) {
                        Variance += matrix.get(i1, i2) * Weight1 * Weight2;
                    }
                    i2++
                }
            }
            i1++
        }
        return Variance;
    }

    // Calculate portfolio standard deviation

    public double standardDeviation(Portfolio portfolio) {
        return Math.sqrt(variance(portfolio));
    }

    // Return annual expected return

    public double annualExpectedReturn(Portfolio portfolio) {
        return (Math.pow(expectedReturn(portfolio), 365) - 1);
    }

    public double annualVariance(Portfolio portfolio) {
        // Calculate annual variance
        return variance(portfolio) * 365.0;
    }

    public double annualStandardDeviation(Portfolio portfolio) {
        return standardDeviation(portfolio) * Math.sqrt(365.0);
    }

    // Calculate CAPM beta with Benchmark representing market portfolio
    public double getBeta(Portfolio portfolio, Portfolio index) {
        double Beta = 0;
        portfolio.items.each {
            Portfolio p = (Portfolio) it.instrument
            double beta = getBeta(p, index);
            Beta += beta * getWeight(portfolio, it.instrument);
        }
        return Beta;
    }

    // Claculate CAPM expected return excess with Benchmark representing market portfolio
    public double excess(Portfolio portfolio, Portfolio index, double InterestRate) {
        return InterestRate + getBeta(portfolio, index) * (index.annualExpectedReturn() - InterestRate);
    }

    public double getSharpeIndex(Portfolio portfolio, double rate) {
        return (annualExpectedReturn(portfolio) - rate) / annualStandardDeviation(portfolio);
    }

    public double getTreynorIndex(Portfolio portfolio, Portfolio index, double rate) {
        return (annualExpectedReturn(portfolio) - rate) / getBeta(portfolio, index);
    }

    public double getDelta(Portfolio portfolio) {
        portfolio.items.each {
            portfolio.delta += it.instrument.getDelta() * it.amount;
        }
        return portfolio.delta;
    }

    /**
     * Calculate CAPM beta with Benchmark representing market portfolio
     */
    public double getBeta(Portfolio portfolio, Asset index) {
        return getCovariance(portfolio, index) / index.variance();
    }

    private double modelPrice(Portfolio portfolio) {
        return 0;
    }

    public Matrix covarianceMatrix(Portfolio portfolio) {
        portfolio.covarianceMatrix = new Matrix(portfolio.items.size(), portfolio.items.size());

        int i1 = 0
        int i2 = 0
        portfolio.items.each { it1 ->
            portfolio.items.each { it2 ->
                portfolio.covarianceMatrix.set(i1, i2, it1.instrument.getCovariance(it2.instrument, FinConstants.LOGRETURN));
                portfolio.covarianceMatrix.set(i2, i1, portfolio.covarianceMatrix.get(i1, i2));
                i2++
            }
            i1++
        }

//        for (int i1 = 0; i1 < portfolio.items.size(); i1++) {
//            for (int i2 = i1; i2 < portfolio.items.size(); i2++) {
//                portfolio.covarianceMatrix.set(i1, i2, getInstrument(portfolio, i1).getCovariance(getInstrument(portfolio, i2), FinConstants.LOGRETURN));
//                portfolio.covarianceMatrix.set(i2, i1, portfolio.covarianceMatrix.get(i1, i2));
//            }
//        }
        return portfolio.covarianceMatrix;
    }

    // Build correlation matrix
    public Matrix correlationMatrix(Portfolio portfolio) {
        portfolio.correlationMatrix = new Matrix(portfolio.items.size(), portfolio.items.size());
        portfolio.covarianceMatrix = covarianceMatrix(portfolio);

        int i1 = 0
        int i2 = 0
        portfolio.items.each { it1 ->
            portfolio.items.each { it2 ->
                portfolio.correlationMatrix.set(i1, i2, portfolio.covarianceMatrix.get(i1, i2) /
                        (it1.instrument.getStandardDeviation(FinConstants.LOGRETURN) *
                                it2.instrument.getStandardDeviation(FinConstants.LOGRETURN)));
                portfolio.correlationMatrix.set(i2, i1, portfolio.correlationMatrix.get(i1, i2));
                i2++
            }
            i1++
        }
//        for (int i1 = 0; i1 < portfolio.items.size(); i1++) {
//            for (int i2 = i1; i2 < portfolio.items.size(); i2++) {
//                portfolio.correlationMatrix.set(i1, i2, portfolio.covarianceMatrix.get(i1, i2) /
//                        (getInstrument(portfolio, i1).getStandardDeviation(FinConstants.LOGRETURN) *
//                                getInstrument(portfolio, i2).getStandardDeviation(FinConstants.LOGRETURN)));
//                portfolio.correlationMatrix.set(i2, i1, portfolio.correlationMatrix.get(i1, i2));
//            }
//        }
        return portfolio.correlationMatrix;
    }

    public void onNewTrade(Portfolio portfolio) {
        System.out.println("onNewTrade");
        System.out.println("Price: " + premium(portfolio));
    }

    public int nTransactions(Portfolio portfolio) {
        return portfolio.trades.size();
    }

//    public Trade getTransaction(Portfolio portfolio, int i) {
//        return portfolio.trades.get(i);
//    }

//    public PortfolioItem item(Portfolio portfolio, int i) {
//        return portfolio.items.get(i);
//    }

    public int nentries(Portfolio portfolio) {
        return portfolio.items.size();
    }

    public int nHoldAsset(Portfolio portfolio) {
        return portfolio.assetsToHold;
    }

//    public Instrument getInstrument(Portfolio portfolio, int i) {
//        return portfolio.items.get(i).instrument
//    }
//
//    public FinConstants getPosition(Portfolio portfolio, int i) {
//        return item(portfolio, i).position();
//    }
//
    public int getItemAmount(Entry item) {
        return item.amount;
    }
//
//    public double getModelPrice(Portfolio portfolio, int i) {
//        return item(portfolio, i).price()
//    }

    public double getCovariance(Portfolio portfolio, int Row, int Col) {
        return portfolio.covarianceMatrix.get(Row, Col);
    }

    public List<Entry> getInstruments(Portfolio portfolio) {
        return portfolio.items;
    }

    public Matrix toMatrixLogReturns(Portfolio portfolio) {
        int dimension = getInstruments(portfolio).size();
        Matrix ret = null;
        List entries = getInstruments(portfolio);
        for (int i = 0; i < entries.size(); i++) {
            Entry item = (Entry) entries.get(i);
            Instrument instrument = item.getInstrument();
            double[] series = instrument.logReturnSeries().convertToArray();
            if (ret == null) {
                ret = new Matrix(dimension, series.length);
            }
            for (int x = 0; x < series.length; x++) {
                double v = series[x];
                ret.set(i, x, v);
            }
        }
        return ret;
    }

    public Matrix toMatrixReturns(Portfolio portfolio) {
        int dimension = getInstruments(portfolio).size();
        Matrix ret = null;
        List entries = getInstruments(portfolio);
        for (int i = 0; i < entries.size(); i++) {
            Entry item = (Entry) entries.get(i);
            Instrument instrument = item.getInstrument();
            double[] serie = instrument.returnSeries().convertToArray();
            if (ret == null) {
                ret = new Matrix(dimension, serie.length);
            }
            for (int x = 0; x < serie.length; x++) {
                double v = serie[x];
                ret.set(i, x, v);
            }
        }
        return ret;
    }

}
