package com.netnumeri.server.finance.beans;


public class NumericalRecipesSeries {

    double[] data;
    Date[] dates;

    public NumericalRecipesSeries() {
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public void setValue(int index, double data) {
        this.data[index] = data;
    }

    public void setDate(int index, Date data) {
        this.dates[index] = data;
    }

    public double getValue(int index) {
        return this.data[index];
    }

    public Date getDate(int index) {
        return this.dates[index];
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public int getSize() {
        return data.length - 1;
    }

    public double getLastValue() {
        return getValue(data.length - 1);
    }

//    public Date lastDate() {
//        return getDate(data.length - 1);
//    }

}