package com.netnumeri.server.finance.utils

import com.dtmc.finance.finpojo.Daily
import com.dtmc.finance.finpojo.Instrument
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.TimeSeries

class IndicatorUtils {

    public static double SMA(TimeSeries series, Date date, int order) {
        if (series == null) throw new IllegalArgumentException("series cannot be null");

        Date dateIndex = date;
        double movingAverage = 0;

        int counter = 0;
        if (series.isEmpty(date)) {
            return 0;
        }

        while (dateIndex != null && counter < order) {
            movingAverage += series.getData(dateIndex);
            dateIndex = series.prevDate(dateIndex);
            counter++;
        }
        return movingAverage / counter;

    }

    public static double SMV(Instrument instrument, Date date, int order, int option) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        double SMA = SMA(instrument, date, order, option);
        double SMV = 0;
        int Count = 0;
        Daily daily = instrument.daily(date);
        while (daily != null && Count < order) {
            switch (option) {
                case FinConstants.HIGH:
                    SMV += (SMA - daily.getHigh()) * (SMA - daily.getHigh());
                    break;
                case FinConstants.LOW:
                    SMV += (SMA - daily.getLow()) * (SMA - daily.getLow());
                    break;
                case FinConstants.CLOSE:
                    SMV += (SMA - daily.getCloseprice()) * (SMA - daily.getCloseprice());
                    break;
                case FinConstants.PRICE:
                    SMV += (SMA - daily.getPrice()) * (SMA - daily.getPrice());
                    break;
                case FinConstants.MEDIANPRICE:
                    SMV += (SMA - daily.getPrice(FinConstants.MEDIANPRICE)) * (SMA - daily.getPrice(FinConstants.MEDIANPRICE));
                    break;
                case FinConstants.AVERAGEPRICE:
                    SMV += (SMA - daily.getPrice(FinConstants.AVERAGEPRICE)) * (SMA - daily.getPrice(FinConstants.AVERAGEPRICE));
                    break;
                case FinConstants.WEIGHTEDPRICE:
                    SMV += (SMA - daily.getPrice(FinConstants.WEIGHTEDPRICE)) * (SMA - daily.getPrice(FinConstants.WEIGHTEDPRICE));
                    break;
                case FinConstants.VOLUME:
                    SMV += (SMA - daily.getVolume()) * (SMA - daily.getVolume());
                    break;
//                case RETURN:
//                    SMV += (SMA - instrument.getReturn(PrevIndex)) * (SMA -
//                            instrument.getReturn(PrevIndex));
//                    break;
            }
//            PrevIndex = instrument.getPrevIndex(PrevIndex);
            daily = instrument.getPrevDaily(daily)
            Count++;
        }
        return SMV / (Count - 1);
    }

    public static double SMV(TimeSeries series, Date date, int order) {
        double SMA = SMA(series, date, order);
        double SMV = 0;
        int Count = 0;
        if (series.isEmpty(date)) {
            return 0;
        }
        while (date != null && Count < order) {
            SMV += (SMA - series.getData(date)) * (SMA - series.getData(date));
            date = series.prevDate(date)
            Count++;
        }
        return SMV / (Count - 1);
    }

    public static double MOM(Instrument instrument, Date date, int order, FinConstants option) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        double MOM = 0;
        int Count = 0;
        Daily daily = instrument.daily(date);
            switch (option) {
                case FinConstants.HIGH:
                    MOM = daily.high;
                    break;
                case FinConstants.LOW:
                    MOM = daily.low;
                    break;
                case FinConstants.CLOSE:
                    MOM = daily.closeprice;
                    break;
                case FinConstants.PRICE:
                case FinConstants.MEDIANPRICE:
                case FinConstants.WEIGHTEDPRICE:
                case FinConstants.AVERAGEPRICE:
                    MOM = daily.price(option);
                    break;
                case FinConstants.VOLUME:
                    MOM = daily.volume;
                    break;
                case FinConstants.RETURN:
                    MOM = instrument.re(date);
                    break;
            }
        while (daily != null != -1 && Count < order) {
            daily = instrument.getPrevDaily(date);
            Count++;
        }
        if (daily == null) {
            return 0;
        } else {
//            daily.set(instrument.getDaily(date));
            switch (option) {
                case FinConstants.HIGH:
                    MOM -= daily.getHigh();
                    break;
                case FinConstants.LOW:
                    MOM -= daily.getLow();
                    break;
                case FinConstants.CLOSE:
                    MOM -= daily.getCloseprice();
                    break;
                case FinConstants.PRICE:
                    MOM -= daily.getPrice();
                    break;
                case FinConstants.MEDIANPRICE:
                    MOM -= daily.getPrice(FinConstants.MEDIANPRICE);
                    break;
                case FinConstants.WEIGHTEDPRICE:
                    MOM -= daily.getPrice(FinConstants.WEIGHTEDPRICE);
                    break;
                case FinConstants.AVERAGEPRICE:
                    MOM -= daily.getPrice(FinConstants.AVERAGEPRICE);
                    break;
                case FinConstants.VOLUME:
                    MOM -= daily.getVolume();
                    break;
                case FinConstants.RETURN:
                    MOM -= instrument.getReturn(date);
                    break;
            }
        }
        return MOM;
    }

    public static double MOM(TimeSeries series, Date date, int order) {
        double MOM = 0;
        int Count = 0;

        if (series.isEmpty(date)) {
            return 0;
        } else {
            MOM = series.getData(date);
        }

        while (date != null && Count < order) {
            date = series.prevDate(date)
            Count++;
        }

        if (date == null) {
            return 0;
        } else {
            MOM -= series.getData(date);
        }

        return MOM;
    }


    public static double ROC(TimeSeries series, Date index, int order) {
        double ROC = 0;
        int Count = 0;
        if (series.isEmpty(index)) {
            return 0;
        } else {
            ROC = series.getData(index);
        }
        while (index != null && Count < order) {
            index = series.prevDate(index);
            Count++;
        }
        if (index == -1) {
            return 0;
        } else {
            ROC /= series.getData(index);
        }
        return ROC;
    }

//    // Commodity channel index
    public static double CCI(TimeSeries series, Date date, int order) {
        double D = 0;
        double P;
        double media;
        if (series.isEmpty(date)) {
            return 0;
        } else {
            P = series.getData(date);
            media = SMA(series, date, order);
        }
        int Count = 0;

        while (date != null && Count < order) {
            if (date == null) {
                return 0;
            }
            D += Math.abs(series.getData(date) - media);
            Count++;
            date = series.prevDate(date)
        }
        D /= Count;
        return (P - media) / (0.015 * D);
    }


    public static double RSI(TimeSeries series, Date date, int order) {
        double previous = 0.0;
        if (series.isEmpty(date))
            return 0.0;

        previous = series.getData(date);
        double up = 0;
        double down = 0;
        int count = 0;
        double curr = 0.0;
        while (date != null && count < order) {
            date = series.prevDate(date);
            curr = series.getData(date);
            if (curr > previous) {
                up += curr - previous;
            } else {
                down += previous - curr;
            }
            previous = curr;
            count++;
        }
        if (date == -1) {
            return 50.0;
        }
        if (down == 0.0) {
            return 100D;
        } else {
            return (100D - (100D / (1.0 + (up / down))));
        }
    }


    public static double SMD(Instrument instrument, Date date, int order, int option) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        return Math.sqrt(SMV(instrument, date, order, option));
    }

    public static double SMD(TimeSeries series, Date date, int order) {
        return Math.sqrt(SMV(series, date, order));
    }

    public static double TO(TimeSeries series, Date date, int order1, int order2) {
        return SMA(series, date, order1) / SMA(series, date, order2);
    }

    public static double PCL(TimeSeries series, Date date, int Order, double K) {
        return (1.0 - K / 100D) * SMA(series, date, Order);
    }

    public static double PCU(TimeSeries series, Date index, int order, double K) {
        return (1.0 + K / 100D) * SMA(series, index, order);
    }

}
