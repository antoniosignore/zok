package com.netnumeri.server.utils

import com.dtmc.finance.finpojo.Daily
import com.dtmc.finance.finpojo.asset.*
import com.dtmc.gson.DailyGSON
import com.dtmc.trading.Signal
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.netnumeri.server.finance.beans.GenericTimeSeries
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.beans.TradeEnum
import com.netnumeri.server.finance.strategy.Strategy
import com.netnumeri.server.finance.ta.Indicator
import com.netnumeri.server.finance.ta.TradeInfo
import com.netnumeri.server.finance.ta.TradeListEntry
import com.netnumeri.server.finance.utils.DateUtils
import com.netnumeri.server.finance.utils.YahooUtils
import org.apache.commons.io.FileUtils

import java.text.DecimalFormat
import java.text.SimpleDateFormat

class StockUtils {

    public static final String BEGIN = "&lt;"
    public static final String END = "&gt;"

    public static enclose(String html) {
        return BEGIN + html + END
    }

    public static void refreshData(Stock stock) {
        lastYear(stock)
        refreshChain(stock)
        stock.snapshot = YahooUtils.getCompanySnapshot(stock.name);
    }

    public static void lastYear(Stock stock) {
        YahooUtils.refreshDailyData(stock, DateUtils.todayOneYearAgo(), DateUtils.today());
    }

    public static void refreshDaily(Stock stock, Date from, Date to) {
        YahooUtils.refreshDailyData(stock, from, to);
    }

    public static void refreshChain(Stock stock) {
        stock.chain = YahooOptions.loadOptionChain(stock.name)
    }

    public static void printTimeplotIndicatorOnFile(String dir, Stock stock) {
        Map<String, Indicator> indicators = stock.indicators
        Set<String> keys = indicators.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            Indicator indicator = indicators.get(key)
            FileUtils.writeStringToFile(new File(dir + indicator.name + ".txt"), indicator.getTimeplotSeries())
        }
    }

    static List<Double> toList(double[] doubles) {
        List<Double> list = new ArrayList<Double>()
        for (int i = 0; i < doubles.length; i++) {
            double d = doubles[i];
            list.add(d)
        }
        return list
    }

    public static void printRawIndicatorOnFile(String dir, Stock stock) {
        Map<String, Indicator> indicators = stock.indicators
        Set<String> keys = indicators.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            Indicator indicator = indicators.get(key)
            FileUtils.writeStringToFile(new File(dir + indicator.name + ".raw"), indicator.getNoDateSeries())
        }
    }

    public static void printArrayOnFile(double[] series, String name) {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < series.length; i++) {
            double val = series[i];
            sb.append("[" + i + ", " + val + "],")
        }
        sb.append("]")
        FileUtils.writeStringToFile(new File(name + ".txt"), sb.toString())
    }

//    public static void printTransactionsOnFile(GenericTimeSeries<Trade> transactions, String dir, String filename) {
//        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<data>");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
//        Set<Date> set = transactions.map().keySet()
//        for (Iterator it = set.iterator(); it.hasNext();) {
//            Date date = (Date) it.next();
//            Trade transaction = transactions.treeMap.get(date)
//            sb.append("<event start=\"" + sdf.format(transaction.date) + "\" title=\"" + transaction.action.toString() + "\"/>\n");
//        }
//        sb.append("</data>")
//        FileUtils.writeStringToFile(new File(dir + filename), sb.toString())
//    }

    public static void printOnFile(String dir, List<TradeListEntry> list) {
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<data>");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        for (int i = 0; i < list.size(); i++) {
            TradeListEntry tradeEntry = list.get(i);
            List<TradeInfo> tradeInfoList = tradeEntry.tradeInfoList
            for (int j = 0; j < tradeInfoList.size(); j++) {
                TradeInfo tradeInfo = tradeInfoList.get(j);
                sb.append("<event start=\"" + sdf.format(tradeInfo.date) + "\"" +
                        "  end=\"" + sdf.format(tradeInfo.closeDate) +
                        "\" title=\"" + " " + "\">\n");
                sb.append(enclose("table"));
                sb.append(enclose("tr"));
                sb.append(enclose("td") + "Trade Type")
                sb.append(enclose("/td"));
                sb.append(enclose("td") + tradeInfo.tradeType);
                sb.append(enclose("/td"));
                sb.append(enclose("/tr"));

                sb.append(enclose("tr"));
                sb.append(enclose("td") + "Trade Open")
                sb.append(enclose("/td"));
                sb.append(enclose("td") + tradeInfo.tradeOpen);
                sb.append(enclose("/td"));
                sb.append(enclose("/tr"));

                sb.append(enclose("tr"));
                sb.append(enclose("td") + "Trade Close")
                sb.append(enclose("/td"));
                sb.append(enclose("td") + tradeInfo.tradeClose);
                sb.append(enclose("/td"));
                sb.append(enclose("/tr"));

                sb.append(enclose("tr"));
                sb.append(enclose("td") + "Trade Result")
                sb.append(enclose("/td"));
                sb.append(enclose("td") + tradeInfo.tradeResult);
                sb.append(enclose("/td"));
                sb.append(enclose("/tr"));
                sb.append(enclose("/table"));
                sb.append("</event>");
            }
        }
        sb.append("</data>")
        FileUtils.writeStringToFile(new File(dir + "trades.xml"), sb.toString())
    }

    static def printTimeSeriesOnFile(TimeSeries timeSeries, String dir) {
        FileUtils.writeStringToFile(new File(dir + timeSeries.name + ".txt"), timeSeries.getTimeplotSeries())
    }

    static void printArrayOnFile(List<Double> reconstructionList, String name) {

        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < reconstructionList.size(); i++) {
            double val = reconstructionList.get(i);
            sb.append("[" + i + ", " + val + "],")
        }
        sb.append("]")
        FileUtils.writeStringToFile(new File(name + ".txt"), sb.toString())
    }

    static String lastDate(Stock stock) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
        return sdf.format(stock.lastDate())
    }

    static String dateTitle(Stock stock) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyy")
        return sdf.format(stock.lastDate())
    }

    static String candleStickPlot(Stock stock) {
        GenericTimeSeries<Daily> dailyarray = stock.dailyarray;
        Date startDate = dailyarray.firstDate()
        Date last = dailyarray.lastDate()
        Date dateIndex = startDate

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        StringBuffer sb = new StringBuffer("[\n");
        while (dateIndex != null) {
            Daily daily = dailyarray.get(dateIndex)
            if (dateIndex.getTime() == last.getTime())
                sb.append("['" + sdf.format(dateIndex) + "'," + daily.openprice + "," + daily.high + "," + daily.low + "," + daily.closeprice + "]\n")
            else
                sb.append("['" + sdf.format(dateIndex) + "'," + daily.openprice + "," + daily.high + "," + daily.low + "," + daily.closeprice + "],\n")
            dateIndex = dailyarray.nextDate(dateIndex)
        }
        sb.append("];\n")
        return sb.toString()
    }

    static String getJqPlot(Strategy strategy) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        StringBuffer sb = new StringBuffer("[\n");
        strategy.signals.each {
            Signal signal = it
            sb.append("['" + sdf.format(signal.day) + "'," + signal.value + "],\n")
        }
        sb.append("];\n")
        return sb.toString()
    }

    static String getBuySignals(Strategy strategy) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        StringBuffer sb = new StringBuffer("[\n");
        strategy.signals.each {
            Signal signal = it
            if (signal.direction == TradeEnum.BUY)
                sb.append("['" + sdf.format(signal.day) + "'," + signal.value + "],\n")
        }
        sb.append("];\n")
        return sb.toString()
    }

    static String getSellSignals(Strategy strategy) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        StringBuffer sb = new StringBuffer("[\n");
        strategy.signals.each {
            Signal signal = it
            if (signal.direction == TradeEnum.SELL)
                sb.append("['" + sdf.format(signal.day) + "'," + signal.value + "],\n")
        }
        sb.append("];\n")
        return sb.toString()
    }

    public static String getTimeSeriesPlot(Indicator series) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat df = new DecimalFormat("#.####");
        List<DailyGSON> ds = new ArrayList<DailyGSON>();
        try {
            Date d = series.firstDate();
            StringBuffer sb = new StringBuffer("[");
            while (DateUtils.isLessEqual(d, series.lastDate())) {
                Double v = series.getData(d);
                if (v == null) {
                    d = DateUtils.addDays(d, 1)
                    continue
                };
                ds.add(buildDaily(sdf.format(d), df.format(v)));
                if (d.getTime() == series.lastDate().getTime())
                    sb.append("['" + sdf.format(d) + "'," + v + "]\n")
                else
                    sb.append("['" + sdf.format(d) + "'," + v + "],\n")

                d = DateUtils.addDays(d, 1)
            }
            sb.append("];\n")
            return sb.toString()
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Strategy

    static DailyGSON getDailyGSON(Stock stock) {

        TimeSeries series = stock.getCloseSeries()

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.####");

        List<DailyGSON> ds = new ArrayList<DailyGSON>();
        ds.add(buildDaily("2013-01-01", "1"));
        ds.add(buildDaily("2013-01-02", "2"));
        ds.add(buildDaily("2013-01-03", "3"));
        ds.add(buildDaily("2013-01-04", "4"));
        ds.add(buildDaily("2013-01-05", "5"));
        ds.add(buildDaily("2013-01-06", "4"));
        ds.add(buildDaily("2013-01-07", "3"));
        ds.add(buildDaily("2013-01-08", "4"));
        ds.add(buildDaily("2013-01-09", "1.3"));
        ds.add(buildDaily("2013-01-10", "1.5"));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(ds);
        System.out.println("json = " + jsonOutput);
    }

    private static DailyGSON buildDaily(String day, String val) {
        DailyGSON d1 = new DailyGSON();
        d1.period = day;
        d1.setClose(val);
        return d1;
    }

    static String last5values(Indicator indicator) {

        Date date = indicator.lastDate()
        Date date_1 = indicator.prevDate(date)
        Date date_2 = indicator.prevDate(date_1)
        Date date_3 = indicator.prevDate(date_2)
        Date date_4 = indicator.prevDate(date_3)

        return indicator.getData(date) + " " +
                indicator.getData(date_1) + " " +
                indicator.getData(date_2) + " " +
                indicator.getData(date_3) + " " +
                indicator.getData(date_4)
    }
}
