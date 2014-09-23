package com.dtmc.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyGSON {

    String period;
    String close;

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public static void main(String[] args) {

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

}
