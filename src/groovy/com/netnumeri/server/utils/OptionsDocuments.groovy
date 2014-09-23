package com.netnumeri.server.utils;

import org.dom4j.Document

public class OptionsDocuments {

    String ticker;

    public Document callsDocument;
    public Document putsDocument;
    private Date expirationDate

    public OptionsDocuments(String ticker) {
        this.ticker = ticker;
    }

    public void setCallsDocument(Document callsDocument) {
        this.callsDocument = callsDocument;
    }

    public void setPutsDocument(Document putsDocument) {
        this.putsDocument = putsDocument;
    }

    public Date getExpirationDate() {
        return expirationDate
    }

    public void setExpirationDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int numberOfFriday = 0;
        while (numberOfFriday < 3) {
            calendar.add(Calendar.DATE, 1);
            Date tomorrow = calendar.getTime()
            Calendar c = Calendar.getInstance();
            c.setTime(tomorrow);
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                numberOfFriday++
            }
        }
        expirationDate = calendar.getTime()
    }
}
