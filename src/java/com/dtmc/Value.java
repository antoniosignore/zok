package com.dtmc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Value implements Comparable {

    public Value(Date date) {
        _date = date;
        _values = new HashMap();
    }

    public Value(Date date, Map values) {
        _date = date;
        _values = new HashMap();
        _values.putAll(values);
    }

    public Date date() {
        return _date;
    }

    public Object get(String key) {
        return _values.get(key);
    }

    public void set(String key, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        } else {
            _values.put(key, value);
            return;
        }
    }

    public int compareTo(Value o) {
        return date().compareTo(o.date());
    }

    public int compareTo(Object x0) {
        return compareTo((Value) x0);
    }

    private final Date _date;
    private final Map _values;
}
