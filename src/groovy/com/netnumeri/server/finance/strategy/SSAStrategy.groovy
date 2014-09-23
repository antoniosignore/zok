package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.Entry
import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Portfolio
import com.dtmc.finance.finpojo.Trade
import com.netnumeri.server.finance.beans.NRError
import com.netnumeri.server.finance.beans.NumericalRecipesSeries
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.beans.TradeEnum
import com.netnumeri.server.finance.data.TransactionSeries
import com.netnumeri.server.finance.ssa.SSAMath
import com.netnumeri.server.finance.ta.*
import com.netnumeri.server.finance.utils.StringTemplate
import com.netnumeri.server.finance.utils.Util

public class SSAStrategy extends Strategy {


    boolean foundABUY = false;

    private boolean fast = false;

    int sma3 = 3;
    int sma14 = 14;
    int sma10 = 10;
    int sma256 = 256;
    int numberOfTradingDays = 5;
    int snapshot = 448;
    int nleft = 10;
    int nright = 10;
    int mode = 4;

    List<String> predictions = new ArrayList<String>();
    List<String> armonics = new ArrayList<String>();


    public SSAStrategy(String name,
                       Portfolio portfolio,
                       Date firstDate,
                       Date lastDate,
                       double wealth) {
        super(name, portfolio, firstDate, lastDate, wealth);
    }


    public void processTradingDay(Instrument instrument,
                                  Date from,
                                  Date today,
                                  double[] serie,
                                  Map params) throws NRError {


        Map signalMap = new HashMap();
        Util.debug("from = " + from);
        Util.debug("today = " + today);

//        instrument.setWindow(from, today);

        TimeSeries closes = instrument.getCloseSeries(from, today);

        Indicator sma1g = new SMAIndicator(closes, "SMA-" + sma3, sma3);

        Util.debug("serie.getSize() = " + serie.length);
        Util.debug("serie.value() = " + serie[serie.length - 1]);

        Util.debug("closes.getFirstData() = " + closes.getFirstData());
        Util.debug("closes.getLastData() = " + closes.getLastData());

//        Indicator vhf = new Indicator(instrument, "VHF-" + sma14, sma14, -1, -1, -1, -1);
//        TS shifted = closes.getShiftedSeries();
//        TS returns = closes.getReturnSeries();
//        TS accumulated = closes.getAccumulatedSeries();
//        TS logreturnlag = closes.getLogReturnSeries();
//        TS logreturnlag2 = closes.getLogReturnSeries(2);
//        Indicator sma2g = new Indicator(closes, "SMA-" + sma14, sma256, -1, -1, -1, -1);
//        Indicator wma1g = new Indicator(closes, "EMA-" + sma256, sma256, -1, -1, -1, -1);
//        Indicator bbup = new Indicator(closes, "BBU", sma10, 2, -1, -1, -1);
//        Indicator bbdw = new Indicator(closes, "BBL", sma10, 2, -1, -1, -1);
//        Util.debug("bbup = " + bbup.lastValidData());
//
//        Indicator pcup = new Indicator(closes, "PCU", sma10, -1, -1, -1, -1);
//        Indicator pcdw = new Indicator(closes, "PCL", sma10, -1, -1, -1, -1);
//
//        Indicator smv = new Indicator(closes, "SMV-" + sma14, sma14, -1, -1, -1, -1);
//        Indicator smd = new Indicator(closes, "SMD-" + sma14, sma14, -1, -1, -1, -1);
//        Indicator mom = new Indicator(closes, "MOM-" + sma14, sma14, -1, -1, -1, -1);
//        Indicator macd = new Indicator(closes, "MACD", 12, 26, -1, -1, -1);
//        Indicator d = new Indicator(macd, "SMA", 9, -1, -1, -1, -1);
//
//        Indicator to = new Indicator(closes, "TO", 4, 20, -1, -1, -1);
//        Indicator roc = new Indicator(closes, "ROC", sma10, -1, -1, -1, -1);
//        Indicator rsi = new Indicator(closes, "RelativeStrengthIndex", sma10, -1, -1, -1, -1);
//        Indicator cci = new Indicator(closes, "CCI", sma10, -1, -1, -1, -1);
//        Indicator osc = new Indicator(closes, "OSC", 4, 20, -1, -1, -1);
//        Indicator k = new Indicator(instrument, "%K", sma10, -1, -1, -1, -1);
//        Indicator s = new Indicator(k, "SMA", sma10, -1, -1, -1, -1);
//        Util.debug("standard deviation:" + closes.getStandardDeviation());
//        Util.debug("portfolio.annualExpectedReturn()" + getPortfolio().annualExpectedReturn());


        HashMap map = (HashMap) params;
        Integer snappar = (Integer) map.get("snapshot");
        double[] detrended;
        double[] prediction = SSAMath.computeForecast(serie, 64);
        double[] predictedserie = Util.concatenateSeries(serie, prediction);

        double currentDay = predictedserie[448];

        double[] monoEigenTrend = getFirstEigen(predictedserie);

        Map trendmaps = Filter.computeArmonics(monoEigenTrend);
        double[] trendD1 = (double[]) trendmaps.get("d1");
        Util.debug("trendD1 = " + trendD1[448]);

/*
        double[] xAxis = Util.vector(1, 10);
        for (int kk = 1; kk <= 10; kk++) {
            xAxis[kk] = kk;
            trend[kk] = monoEigenTrend[437 + kk];
//            trend[k] = k;
        }
        Regression.MedfitResult m = Regression.medfit(xAxis, trend, 10);
        Util.debug("m.a = " + m.a);
        Util.debug("m.b = " + m.b);
        Util.debug("m.abdev = " + m.abdev);
*/

        double[] twoEigenTrend = getDoubleEigenvectorsTrend(predictedserie);

        detrended = Util.diffSeries(predictedserie, monoEigenTrend);

//        Util.toFile("/home/antonio/graphs/prediction" + DateUtils.toYYYY_MM_DD(today) + ".txt", prediction);
//        Util.toFile("/home/antonio/graphs/predictedserie" + DateUtils.toYYYY_MM_DD(today) + ".txt", predictedserie);
//        Util.toFile("/home/antonio/graphs/monoEigenTrend" + DateUtils.toYYYY_MM_DD(today) + ".txt", monoEigenTrend);
//        Util.toFile("/home/antonio/graphs/twoEigenTrend" + DateUtils.toYYYY_MM_DD(today) + ".txt", twoEigenTrend);
//        Util.toFile("/home/antonio/graphs/detrended" + DateUtils.toYYYY_MM_DD(today) + ".txt", detrended);
        //  Util.toFile("/home/antonio/graphs/detrended" + DateUtils.toYYYY_MM_DD(today) + ".txt", detrended);

        Map maps = Filter.computeArmonics(predictedserie);
        double[] smoothedData = (double[]) maps.get("filter");
        double[] smoothedData2N = (double[]) maps.get("doublefilter");
        double[] derivative1 = (double[]) maps.get("d1");
        double[] derivative2 = (double[]) maps.get("d2");

        derivative1[1] = 0;
        derivative1[2] = 0;
        derivative1[3] = 0;
        derivative1[4] = 0;
        derivative1[5] = 0;
        derivative1[6] = 0;
        derivative1[7] = 0;
        derivative1[8] = 0;
        derivative1[9] = 0;
        derivative1[10] = 0;
        derivative1[504] = 0;
        derivative1[505] = 0;
        derivative1[506] = 0;
        derivative1[507] = 0;
        derivative1[508] = 0;
        derivative1[509] = 0;
        derivative1[510] = 0;
        derivative1[511] = 0;
        derivative1[512] = 0;
        derivative1[513] = 0;

        derivative2[1] = 0;
        derivative2[2] = 0;
        derivative2[3] = 0;
        derivative2[4] = 0;
        derivative2[5] = 0;
        derivative2[6] = 0;
        derivative2[7] = 0;
        derivative2[8] = 0;
        derivative2[9] = 0;
        derivative2[10] = 0;
        derivative2[504] = 0;
        derivative2[505] = 0;
        derivative2[506] = 0;
        derivative2[507] = 0;
        derivative2[508] = 0;
        derivative2[509] = 0;
        derivative2[510] = 0;
        derivative2[511] = 0;
        derivative2[512] = 0;

//        Util.toFile("/home/antonio/graphs/smoothedData" + DateUtils.toYYYY_MM_DD(today) + ".txt", smoothedData);
//        Util.toFile("/home/antonio/graphs/smoothedData2N" + DateUtils.toYYYY_MM_DD(today) + ".txt", smoothedData2N);
//        Util.toFile("/home/antonio/graphs/derivative1" + DateUtils.toYYYY_MM_DD(today) + ".txt", derivative1);
//        Util.toFile("/home/antonio/graphs/derivative2" + DateUtils.toYYYY_MM_DD(today) + ".txt", derivative2);

        signalMap.put("smoothedData", smoothedData);
        signalMap.put("smoothedData2N", smoothedData2N);
        signalMap.put("derivative1", derivative1);
        signalMap.put("derivative2", derivative2);

        double[] velmacd = Util.diffSeries((double[]) maps.get("filter"), (double[]) maps.get("doublefilter"));
//        Util.toFile("/home/antonio/graphs/velmacd" + today + ".txt", velmacd);

//        GraphicsUtils.toGIF(graph.getImage(), "/home/antonio/graphs/Prediction-" + label + ".gif");
//        predictions.add("/home/antonio/graphs/Prediction-" + today + ".gif");
//
//        FinGraph detrendgraph = GraphicsUtils.createGraph("detrended" + label, detrended, 600, 200, 1, false,512);
//        GraphicsUtils.toGIF(detrendgraph.getImage(), "/home/antonio/graphs/detrended-" + label + ".gif");

//        armonics.add("/home/antonio/graphs/topchart-" + label + ".gif");

        evaluatePortfolioOnDate(today, signalMap);
    }

    public void init() {
        foundABUY = false;
    }

    public void execute(Map parametersMap) {
        try {
            List<Entry> portfolioItems = getPortfolio().getItems();

            getPortfolio().getItems().each {

                init();

                Entry portfolioItem = it
                Instrument instrument = portfolioItem.getInstrument();

                TimeSeries logserie = instrument.buildlogAverageSeries();
                NumericalRecipesSeries lserie = logserie.asRecipes();
                int size = Util.size(lserie.getData());
                double[] vals = Util.vector(1, snapshot);

                for (int index = size - numberOfTradingDays + 1; index <= size; index++) {
                    Date from = lserie.getDate(index - snapshot + 1);
                    for (int j = 1; j <= snapshot; j++) {
                        double valore = lserie.getValue(index - snapshot + j);
                        vals[j] = valore;
                    }
                    Util.debug("lserie.getDate(index) = " + lserie.getDate(index) + " val:" + lserie.getValue(index));
                    processTradingDay(instrument, from, lserie.getDate(index), vals, parametersMap);
                }

            }


            TransactionSeries tserie = getTransactionSerie();
            Util.debug("tserie.getNTransactions() = " + tserie.getNTransactions());

            BackTest trader = new BackTest(getTransactionSerie(), getPortfolio(), 100000);
            double value = trader.test();
            try {
//                trader.generateCharts();
//                Util.debug(XML.toString(XML.stringToDocument(trader.toXMLString()), true, true));
                trader.getNLossTradesLong();
                Util.debug("trader.getNLossTradesTotal() = " + trader.getNLossTradesTotal());
                Util.debug("trader.getNWinTradesTotal()() = " + trader.getNWinTradesTotal());
                Util.debug("trader.getNLossTradesLong() = " + trader.getNLossTradesLong());
                Util.debug("trader.getNLossTradesShort() = " + trader.getNLossTradesShort());
                Util.debug("tra = " + trader.getAccountWealth());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
        }
    }

    public void evaluatePortfolioOnDate(Date date, Map signals) {

        if (date == null) throw new IllegalArgumentException("date cannot be null");

        for (int i = 0; i < getPortfolio().nentries(); i++) {
            Instrument instrument = getPortfolio().getInstrument(i);
            if (instrument.isDataAvailable(date)) {

                try {

                    double[] smoothedData = (double[]) signals.get("smoothedData");
                    double[] derivative1 = (double[]) signals.get("derivative1");
                    double[] derivative2 = (double[]) signals.get("derivative2");

                    int amount = 100;

                    if (instrument.isDataAvailable(date)) {
                        int signal = getLastValidSignal(derivative1, derivative2);

                        if (signal == TradeEnum.BUY) {
                            Trade transaction = new Trade(instrument, TradeEnum.BUY, amount, instrument.close(date), date);
                            add(transaction);
                            foundABUY = true;
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final int WINDOW = 100
    public static final int i = 0

    public void evaluateInstrumentOnDate(Date date, Instrument stock) {

//        if (i++ < WINDOW) return;
//
//        String dir = "/home/antonio/timeplot/ssa/" + new SimpleDateFormat("yyyyMMdd").format(date) + "/"
//        TimeSeries closes = stock.getCloseSeries(stock.firstDate(), date)
//
//        SSAAnalysis analysis = new SSAAnalysis(closes.convertToList(), WINDOW)
//
////        stock.indicators.put("SMA-" + 256, new SMAIndicator(closes, "SMA-" + 256, 256))
//
//        def components = [0]
//        Indicator ssa0 = new SSAComponentsIndicator(closes, "SSA-0", analysis, components);
//        stock.indicators.put(ssa0.name, ssa0)
//
//        components = [1]
//        Indicator ssa1 = new SSAComponentsIndicator(closes, "SSA-1", analysis, components);
//        stock.indicators.put(ssa1.name, ssa1)
//
////        components = [0,1]
////        Indicator ssa01 = new SSAComponentsIndicator(closes,"SSAStrategy-01", analysis, components);
////        stock.indicators.put(ssa01.name, ssa01)
//
//        components = [2]
//        Indicator ssa2 = new SSAComponentsIndicator(closes, "SSA-2", analysis, components);
//        stock.indicators.put(ssa2.name, ssa2)
//
//        components = [3]
//        Indicator ssa3 = new SSAComponentsIndicator(closes, "SSA-3", analysis, components);
//        stock.indicators.put(ssa3.name, ssa3)
//
//        components = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
//        Indicator ssa05 = new SSAComponentsIndicator(closes, "SSA-05", analysis, components);
//        stock.indicators.put(ssa05.name, ssa05)
//
//        Indicator ssa1Predict = new SSASeriesPredictionIndicator(closes, "SSA-0-predict", WINDOW, 3, components, 10);
//        stock.indicators.put(ssa1Predict.name, ssa1Predict)
//
//        StockUtils.printTimeplotIndicatorOnFile(dir, stock)
//
//        SimpleDateFormat timeplotFormat = new SimpleDateFormat("yyyy-MM-dd")
//
//        String html = getHtml(timeplotFormat.format(date), dir)
//
//        FileUtils.writeStringToFile(new File(dir + "/ssa.html"), html)

    }

    private int getLastValidSignal(double[] derivative1, double[] derivative2) {
        int today = 448;
        for (int i = 0; i < 20; i++) {
            if (derivative1[today - i - 1] < 0 && derivative1[today - i] >= 0) {
                if (derivative2[today - i] > 0)
                    return TradeEnum.BUY;
            }
            if (derivative1[today - i - 1] > 0 && derivative1[today - i] <= 0) {
                if (derivative2[today - i] < 0)
                    return TradeEnum.SELL;
            }
        }
        throw new IllegalStateException("no signal found");
    }

    private String getHtml(String min, String dir) {
        StringTemplate html = new StringTemplate(
                "<html>\n" +
                        "<head>\n" +
                        "    <title>TradeGambler</title>\n" +
                        "    <style>\n" +
                        "\n" +
                        "    </style>\n" +
                        "\n" +
                        "    <script type=\"text/javascript\" src=\"http://api.simile-widgets.org/timeplot/1.1/timeplot-api.js\"></script>\n" +
                        "\n" +
                        "   <script>\n" +
                        "           var timeplot1;\n" +
                        "           var timeplot2;\n" +
                        "           var timeplot3;\n" +
                        "           var timeplot4;\n" +
                        "           var timeplot5;\n" +
                        "\n" +
                        "           var color3 = new Timeplot.Color('#91AA9D');\n" +
                        "           var color5 = new Timeplot.Color('#193441');\n" +
                        "           var red = new Timeplot.Color('#B9121B');\n" +
                        "           var blue = new Timeplot.Color('#193441');\n" +
                        "           var green = new Timeplot.Color('#468966');\n" +
                        "           var lightGreen = new Timeplot.Color('#5C832F');\n" +
                        "           var celeste = new Timeplot.Color('#00AAE4');\n" +
                        "\n" +
                        "           var gridColor  = new Timeplot.Color('#FFE57F');\n" +
                        "\n" +
                        "        function onLoad() {\n" +
                        "\n" +
                        "            var timeGeometry = new Timeplot.DefaultTimeGeometry({\n" +
                        "                gridColor: \"#FFFFFF\",\n" +
                        "                min: \"${min}\",\n" +
                        "                axisLabelsPlacement: \"bottom\"\n" +
                        "            });\n" +
                        "\n" +
                        "            var geometry1 = new Timeplot.DefaultValueGeometry({\n" +
                        "                min: 0\n" +
                        "            });\n" +
                        "\n" +
                        "           var geometry2 = new Timeplot.DefaultValueGeometry({\n" +
                        "               min: 0\n" +
                        "           });\n" +
                        "\n" +
                        "           var geometry3 = new Timeplot.DefaultValueGeometry({\n" +
                        "               min: 0\n" +
                        "           });\n" +
                        "\n" +
                        "           var geometry4 = new Timeplot.DefaultValueGeometry({\n" +
                        "               min: 0\n" +
                        "           });\n" +
                        "\n" +
                        "            var eventSource1 = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSourceStock = new Timeplot.ColumnSource(eventSource1,1);\n" +
                        "\n" +
                        "            var eventSource2 = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource2 = new Timeplot.ColumnSource(eventSource2,1);\n" +
                        "\n" +
                        "            var eventSource1SSA = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource1SSA = new Timeplot.ColumnSource(eventSource1SSA,1);\n" +
                        "\n" +
                        "            var eventSource2SSA = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource2SSA = new Timeplot.ColumnSource(eventSource2SSA,1);\n" +
                        "\n" +
                        "            var eventSource3SSA = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource3SSA = new Timeplot.ColumnSource(eventSource3SSA,1);\n" +
                        "\n" +
                        "            var eventSource4SSA = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource4SSA = new Timeplot.ColumnSource(eventSource4SSA,1);\n" +
                        "\n" +
                        "            var eventSource14SSA = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSource14SSA = new Timeplot.ColumnSource(eventSource14SSA,1);\n" +
                        "\n" +
                        "            var eventSourcePredict = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSourcePredict = new Timeplot.ColumnSource(eventSourcePredict,1);\n" +
                        "\n" +
                        "            var eventSourceOrig = new Timeplot.DefaultEventSource();\n" +
                        "            var dataSourceStockOrig = new Timeplot.ColumnSource(eventSourceOrig,1);\n" +
                        "\n" +
                        "\n" +
                        "            var plotInfo1 = [\n" +
                        "                Timeplot.createPlotInfo({\n" +
                        "                    id: \"stock\",\n" +
                        "                    dataSource: dataSourceStock,\n" +
                        "                    timeGeometry: timeGeometry,\n" +
                        "                    valueGeometry: geometry1,\n" +
                        "                    lineColor: blue,\n" +
                        "                    showValues: true,\n" +
                        "                    roundValues: false,\n" +
                        "                    lineWidth:2\n" +
                        "                }),\n" +
                        "                Timeplot.createPlotInfo({\n" +
                        "                        id: \"Eigen1\",\n" +
                        "                        dataSource: dataSource1SSA,\n" +
                        "                        timeGeometry: timeGeometry,\n" +
                        "                        valueGeometry: geometry1,\n" +
                        "                        lineColor: color3,\n" +
                        "                        showValues: true,\n" +
                        "                        roundValues: false\n" +
                        "                }),\n" +
                        "            ];\n" +
                        "\n" +
                        "           var plotInfo2= [\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"stock2\",\n" +
                        "                   dataSource: dataSourceStock,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry1,\n" +
                        "                   lineColor: blue,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false,\n" +
                        "                   lineWidth:2\n" +
                        "               }),\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"Eigen2\",\n" +
                        "                   dataSource: dataSource2SSA,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry2,\n" +
                        "                   lineColor: color3,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false\n" +
                        "           })\n" +
                        "       ];\n" +
                        "\n" +
                        "           var plotInfo3 = [\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"stock\",\n" +
                        "                   dataSource: dataSourceStock,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry1,\n" +
                        "                   lineColor: blue,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false,\n" +
                        "                   lineWidth:2\n" +
                        "               }),\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"Eigen3\",\n" +
                        "                   dataSource: dataSource3SSA,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry3,\n" +
                        "                   lineColor: color3,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false\n" +
                        "           })\n" +
                        "       ];\n" +
                        "\n" +
                        "           var plotInfo4 = [\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"stock\",\n" +
                        "                   dataSource: dataSourceStock,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry1,\n" +
                        "                   lineColor: blue,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false,\n" +
                        "                   lineWidth:2\n" +
                        "               }),\n" +
                        "               Timeplot.createPlotInfo({\n" +
                        "                   id: \"Eigen4\",\n" +
                        "                   dataSource: dataSource4SSA,\n" +
                        "                   timeGeometry: timeGeometry,\n" +
                        "                   valueGeometry: geometry4,\n" +
                        "                   lineColor: color3,\n" +
                        "                   showValues: true,\n" +
                        "                   roundValues: false\n" +
                        "           })\n" +
                        "       ];\n" +
                        "\n" +
                        "            // stock-orig.txt\n" +
                        "       var plotInfo5 = [\n" +
                        "           Timeplot.createPlotInfo({\n" +
                        "               id: \"stock\",\n" +
                        "               dataSource: dataSourceStock,\n" +
                        "               timeGeometry: timeGeometry,\n" +
                        "               valueGeometry: geometry1,\n" +
                        "               lineColor: blue,\n" +
                        "               showValues: true,\n" +
                        "               roundValues: false,\n" +
                        "               lineWidth:2\n" +
                        "           }),\n" +
                        "           Timeplot.createPlotInfo({\n" +
                        "               id: \"stock-orig\",\n" +
                        "               dataSource: dataSourceStockOrig,\n" +
                        "               timeGeometry: timeGeometry,\n" +
                        "               valueGeometry: geometry1,\n" +
                        "               lineColor: lightGreen,\n" +
                        "               showValues: true,\n" +
                        "               roundValues: false,\n" +
                        "               lineWidth:2\n" +
                        "           }),Timeplot.createPlotInfo({\n" +
                        "               id: \"Eigen14\",\n" +
                        "               dataSource: dataSource14SSA,\n" +
                        "               timeGeometry: timeGeometry,\n" +
                        "               valueGeometry: geometry1,\n" +
                        "               lineColor: color3,\n" +
                        "               showValues: true,\n" +
                        "               roundValues: false\n" +
                        "           }),\n" +
                        "           Timeplot.createPlotInfo({\n" +
                        "               id: \"EigenPredict\",\n" +
                        "               dataSource: dataSourcePredict,\n" +
                        "               timeGeometry: timeGeometry,\n" +
                        "               valueGeometry: geometry1,\n" +
                        "               lineColor: celeste,\n" +
                        "               showValues: true,\n" +
                        "               roundValues: false,\n" +
                        "               lineWidth:2\n" +
                        "           })\n" +
                        "       ];\n" +
                        "\n" +
                        "\n" +
                        "           timeplot1 = Timeplot.create(document.getElementById(\"timeplot1\"), plotInfo1);\n" +
                        "           timeplot1.loadText(\"${dir}/stock.txt\", \" \", eventSource1);\n" +
                        "           timeplot1.loadText(\"${dir}/SSA-0.txt\", \" \", eventSource1SSA);\n" +
                        "            timeplot1.loadText(\"${dir}/SMA-256.txt\", \" \", eventSource2);\n" +
                        "\n" +
                        "           timeplot2 = Timeplot.create(document.getElementById(\"timeplot2\"), plotInfo2);\n" +
                        "           timeplot2.loadText(\"${dir}/stock.txt\", \" \", eventSource1);\n" +
                        "           timeplot2.loadText(\"${dir}/SSA-1.txt\", \" \", eventSource2SSA);\n" +
                        "\n" +
                        "           timeplot3 = Timeplot.create(document.getElementById(\"timeplot3\"), plotInfo3);\n" +
                        "           timeplot3.loadText(\"${dir}/stock.txt\", \" \", eventSource1);\n" +
                        "           timeplot3.loadText(\"${dir}/SSA-2.txt\", \" \", eventSource3SSA);\n" +
                        "\n" +
                        "           timeplot4 = Timeplot.create(document.getElementById(\"timeplot4\"), plotInfo4);\n" +
                        "           timeplot4.loadText(\"${dir}/stock.txt\", \" \", eventSource1);\n" +
                        "           timeplot4.loadText(\"${dir}/SSA-3.txt\", \" \", eventSource4SSA);\n" +
                        "\n" +
                        "           timeplot5 = Timeplot.create(document.getElementById(\"timeplot5\"), plotInfo5);\n" +
                        "//           timeplot5.loadText(\"${dir}/stock.txt\", \" \", eventSource1);\n" +
                        "           timeplot5.loadText(\"${dir}/stock-orig.txt\", \" \", eventSourceOrig);\n" +
                        "           timeplot5.loadText(\"${dir}/SSA-05.txt\", \" \", eventSource14SSA);\n" +
                        "           timeplot5.loadText(\"${dir}/SSA-0-predict.txt\", \" \", eventSourcePredict);\n" +
                        "\n" +
                        "\n" +
                        "        var resizeTimerID = null;\n" +
                        "        function onResize() {\n" +
                        "            if (resizeTimerID == null) {\n" +
                        "                resizeTimerID = window.setTimeout(function() {\n" +
                        "                    resizeTimerID = null;\n" +
                        "                        if (timeplot1) timeplot1.repaint();\n" +
                        "                        if (timeplot2) timeplot2.repaint();\n" +
                        "                        if (timeplot3) timeplot3.repaint();\n" +
                        "                        if (timeplot4) timeplot4.repaint();\n" +
                        "                        if (timeplot5) timeplot5.repaint();\n" +
                        "                }, 0);\n" +
                        "            }\n" +
                        "        }\n" +
                        "        }\n" +
                        "    </script>\n" +
                        "  </head>\n" +
                        "  <body onload=\"onLoad();\" onresize=\"onResize();\">\n" +
                        "\n" +
                        "    <div id=\"content\">\n" +
                        "        <div id=\"timeplot1\" style=\"height: 400px\" class=\"timeplot\"></div>\n" +
                        "        <div id=\"timeplot2\" style=\"height: 400px\" class=\"timeplot\"></div>\n" +
                        "        <div id=\"timeplot3\" style=\"height: 400px\" class=\"timeplot\"></div>\n" +
                        "        <div id=\"timeplot4\" style=\"height: 400px\" class=\"timeplot\"></div>\n" +
                        "        <div id=\"timeplot5\" style=\"height: 400px\" class=\"timeplot\"></div>\n" +
                        "    </div>\n" +
                        "\n" +
                        "  </body>\n" +
                        "</html>"
        )

        html.setBlankNull();

        Map<String, String> m = new HashMap<String, String>();
        m.put("min", min);
        m.put("dir", dir);
        return html.substitute(m);

    }

    @Override
    void evaluateInstrumentOnDate(Date date) {

    }
}