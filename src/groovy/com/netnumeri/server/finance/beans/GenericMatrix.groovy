package com.netnumeri.server.finance.beans


class GenericMatrix<T> implements Serializable {

    private List<GenericTimeSeries<T>> listOfTimeSeries

    GenericMatrix() {
        listOfTimeSeries = new ArrayList<GenericTimeSeries<T>>();
    }

    TreeMap<Date, T> map(int row) {
        GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
        return gst.map();
    }

    T get(int row, Date date) {
        return listOfTimeSeries.get(0).get(date);
    }

    void put(int row, Date date, T value) {

        try {
            listOfTimeSeries.get(row)
        }
        catch (IndexOutOfBoundsException ex) {
            listOfTimeSeries.add(row, new GenericTimeSeries<T>())
        }
        listOfTimeSeries.get(row).put(date, value);
    }

    boolean isEmpty(int row, Date date) {
        try {
            if (listOfTimeSeries.get(row).get(date) != null) return false;
        } catch (Throwable th) {
            return true;
        }
        return true
    }

    public boolean isEmpty(int row) {
        try {
            if (listOfTimeSeries.get(row) == null || listOfTimeSeries.size() == 0)
                return true;
        } catch (Throwable th) {
            return true
        }
        return true;
    }

//    public int index ( Date date) {
//        def get = listOfTimeSeries.get(0);
//        return get.index(date);
//    }

    public T lastValidData(int row) {
        def get = listOfTimeSeries.get(row);
        return get.lastValue();
    }

    public boolean isValidRow(int row) {
        if (row < 0 || row >= listOfTimeSeries.size()) return false;
        else return true;
    }

    public int size(int row) {
        def get = listOfTimeSeries.get(row);
        return get.size();
    }

    public GenericMatrix<T> cloneIt() {
        GenericMatrix<T> cloned = new GenericMatrix<T>();

        for (int i = 0; i < listOfTimeSeries.size(); i++) {
            GenericTimeSeries<T> series = listOfTimeSeries.get(i);
            TreeMap<Date, T> map = series;
            Set<T> keys = map.keySet();
            Iterator<Integer> iterator = keys.iterator();
            while (iterator.hasNext()) {
                Date date = iterator.next();
                cloned.put(i, date, map.get(date))
            }
        }
        return cloned;
    }

    public Date firstDate(int row) {
        try {
            GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
            Date date = gst.firstDate()
            return date;
        } catch (Throwable rx) {
            return null;
        }
    }

    public Date nextDate(int row, Date date) {
        try {
            GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
            return gst.nextDate(date);
        } catch (Throwable rx) {
            return null;
        }
    }

    public Date prevDate(int row, Date date) {
        try {
            GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
            return gst.prevDate(date);
        } catch (Throwable rx) {
            return null;
        }
    }

    public Date lastDate(int row) {
        try {
            GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
            return gst.lastDate();
        } catch (Throwable rx) {
            return null;
        }
    }

    public int noElements(int row, Date firstCalendarDate, Date lastCalendarDate) {
        try {
            if (firstCalendarDate == null || lastCalendarDate == null) return 0;
            GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
            return gst.noElements(firstCalendarDate, lastCalendarDate)
        } catch (Throwable rx) {
            return 0;
        }
    }

    public int noElements(int row) {
        GenericTimeSeries<T> gst = listOfTimeSeries.get(row);
        return gst.noElements(firstDate(row), lastDate(row))
    }

    int getNRows() {
        return listOfTimeSeries.size();
    }

    public Date dateByIndex(int i) {
        GenericTimeSeries<T> gst = listOfTimeSeries.get(0);
        return gst.dateByIndex(i)
    }

    public T value(int i) {
        GenericTimeSeries<T> gst = listOfTimeSeries.get(0);
        return gst.valueByIndex(i)
    }

    public int index(Date date) {
        GenericTimeSeries<T> gst = listOfTimeSeries.get(0);
        return gst.index(date)
    }


}
