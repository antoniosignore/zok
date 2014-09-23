package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.trading.Signal
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.beans.TradeEnum
import com.netnumeri.server.finance.ssa.Histogram
import com.netnumeri.server.finance.ssa.HistogramStat
import com.netnumeri.server.finance.ta.BollingerBandDiffIndicator
import com.netnumeri.server.finance.ta.Indicator
import com.netnumeri.server.finance.ta.NormalizedSeriesIndicator
import com.netnumeri.server.finance.ta.SSAComponentsIndicator
import com.netnumeri.server.finance.utils.DateUtils
import com.netnumeri.server.utils.StockUtils

import java.text.SimpleDateFormat

public class SSASignal extends Strategy {

    public static final int WINDOW = 50
    Instrument asset
    TradeEnum lastTrade
    List<Stock> stocksList = new ArrayList<Stock>()
    Histogram histogram

    public SSASignal(String name, Stock asset, Date firstDate, Date lastDate) {
        super(name, asset, firstDate, lastDate, 0);
        this.asset = asset
        histogram = new HistogramStat(50, -100, 100);
    }

    public void evaluateInstrumentOnDate(Date date) {

        Date da = DateUtils.dateNMonthsAgo(date, 3);
        Date a = date

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd")

        Stock stock = new Stock(asset.name, format.format(date));

        StockUtils.refreshDaily(stock as Stock, da, a);

        TimeSeries closeSeries = stock.buildCloseSeries()
        closeSeries.normalize()

        Indicator normalized = new NormalizedSeriesIndicator(closeSeries, "SSA-01");
        Indicator trend = new SSAComponentsIndicator(closeSeries, "SSA-0", WINDOW, [0])
        Indicator comp1 = new SSAComponentsIndicator(closeSeries, "SSA-1", WINDOW, [1])
        Indicator comp12 = new SSAComponentsIndicator(closeSeries, "SSA-12", WINDOW, [1, 2])
//        Indicator bbu = new BollingerBandUpIndicator(closeSeries, "BB-Upper", 10, 2)
//        Indicator bbl =new BollingerBandLowerIndicator(closeSeries, "BB-Lower", 10, 2)
        Indicator bbdiff = new BollingerBandDiffIndicator(closeSeries, "BB-Diff", 10, 2)
//        Indicator ssa1Predict = new SSASeriesPredictionIndicator(closeSeries, "SSA-0-predict", WINDOW, 3, [0, 1, 2], 10);


        stock.indicators.put("normalized", normalized)
        stock.indicators.put("trend", trend)
        stock.indicators.put("comp1", comp1)
        stock.indicators.put("comp12", comp12)
//        stock.indicators.put("bbl", bbl)
//        stock.indicators.put("bbu", bbu)
        stock.indicators.put("bbdiff", bbdiff)
//        stock.indicators.put("predict", ssa1Predict)

        if (!(DateUtils.isGreater(date, trend.firstDate()) && DateUtils.isGreater(date, comp1.firstDate())))
            return

        Date previousDate
        Date prevPreviousDate

        try {
            previousDate = comp1.prevDate(date)
            prevPreviousDate = comp1.prevDate(previousDate)
        } catch (Throwable th) {
            return
        }

        if (previousDate == null || prevPreviousDate == null)
            return

        Double todaySSA0 = trend.getData(date)
        Double yesterdaySSA0 = trend.getData(previousDate)

        Double todaySSA1 = comp1.getData(date)
        Double yesterdaySSA1 = comp1.getData(previousDate)
        Double twoDaysBeforeSSA1 = comp1.getData(prevPreviousDate)

        boolean isATop = twoDaysBeforeSSA1 < yesterdaySSA1 && yesterdaySSA1 > todaySSA1
        boolean isALow = twoDaysBeforeSSA1 > yesterdaySSA1 && yesterdaySSA1 < todaySSA1
        boolean inATrade = (lastTrade == null)
        boolean trendingDown = ((yesterdaySSA0 > todaySSA0))

        if (isATop) this.signals.add(new Signal(date, TradeEnum.SELL, asset, asset.value(date, FinConstants.CLOSE)))
        if (isALow) signals.add(new Signal(date, TradeEnum.BUY, asset, asset.value(date, FinConstants.CLOSE)))

        stocksList.add(stock)

    }


}



