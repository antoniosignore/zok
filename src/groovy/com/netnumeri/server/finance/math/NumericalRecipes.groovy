package com.netnumeri.server.finance.math

import com.netnumeri.server.finance.beans.Moment
import com.netnumeri.server.finance.beans.NRError;


public class NumericalRecipes {

//    private static native JsArrayString regExpMatch(String regEx, String target) /*-{
//        var re = new RegExp();
//        return re.compile(regEx).exec(target);
//    }-*/;
//
//    public static Long getPortfolioLongId(String portfolioId) {
//        JsArrayString match = regExpMatch("\\/(\\d+)$", portfolioId);
//        if (match.length() > 1) {
//            portfolioId = match.get(1);
//        } else {
//            GWT.log("Error parsing the portfolio id.", null);
//        }
//        return Long.parseLong(portfolioId);
//    }

    public static int[] ivector(int startIndex, int size) {
        int[] v = new int[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0;
        }
        return (v);
    }

    public static double[] vector(int startIndex, int size) {
        double[] v = new double[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0.0F;
        }
        return (v);
    }

    public static Date[] datesvector(int startIndex, int size) {
        Date[] v = new Date[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = null;
        }
        return (v);
    }

    public static int[] intvector(int startIndex, int size) {
        int[] v = new int[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0;
        }
        return (v);
    }

    public static double[][] matrix(int start1, int size1, int start2, int size2) {
        double[][] v = new double[start1 + size1][];

        for (int i = 0; i < v.length; i++) {
            v[i] = vector(start2, size2);
        }
        return (v);
    }

    public static void swap(double[] data, int a, int b) {
        double tempr = data[a];
        data[a] = data[b];
        data[b] = tempr;
    }

    public static double sign(double a, double b) {
        return (b >= 0.0F ? Math.abs(a) : -Math.abs(a));
    }

    public static void nrerror(String message)
            throws NRError {
        throw (new NRError(message));
    }

    public static int imin(int x, int y) {
        return (x < y ? x : y);
    }

    public static double max(double x, double y) {
        return (x > y ? x : y);
    }


    public static double[] sumSeries(double[] s1, double[] s2) {
        int size = s1.length - 1;
        double[] sum = NumericalRecipes.vector(1, size);
        for (int i = 1; i <= size; i++)
            sum[i] = s1[i] + s2[i];
        return sum;
    }

    public static double[] diffSeries(double[] s1, double[] s2) {
        int size = s1.length - 1;
        double[] diff = NumericalRecipes.vector(1, size);
        for (int i = 1; i <= size; i++)
            diff[i] = s1[i] - s2[i];
        return diff;
    }

    public static double[] exponential(double[] s1) {
        int size = s1.length - 1;
        double[] sum = NumericalRecipes.vector(1, size);
        for (int i = 1; i <= size; i++)
            sum[i] = (double) Math.exp(s1[i]);
        return sum;
    }

    public static double[] augmentByMovingAverage(double[] s1) throws NRError {
        double[] aug = NumericalRecipes.vector(1, size(s1));
        double[] madata = NumericalRecipes.vector(1, 10);
        Moment.MomentResult ma = null;
        for (int i = 1; i < size(s1); i++)
            aug[i] = s1[i + 1];
        double add = 0;
        for (int j = 1; j <= 10; j++) {
            double val = s1[size(s1) - 10 + j];
            madata[j] = val;
            add += val;
        }
        ma = Moment.moment(madata, 10);
        aug[size(s1)] = ma.ave;
        return aug;
    }

    public static double[] concatenateSeries(double[] s1, double[] s2) {
        double[] totSerie = NumericalRecipes.vector(1, NumericalRecipes.size(s1) + NumericalRecipes.size(s2));
        int i = -1;
        for (i = 1; i <= NumericalRecipes.size(s1); i++) {
            totSerie[i] = s1[i];
        }
        for (int j = 1; j <= NumericalRecipes.size(s2); j++) {
            totSerie[i] = s2[j];
            i++;
        }
        return totSerie;
    }

    public static double[] getPrevN(double[] series, int seriesSize, int today) {
        double[] totSerie = NumericalRecipes.vector(1, seriesSize);
        int i = -1;
        for (i = 1; i <= seriesSize; i++)
            totSerie[i] = series[today - seriesSize + i];
        return totSerie;
    }

    public static double[] getNextN(double[] series, int seriesSize, int today) {
        double[] totSerie = NumericalRecipes.vector(1, seriesSize);
        int i = -1;
        for (i = 1; i <= seriesSize; i++)
            totSerie[i] = series[today + i];
        return totSerie;
    }

    public static double getLastValue(double[] series) {
        return series[series.length - 1];
    }

    public static int size(double[] s1) {
        return s1.length - 1;
    }

    public static void setLastValue(double[] series, double x) {
        series[size(series)] = x;
    }

    public static double[] convert(double[] serie) {
        double[] ar = new double[serie.length];
        for (int i = 1; i < serie.length; i++) {
            ar[i - 1] = (double) serie[i];
        }
        return ar;
    }

//    public static Date toYYmmDD(String date, String format) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat(format);
//            Date da = sdf.parse(date);
//            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
//            return new Date(sdf2.format(da));
//        } catch (ParseException e) {
//        }
//        return null;
//    }

}