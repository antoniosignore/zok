
package com.dtmc.algorithms.doji;

import com.dtmc.Value;

import java.util.List;

class Scalar
        implements Cloneable {

    public Scalar(String source, int index, double value) {
        _source = source;
        _index = index;
        _value = value;
        _isConst = false;
    }

    public Scalar(String source, int index, double value, boolean isConst) {
        _source = source;
        _index = index;
        _value = value;
        _isConst = isConst;
    }

    void setValue(double value) {
        _value = value;
    }

    double getValue() {
        return _value;
    }

    boolean isConst() {
        return _isConst;
    }

    void setConst(boolean value) {
        _isConst = value;
    }

    double calculateValue(List data, int index) {
        Double value = (Double) ((Value) data.get(index + _index)).get(_source);
        return value == null ? getValue() : getValue() * value.doubleValue();
    }

    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (_source == null ? 0 : _source.hashCode());
        hash = 67 * hash + _index;
        hash = 67 * hash + (int) (Double.doubleToLongBits(_value) ^ Double.doubleToLongBits(_value) >>> 32);
        hash = 67 * hash + (_isConst ? 1 : 0);
        return hash;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Scalar)) {
            return false;
        } else {
            Scalar other = (Scalar) o;
            return _source.compareTo(other._source) == 0 && _index == other._index && Double.compare(_value, other._value) == 0 && _isConst == other._isConst;
        }
    }

    public Object clone() {
        return new Scalar(_source, _index, getValue(), isConst());
    }

    private final String _source;
    private int _index;
    private double _value;
    private boolean _isConst;
}
