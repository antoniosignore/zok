

package com.dtmc.algorithms.doji;

import java.util.List;

// Referenced classes of package com.dtmc.algorithms.doji:
//            Scalar

abstract class Constraint {

    protected abstract boolean process(List list, int i);

    public Constraint(Scalar tolerance, Iterable scalars) {
        _tolerance = null;
        _scalars = null;
        _lastResult = false;
        _lastIndex = -1;
        _tolerance = tolerance;
        _scalars = scalars;
    }

    public Scalar getTolerance() {
        return _tolerance;
    }

    void setTolerance(Scalar tolerance) {
        _tolerance = tolerance;
    }

    Iterable getScalars() {
        return _scalars;
    }

    void setScalars(Iterable scalars) {
        _scalars = scalars;
    }

    boolean eval(List data, int index) {
        if (_lastIndex != index) {
            _lastResult = process(data, index);
            _lastIndex = index;
        }
        return _lastResult;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Constraint))
            return false;
        if (getClass().getName().compareTo(o.getClass().getName()) != 0) {
            return false;
        } else {
            Constraint other = (Constraint) o;
            return _tolerance.equals(other._tolerance) && _scalars.equals(other._scalars);
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (_tolerance == null ? 0 : _tolerance.hashCode());
        hash = 59 * hash + (_scalars == null ? 0 : _scalars.hashCode());
        return hash;
    }

    private Scalar _tolerance;
    private Iterable _scalars;
    private boolean _lastResult;
    private int _lastIndex;
}
