package com.netnumeri.server.finance.indicator.utils;

import java.text.SimpleDateFormat

public class Utils {

    public static int differDays(String date1, String date2) {
        long tDifMili = 0;
        try {
            SimpleDateFormat tFormat = new SimpleDateFormat("yyyyMMdd");
            int tDiferenca = 0;

            Date tDataIni = tFormat.parse(date1);
            Date tDataFim = tFormat.parse(date2);

            tDifMili = tDataFim.getTime() - tDataIni.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int) (tDifMili / (1000 * 60 * 60 * 24));
    }

}
