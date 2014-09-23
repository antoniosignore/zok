package com.netnumeri.server.finance.beans

import com.dtmc.gson.DailyGSON
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.netnumeri.server.finance.math.NumericalRecipes
import com.netnumeri.server.finance.ssa.Histogram
import com.netnumeri.server.finance.utils.DateUtils

import java.text.DecimalFormat
import java.text.SimpleDateFormat

public class TimeSeries implements Serializable {

    String username;
    String name;
    def description;
    def active = true;
    def created = new Date();

    private FinConstants option;
    private int size = 0;
    protected int[] lengthsArray = null;

    GenericMatrix<Double> matrix = new GenericMatrix<Double>();

    private int correlationPairs = 0;

    public TimeSeries() {
        init();
    }

    public TimeSeries(String name) {
        this.name = name
        init();
    }

    public void init() {
        size = 0;
        lengthsArray = null;
        correlationPairs = 0;
    }

    public FinConstants getOption() {
        return option;
    }

    public void setOption(FinConstants Option) {
        option = Option;
    }

    public int getNRows() {
        return matrix.getNRows();
    }

    public boolean isEmpty(Date date) {
        return isEmpty(date, 0);
    }

    public boolean isEmpty(Date date, int row) {
        return matrix.isEmpty(row, date);
    }

    public int getNData() {
        return getNumberOfNotNullData(0);
    }

    // Return number of non-empty data entries for row
    public int getNumberOfNotNullData(int Row) {
        return matrix.size(Row);
    }

    public Double getData(Date date) {
        return getData(date, 0);
    }

    public Double getData(Date date, int row) {
        if (date == null) return null;
        return matrix.get(row, date);
    }

    public void set(Date date, double value) {
        set(date, value, 0);
    }

    public void set(Date date, double value, int row) {
        add(row, date, value);
    }

    public void set(TimeSeries Series) {
        set(Series, 0, 0);
    }

    public void set(TimeSeries series, int FromRow, int ToRow) {

        Map<Date, Double> map = series.matrix.map(FromRow);
        Set<Date> dateSet = map.keySet();
        for (Iterator<Date> iterator = dateSet.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            add(ToRow, date, map.get(date));
        }
    }

    public void set(TimeSeries Series, Date firstDate, Date lastDate) {
        set(Series, firstDate, lastDate, 0, 0);
    }

    public void set(TimeSeries series, Date firstDate, Date lastDate, int fromRow, int toRow) {
        firstDate = DateUtils.max(firstDate, series.firstDate());
        lastDate = DateUtils.min(lastDate, series.lastDate());

        Set<Date> dates1 = getDates(fromRow, firstDate, lastDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date next = iterator.next();
            add(toRow, next, series.getData(next, fromRow));
        }
    }

    Date firstDate() {
        return firstDate(0)
    }

    Date firstDate(int row) {
        return matrix.firstDate(row)
    }

    Date nextDate(Date date) {
        return matrix.nextDate(0, date)
    }

    Date prevDate(Date date) {
        return matrix.prevDate(0, date)
    }

    Date lastDate() {
        return lastDate(0)
    }

    Date lastDate(int row) {
        return matrix.lastDate(row)
    }

    public void add(Date date, double data) {
        add(0, date, data);
    }

    public void add(int row, Date date, double value) {
        matrix.put(row, date, value);
    }

    public double timeSeries() {
        return timeSeries(firstDate(0), lastDate(0), 0);
    }

    public TimeSeries timeSeries(Date first, Date last) {
        return timeSeries(first, last, 0);
    }

    // Return subseries from first to last
    public TimeSeries timeSeries(Date first, Date last, int row) {
        if (!matrix.isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        TimeSeries timeseries = new TimeSeries();
        if (last == null) last = lastDate();

        Set<Date> dates1 = getDates(row, first, last)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date next = iterator.next();
            timeseries.add(row, next, getData(next, row));
        }
        return timeseries;
    }

    public TimeSeries getAccumulatedSeries() {
        return getAccumulatedSeries(0, firstDate(0), lastDate(0));
    }

    public TimeSeries getAccumulatedSeries(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        TimeSeries AccumulatedSeries = new TimeSeries();
        double AccumulatedData = 0;

        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date next = iterator.next();
            AccumulatedData += getData(next);
            AccumulatedSeries.add(next, AccumulatedData);
        }
        return AccumulatedSeries;
    }

    public TimeSeries getDiffSeries(TimeSeries Series) {
        return getDiffSeries(Series, 0, firstDate(0), lastDate(0));
    }

    public TimeSeries getDiffSeries(TimeSeries Series, int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        TimeSeries diffSeries = new TimeSeries();
        firstCalendarDate = DateUtils.max(firstDate(), Series.firstDate());
        lastCalendarDate = DateUtils.min(lastDate(), Series.lastDate());

        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date next = iterator.next();
            diffSeries.add(next, getData(next) - Series.getData(next));
        }
        return diffSeries;
    }

    public TimeSeries getShiftedSeries() {
        return getShiftedSeries(1, firstDate(0), lastDate(0));
    }

    public TimeSeries getShiftedSeries(Date firstCalendarDate, Date lastCalendarDate) {
        return getShiftedSeries(1, firstCalendarDate, lastCalendarDate);
    }

    public TimeSeries getShiftedSeries(int Lag, Date firstCalendarDate, Date lastCalendarDate) {
        TimeSeries shiftedSeries = new TimeSeries();

//        Date firstCalendarDate = firstDate();
//        Date lastCalendarDate = lastDate();
//
//        if (from != null) firstCalendarDate = from;
//        if (to != null) lastCalendarDate = to;

        for (Date date = firstCalendarDate; DateUtils.isLessEqual(date, lastCalendarDate); date = DateUtils.addDays(date, 1)) {
            if (!isEmpty(date)) {
                date = DateUtils.addDays(date, Lag);
                shiftedSeries.add(date, getData(date));
            }
        }
        return shiftedSeries;
    }

    public double getFirstData() {
        return getFirstData(0);
    }

    public double getFirstData(int row) {
        checkParams(row, firstDate())
        return matrix.get(row, firstDate());
    }

    public double getLastValidData() {
        return getLastValidData(0);
    }

    public double getLastValidData(int row) {
        return matrix.lastValidData(row);
    }

    public double getLastData() {
        return getLastData(0);
    }

    public double getLastData(int row) {
        if (!matrix.isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return matrix.lastValidData(row);
    }

    public double getMin() {
        return getMin(0, firstDate(0), lastDate(0));
    }

    public double getMin(Date firstCalendarDate, Date lastCalendarDate) {
        return getMin(0, firstCalendarDate, lastCalendarDate);
    }

    // Calculate min data value between first and last index in row
    public double getMin(int row, Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)

        double Min = Double.MAX_VALUE;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            Min = Math.min(Min, matrix.get(row, date));
        }
        return Min;
    }

    public double getMax() {
        return getMax(0, firstDate(0), lastDate(0));
    }

    public double getMax(Date firstCalendarDate, Date lastCalendarDate) {
        return getMax(0, firstCalendarDate, lastCalendarDate);
    }

    // Calculate max data value between first and last index in row
    public double getMax(int row, Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)

        double Max = Double.MIN_VALUE;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            Max = Math.max(Max, matrix.get(row, date));
        }
        return Max;
    }

    // Return number of zero data entries in row
    public double getNZero() {
        return getNZero(0, firstDate(0), lastDate(0));
    }

    public int getNZero(int row, Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            if (matrix.get(row, date) == 0) N++;
        }
        return N;
    }

    /**
     * Return number of non zero data entries
     */
    public double getNNonZero() {
        return getNNonZero(0, firstDate(0), lastDate(0));
    }

    public int getNNonZero(Date firstCalendarDate, Date lastCalendarDate) {
        return getNNonZero(0, firstCalendarDate, lastCalendarDate);
    }

    /**
     * Return number of non zero data entries in row
     *
     * @param row
     */
    public int getNNonZero(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            if (matrix.get(row, date) != 0) N++;
        }
        return N;
    }

    public int getNPositive(Date firstCalendarDate, Date lastCalendarDate) {
        return getNPositive(0, firstCalendarDate, lastCalendarDate);
    }

    // Return number of positive (sign) data entries in row
    public double getNPositive() {
        return getNPositive(0, firstDate(0), lastDate(0));
    }

    public int getNPositive(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            if (matrix.get(row, date) > 0) N++;
        }
        return N;
    }

    // Return number of negative (sign) data entries in row
    public int getNNegative() {
        return getNNegative(0, firstDate(0), lastDate(0));
    }

    public int getNNegative(Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        return getNNegative(0, firstCalendarDate, lastCalendarDate);
    }

    public int getNNegative(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            if (matrix.get(row, date) < 0) N++;
        }
        return N;
    }

    public double getMean() {
        return getMean(0, firstDate(0), lastDate(0));
    }

    public double getMean(Date firstCalendarDate, Date lastCalendarDate) {
        return getMean(0, firstCalendarDate, lastCalendarDate);
    }

    public double getMean(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
        double Mean = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            Mean += val;
            N++;
        }
        return Mean / N;
    }

    public double getVariance() {
        return getVariance(0, firstDate(0), lastDate(0));
    }

    public double getVariance(Date firstCalendarDate, Date lastCalendarDate) {
        if (firstCalendarDate == null || lastCalendarDate == null) return 0;

        return getVariance(0, firstCalendarDate, lastCalendarDate);
    }

    /*
    public double getVariance(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        double Mean = getMean(firstIndex, lastIndex, row);
        int N = 0;
        double Variance = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (!isEmpty(i)) {
                Variance += (matrix.get(row, i) - Mean) * (matrix.get(row, i) - Mean);
                N++;
            }
        }
        return Variance / (N - 1);
    }
     */

    public double getVariance(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int N = 0;
//        double Mean = 0;
        double Mean = getMean(row, firstCalendarDate, lastCalendarDate);
        double Variance = 0;
        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            Variance += (val - Mean) * (val - Mean);
            N++;
        }
        return Variance / (N - 1);
    }

    private void checkParams(int row, Date firstCalendarDate, Date lastCalendarDate) {
        if (!matrix.isValidRow(row))
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        if (firstCalendarDate == null || lastCalendarDate == null)
            throw new IllegalArgumentException("FirstCalendarDate and/or lastCalendarDate are null")
    }

    private void checkParams(int row, Date firstCalendarDate) {
        if (!matrix.isValidRow(row))
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        if (firstCalendarDate == null)
            throw new IllegalArgumentException("FirstCalendarDate is null")
    }

    private Set<Date> getDates(int row, Date firstCalendarDate, Date lastCalendarDate) {
        TreeMap<Date, Double> map1 = matrix.map(row);
        SortedMap<Date, Double> dateDoubleSortedMap = map1.subMap(firstCalendarDate, true, lastCalendarDate, true);
        Set<Date> dates1 = dateDoubleSortedMap.keySet();
        return dates1
    }

    private Set<Date> getDates(Date firstCalendarDate, Date lastCalendarDate) {
        return getDates(0, firstCalendarDate, lastCalendarDate)
    }

    public double getStandardDeviation() {
        return getStandardDeviation(0, firstDate(0), lastDate(0));
    }

    public double getStandardDeviation(Date firstCalendarDate, Date lastCalendarDate) {
        return getStandardDeviation(0, firstCalendarDate, lastCalendarDate);
    }

    // Return standard deviation
    public double getStandardDeviation(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        return Math.sqrt(getVariance(row, firstCalendarDate, lastCalendarDate));
    }

    public double getStandardError() {
        return getStandardError(0, firstDate(0), lastDate(0));
    }

    public double getStandardError(Date firstCalendarDate, Date lastCalendarDate) {
        return getStandardError(0, firstCalendarDate, lastCalendarDate);
    }

    public double getStandardError(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        double Mean = getMean(row, firstCalendarDate, lastCalendarDate);
        int N = 0;
        double SE = 0;

        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            SE += (val - Mean) * (val - Mean);
            N++;
        }

//        for (int i = firstIndex; i <= lastIndex; i++) {
//            if (!isEmpty(i)) {
//                SE += (valori[row][i] - Mean)*(valori[row][i] - Mean);
//                SE += (matrix.get(row, i) - Mean) * (matrix.get(row, i) - Mean);
//                N++;
//            }
//        }
        return Math.sqrt(SE / (N * (N - 1)));
    }


    public double getMomentum(int row) {
        return getMomentum(0, row, firstDate(0), lastDate(0));
    }

    public double getMomentum(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        return getMomentum(0, row, firstCalendarDate, lastCalendarDate);
    }

    // Return cental momentum of k-th order between first and last index
    // k = 1 - mean
    // k = 2 - variance
    // k = 3 - asymmetry
    // k = 4 - excess
    public double getMomentum(int row, int k, Date firstCalendarDate, Date lastCalendarDate) {
        if (!matrix.isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
//        firstIndex = Math.max(firstIndex, getFirstIndex());
//        lastIndex = Math.min(lastIndex, getLastIndex());

        double X;
        if (k == 1) {
            X = 0;
        } else {
            X = getMean(row, firstCalendarDate, lastCalendarDate);
        }
        int N = 0;
        double Momentum = 0;

        Set<Date> dates1 = getDates(row, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
//            Variance += (val - Mean) * (val - Mean);
            Momentum += Math.pow(val - X, k);
            N++;
        }

//        for (int i = firstIndex; i <= lastIndex; i++) {
//            if (!isEmpty(i)) {
//                // Momentum += Math.pow(valori[row][i] - Y, k);
//                Momentum += Math.pow(matrix.get(row, i) - Y, k);
//                N++;
//            }
//        }
        if (N == 0) {
            System.out.println("getMomentum . N = 0");
            return 0;
        } else {
            return Momentum / N;
        }
    }


    public double getAsimmetry() {
        return getAsimmetry(firstDate(0), lastDate(0));
    }

    public double getAsimmetry(Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)
        return getAsimmetry(0, firstCalendarDate, lastCalendarDate);
    }

    // Return asymmetry of time xyseries distribution between first and last index
    public double getAsimmetry(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        double StdDev = getStandardDeviation(row, firstCalendarDate, lastCalendarDate);
        if (StdDev == 0) {
            System.out.println("getAsimmetry. StdDev = 0");
            return 0;
        } else {
            return getMomentum(row, 3, firstCalendarDate, lastCalendarDate) / Math.pow(StdDev, 3);
        }
    }


    public double getExcess() {
        return getExcess(firstDate(0), lastDate(0));
    }

    public double getExcess(Date firstCalendarDate, Date lastCalendarDate) {
        return getExcess(0, firstCalendarDate, lastCalendarDate);
    }

    public double getExcess(int row, Date firstCalendarDate, Date lastCalendarDate) {
        // Return excess of time xyseries distribution between first and last index
        checkParams(row, firstCalendarDate, lastCalendarDate)

        double StdDev = getStandardDeviation(row, firstCalendarDate, lastCalendarDate);
        if (StdDev == 0) {
            System.out.println("getAsimmetry. StdDev = 0");
            return 0;
        } else {
            return getMomentum(row, 4, firstCalendarDate, lastCalendarDate) / Math.pow(StdDev, 4);
        }
    }

    public double getError() {
        return getError(0);
    }

    public double getError(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        return getStandardDeviation(row,
                firstCalendarDate, lastCalendarDate) /
                Math.sqrt(matrix.noElements(row, firstCalendarDate, lastCalendarDate));
    }

    // Calculate covariance between two rows
    public double getCovariance(int row1, int row2, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        double Mean1 = getMean(row1, firstCalendarDate, lastCalendarDate);
        double Mean2 = getMean(row2, firstCalendarDate, lastCalendarDate);
        correlationPairs = 0;
        double Covariance = 0;

        Set<Date> dates1 = getDates(row1, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            correlationPairs++;
            Covariance += (matrix.get(row1, date) - Mean1) * (matrix.get(row2, date) - Mean2);
        }

        if (correlationPairs <= 1) return 0;
        return Covariance / (correlationPairs - 1);
    }

    /**
     * Calculate correlation between two rows
     */
    public double getCorrelation(int row1, int row2, Date firstCalendarDate, Date lastCalendarDate) {
        return getCovariance(row1, row2, firstCalendarDate, lastCalendarDate) /
                (getStandardDeviation(row1, firstCalendarDate, lastCalendarDate) * getStandardDeviation(row2, firstCalendarDate, lastCalendarDate));
    }

    public double getCovariance(TimeSeries series, Date firstCalendarDate, Date lastCalendarDate) {
        double Mean1 = getMean(firstCalendarDate, lastCalendarDate);
        double Mean2 = series.getMean(firstCalendarDate, lastCalendarDate);

        correlationPairs = 0;
        double Covariance = 0;

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)

        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            correlationPairs++;
            double val1 = matrix.get(0, date);
            double val2 = series.matrix.get(0, date);
            Covariance += (val1 - Mean1) * (val2 - Mean2);
        }
        if (correlationPairs <= 1) return 0;
        return Covariance / (correlationPairs - 1);
    }

    // Calculate correlation
    public double getCorrelation(TimeSeries series, Date firstCalendarDate, Date lastCalendarDate) {
        return getCovariance(series, firstCalendarDate, lastCalendarDate) /
                (getStandardDeviation(firstCalendarDate, lastCalendarDate) * series.getStandardDeviation(firstCalendarDate, lastCalendarDate));
    }

    // Return number of data pairs used in correlation calculations
    public int getNCorrelationPairs(TimeSeries series, Date firstCalendarDate, Date lastCalendarDate) {
        getCovariance(series, firstCalendarDate, lastCalendarDate);
        return correlationPairs;
    }

//    public double getAutoCovariance(Date firstCalendarDate, Date lastCalendarDate) {
//        return getAutoCovariance(1,firstCalendarDate,lastCalendarDate);
//    }

    // Calculate auto covariance for lag
//    public double getAutoCovariance(int lag, Date firstCalendarDate, Date lastCalendarDate) {
//        double Mean = getMean(firstCalendarDate,lastCalendarDate);
//        double AutoCovariance = 0;
//
//        int CurrentIndex = getFirstIndex();
//        int LaggedIndex = getFirstIndex();
//
//        for (int i = 0; i < lag; i++) {
//            LaggedIndex = getNextIndex(LaggedIndex);
//            if (LaggedIndex == -1) {
//                return 0;
//            }
//        }
//
//        int N = 0;
//        while (LaggedIndex != -1) {
//            AutoCovariance += (matrix.get(0, CurrentIndex) - Mean) * (matrix.get(0, LaggedIndex) - Mean);
//            N++;
//            CurrentIndex = getNextIndex(CurrentIndex);
//            LaggedIndex = getNextIndex(LaggedIndex);
//        }
//        if (N <= lag) {
//            return 0;
//        }
//        AutoCovariance /= (N - lag);
//        return AutoCovariance;
//    }

//    public double getAutoCorrelation(Date firstCalendarDate, Date lastCalendarDate) {
//        return getAutoCorrelation(1,firstCalendarDate,lastCalendarDate);
//    }
//
//    public double getAutoCorrelation(int lag, Date firstCalendarDate, Date lastCalendarDate) {
//        return getAutoCovariance(lag,firstCalendarDate,lastCalendarDate) / getVariance(firstCalendarDate,lastCalendarDate);
//    }

    public TimeSeries getReturnSeries() {
        return getReturnSeries(1, firstDate(0), lastDate(0));
    }

    public TimeSeries getReturnSeries(Date firstCalendarDate, Date lastCalendarDate) {
        return getReturnSeries(1, firstCalendarDate, lastCalendarDate);
    }

    public TimeSeries getReturnSeries(int lag, Date firstCalendarDate, Date lastCalendarDate) {
        TimeSeries ReturnSeries = new TimeSeries();
        ReturnSeries.setOption(FinConstants.RETURN);
        double Data;
        double LastData = getData(firstCalendarDate);

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            Data = getData(date);
            if (LastData != 0)
                ReturnSeries.add(date, Data / LastData);
            LastData = Data;
        }
        return ReturnSeries;
    }

    public TimeSeries getLogReturnSeries() {
        return getLogReturnSeries(1, firstDate(0), lastDate(0));
    }

    public TimeSeries getLogReturnSeries(Date firstCalendarDate, Date lastCalendarDate) {
        return getLogReturnSeries(1, firstCalendarDate, lastCalendarDate);
    }

    public TimeSeries getLogReturnSeries(int Lag, Date firstCalendarDate, Date lastCalendarDate) {
        TimeSeries ReturnSeries = new TimeSeries();
        ReturnSeries.setOption(FinConstants.LOGRETURN);
        double Data;
        double LastData = getData(firstCalendarDate);

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            Data = getData(date);
            if (LastData != 0)
                ReturnSeries.add(date, Math.log(Data / LastData));
            LastData = Data;
        }
        return ReturnSeries;
    }


    public int getNCrossings() {
        return getNCrossings(1, firstDate(0), lastDate(0));
    }

    public int getNCrossings(Date firstCalendarDate, Date lastCalendarDate) {
        return getNCrossings(0, firstCalendarDate, lastCalendarDate);
    }

    public int getNCrossings(double Level, Date firstCalendarDate, Date lastCalendarDate) {
        boolean IsBelow;
        double Data = getFirstData();
        if (Data < Level)
            IsBelow = true;
        else
            IsBelow = false;
//        int FirstIndex = getFirstIndex() + 1;
//        int LastIndex = getLastIndex();

        int NCrossings = 0;

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        int i = 0;
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            if (i == 0) {
                i++; continue
            };
            Date date = iterator.next();
            Data = getData(date);
            if (IsBelow && Data > Level || (!IsBelow && Data < Level)) {
                NCrossings++;
                IsBelow = !IsBelow;
            }
            i++;
        }
        return NCrossings;
    }

    // clear time xyseries
    public void clear() {
        init();
    }

    public void normalize() {
        normalize(0, firstDate(0), lastDate(0));
    }

    public void normalize(Date firstCalendarDate, Date lastCalendarDate) {
        normalize(0, firstCalendarDate, lastCalendarDate);
    }

    // Normalize data inside a window.
    // Note that data outside the window remain untouched!
    public void normalize(int row, Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)

        double Mean = getMean(row, firstCalendarDate, lastCalendarDate);
        double StandardDeviation = getStandardDeviation(row, firstCalendarDate, lastCalendarDate);

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            matrix.put(row, date, (matrix.get(row, date) - Mean) / StandardDeviation);
        }
    }

    public void linearMapping(double lower, double upper) {
        linearMapping(lower, upper, firstDate(0), lastDate(0));
    }

    public void linearMapping(double lower, double upper, Date firstCalendarDate, Date lastCalendarDate) {
        linearMapping(lower, upper, 0, firstCalendarDate, lastCalendarDate);
    }

    // Perform linear mapping for row data inside a window.
    public void linearMapping(double Lower, double Upper, int Row, Date firstCalendarDate, Date lastCalendarDate) {
        double Alpha = (Upper - Lower) / (getMax(Row, firstCalendarDate, lastCalendarDate) - getMin(Row, firstCalendarDate, lastCalendarDate));
        double Beta = -1 * Alpha * getMin(Row, firstCalendarDate, lastCalendarDate) + Lower;

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            matrix.put(Row, date, matrix.get(Row, date) * Alpha + Beta);
        }
    }

    /* smooth time xyseries with simple moving average
     */
//    public void SMA(int Order) {
//        int FirstIndex = getLowerBoundIndex();
//        int LastIndex = getUpperBoundIndex();
//        double Sum = 0;
//        double LastData = 0;
//        double PointCount = 0;
//        int Index;
//        for (Index = FirstIndex; Index <= LastIndex; Index++) {
//            if (!isEmpty(Index)) {
//                LastData = matrix.get(0, Index);
//                Sum += LastData;
//                PointCount++;
//                matrix.set(0, Index, Sum / PointCount);
//                if (PointCount == Order) {
//                    break;
//                }
//            }
//        }
//        FirstIndex = Index + 1;
//        for (Index = FirstIndex; Index <= LastIndex; Index++) {
//            if (!isEmpty(Index)) {
//                Sum -= LastData;
//                LastData = matrix.get(0, Index);
//                Sum += LastData;
//                matrix.set(0, Index, Sum / Order);
//            }
//        }
//    }

    // Return Lag time xyseries
//    public TimeSeries lag(int Periods) {
//        TimeSeries lag = new TimeSeries();
//        int i;
//        int k;
//        int index = 0;
//        int prev;
//        for (i = 0; i < Periods; i++) {
//            index = getNextIndex(index);
//        }
//        while (index != -1) {
//            prev = index;
//            for (i = 0; i < Periods; i++)
//                prev = getPrevIndex(prev);
//            for (k = 0; k < dimension; k++)
//                lag.add(k, getDate(index), getData(prev, k));
//            index = getNextIndex(index);
//        }
//        return lag;
//    }
//
//    public TimeSeries lead(int Periods) {
//        TimeSeries lead = new TimeSeries();
//        int i;
//        int k;
//        int index = getLastIndex();
//        int next;
//        int stopat;
//        for (i = 0; i < Periods; i++)
//            index = getPrevIndex(index);
//        stopat = index;
//        index = 0;
//        next = 0;
//        while (index <= stopat) {
//            next = index;
//            for (i = 0; i < Periods; i++)
//                next = getNextIndex(next);
//            for (k = 0; k < dimension; k++)
//                lead.add(k, getDate(index), getData(next, k));
//            index = getNextIndex(index);
//        }
//        return lead;
//    }

    // todo = clone the GenericMatrix
    public TimeSeries clone() {
        TimeSeries timeSeries = new TimeSeries();
        timeSeries.clear();
        timeSeries.size = this.size;
        timeSeries.matrix = this.matrix.cloneIt();
        timeSeries.option = option;
        timeSeries.correlationPairs = correlationPairs;
        return timeSeries;
    }

    public NumericalRecipesSeries asRecipes(int row, Date firstCalendarDate, Date lastCalendarDate) {
        NumericalRecipesSeries series = new NumericalRecipesSeries();
        int size = matrix.noElements(row, firstCalendarDate, lastCalendarDate);
        series.setData(NumericalRecipes.vector(1, size));
        series.setDates(NumericalRecipes.datesvector(1, size));

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        int j = 1;
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            series.setValue(j, (double) val);
            series.setDate(j++, date);
        }
        return series;
    }

    public double[] convertToArray() {
        return convertToArray(0, firstDate(), lastDate());
    }

    public double[] convertToArray(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int size = matrix.noElements(row, firstCalendarDate, lastCalendarDate);
        double[] vals = new double[size];
        int j = 0;
        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            vals[j++] = val;
        }
        return vals;
    }


    public List<Double> convertToList() {
        return convertToList(0, firstDate(), lastDate());
    }

    public List<Double> convertToList(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        int size = matrix.noElements(row, firstCalendarDate, lastCalendarDate);
        List<Double> vals = new ArrayList<Double>();
        int j = 0;
        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)
        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();
            double val = matrix.get(row, date);
            vals.add(val);
        }
        return vals;
    }


    public String getTimeplotSeries() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.####");
        try {
            StringBuffer sb = new StringBuffer();
            Date d = firstDate();
            while (DateUtils.isLessEqual(d, lastDate())) {
                Double v = this.getData(d);
                if (v == null) {
                    d = DateUtils.addDays(d, 1)
                    continue
                };
                sb.append(sdf.format(d)).append(' ').append(df.format(v)).append("\n");
                d = DateUtils.addDays(d, 1)
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DailyGSON buildDaily(String day, String val) {
        DailyGSON d1 = new DailyGSON();
        d1.period = day;
        d1.setClose(val);
        return d1;
    }

    public String getJsonSeries() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.####");
        List<DailyGSON> ds = new ArrayList<DailyGSON>();
        try {
            Date d = firstDate();
            while (DateUtils.isLessEqual(d, lastDate())) {
                Double v = this.getData(d);
                if (v == null) {
                    d = DateUtils.addDays(d, 1)
                    continue
                };
                ds.add(buildDaily(sdf.format(d), df.format(v)));
                d = DateUtils.addDays(d, 1)
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(ds);
            return jsonOutput;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJqPlot() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat df = new DecimalFormat("#.####");
        List<DailyGSON> ds = new ArrayList<DailyGSON>();
        try {
            Date d = firstDate();
            StringBuffer sb = new StringBuffer("[");
            while (DateUtils.isLessEqual(d, lastDate())) {
                Double v = this.getData(d);
                if (v == null) {
                    d = DateUtils.addDays(d, 1)
                    continue
                };
                ds.add(buildDaily(sdf.format(d), df.format(v)));
                if (d.getTime() == lastDate().getTime())
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

    public String getNoDateSeries() {
        DecimalFormat df = new DecimalFormat("#.####");
        try {
            StringBuffer sb = new StringBuffer();
            Date d = firstDate();
            while (DateUtils.isLessEqual(d, lastDate())) {
                Double v = this.getData(d);
                if (v == null) {
                    d = DateUtils.addDays(d, 1)
                    continue
                };
                sb.append(df.format(v)).append("\n");
                d = DateUtils.addDays(d, 1)
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSize(int row, Date firstCalendarDate, Date lastCalendarDate) {

        checkParams(row, firstCalendarDate, lastCalendarDate)

        return matrix.noElements(row, firstCalendarDate, lastCalendarDate);
    }

    public Integer getSize() {
        try {
            return matrix.noElements(0, firstDate(), lastDate());
        } catch (Throwable ex) {
            return 0;
        }
    }

    double getValue(int i) {
        return matrix.value(i)
    }

    Histogram histogram() {
        return histogram(0, firstDate(), lastDate());
    }

    /*
    double[] f = {1, 2, 3, 4, 5, 6, 5, 4, 7, 8, 9, 3, 1, 4, 6, 8, 9, 7, 4, 1};
List<Integer> ext = new ArrayList<Integer> ();
for (int i = 0; i<f.length-2; i++) {
  if ((f[i+1]-f[i])*(f[i+2]-f[i+1]) <= 0) { // changed sign?
    ext.add(i+1);
  }
}
     */

    public Histogram histogram(int row, Date firstCalendarDate, Date lastCalendarDate) {
        checkParams(row, firstCalendarDate, lastCalendarDate)

        Histogram histogram = new Histogram(20, 100, 100)
        double distanza = 0;
        boolean foundFirst = false;

        Set<Date> dates1 = getDates(0, firstCalendarDate, lastCalendarDate)

        Date previousDate
        Date prevPreviousDate

        for (Iterator<Date> iterator = dates1.iterator(); iterator.hasNext();) {
            Date date = iterator.next();

            try {
                previousDate = this.prevDate(date)
                prevPreviousDate = this.prevDate(previousDate)
            } catch (Throwable th) {
                return
            }

            if (previousDate == null || prevPreviousDate == null)
                continue

            Double today = this.getData(date)
            Double yesterday = this.getData(previousDate)
            Double twoDaysBefore = this.getData(prevPreviousDate)

            boolean isATop = twoDaysBefore < yesterday && yesterday > today
            boolean isALow = twoDaysBefore > yesterday && yesterday < today

            if (isATop || isALow) {
                if (foundFirst) histogram.add(distanza)
                foundFirst = true
                distanza = 0
            } else
                distanza = distanza + 1;
        }
        return histogram
    }
}