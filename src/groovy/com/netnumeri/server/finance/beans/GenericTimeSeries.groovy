package com.netnumeri.server.finance.beans

class GenericTimeSeries<T> extends TreeMap<Date, T> {

    @SuppressWarnings("unchecked")
    public GenericTimeSeries() {
        super(new DateComparator())
    }

    public T firstValue() {
        def key = firstKey();
        return get(key);
    }

    public Date nextDate(Date date) {
        return higherKey(date);
    }

    public Date prevDate(Date date) {
        return lowerKey(date);
    }

    public T nextValue(Date date) {
        Map.Entry<Date, T> entry = higherEntry(date)
        if (entry != null) return entry.value
        return null
    }

    public T prevValue(Date date) {
        Map.Entry<Date, T> entry = lowerEntry(date)
        if (entry != null) return entry.value
        return null
    }

    public T lastValue() {
        def key = lastKey();
        return get(key);
    }

    public Date firstDate() {
        return firstKey();
    }

    public Date lastDate() {
        return lastKey();
    }

    public TreeMap<Date, T> map() {
        return this
    }

    int noElements(Date firstCalendarDate, Date lastCalendarDate) {
        return subMap(firstCalendarDate, true, lastCalendarDate, true).size();
    }

    public Date dateByIndex(int i) {
        Object[] array = keySet().toArray()
        return array[i] as Date
    }

    public T valueByIndex(int i) {
        Object[] array = values().toArray()
        return array[i] as T
    }

    public int index(Date date) {
        return headMap(date).size();
    }
}

class DateComparator implements Comparator<Date> {
    @Override
    public int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

}
