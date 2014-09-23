package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.TimeSeries
import com.dtmc.finance.finpojo.Instrument

import com.netnumeri.server.finance.utils.DateUtils

public abstract class Indicator extends TimeSeries {

    protected FinConstants indicator;
    protected String name;
    protected TimeSeries series;
    protected TimeSeries series1;
    protected TimeSeries series2;
    protected Instrument instrument;

    private static final double DOUBLE100 = 100.0

    public Indicator() {
    }

    public Indicator(TimeSeries series, String name) {
        super(name);
        this.series = series;
    }

    public Indicator(Instrument instrument, String name) {
        super(name);
        series = instrument.closeSeries()
        this.instrument = instrument;
    }

    protected void copyBackwords(double[] ad) {
        Date date = series.lastDate();
        for (int i = 0; i < ad.length; i++) {
            double value = ad[ad.length - i - 1];
            add(date, value);
            date = series.prevDate(date);
        }
    }

    protected void copyBackwords(List<Double> ad) {
        Date date = series.lastDate();
        for (int i = 0; i < ad.size(); i++) {
            double value = ad.get(ad.size() - i - 1)
            add(date, value)
            date = series.prevDate(date)
        }
    }

    protected void copyInTheFuture(double[] ad) {
        Date date = series.lastDate();
        for (int i = 0; i < ad.length; i++) {
            double value = ad[i];
            date = DateUtils.addDays(date, 1);
            add(date, value);
        }
    }


    public static void main(String[] args) {

//        String[] tests = {
//            "lowercase",        // [lowercase]
//            "Class",            // [Class]
//            "MyClass",          // [My Class]
//            "HTML",             // [HTML]
//            "PDFLoader",        // [PDF Loader]
//            "AString",          // [A String]
//            "SimpleXMLParser",  // [Simple XML Parser]
//            "GL11Version",      // [GL 11 Version]
//            "99Bottles",        // [99 Bottles]
//            "May5",             // [May 5]
//            "BFG9000",          // [BFG 9000]
//        };

        System.out.println("[" + splitCamelCase("SimpleXMLParser") + "]");
    }
}


