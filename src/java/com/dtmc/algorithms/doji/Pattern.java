

package com.dtmc.algorithms.doji;

import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.dtmc.algorithms.doji:
//            Constraint

public final class Pattern
        implements Comparable {

    Pattern(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getForecast() {
        return forecast;
    }

    void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

    Iterable getConstraints() {
        return constraints;
    }

    void setConstraints(Iterable constraints) {
        this.constraints = constraints;
    }

    public boolean eval(List data, int index) {
        for (Iterator i$ = constraints.iterator(); i$.hasNext(); ) {
            Constraint constraint = (Constraint) i$.next();
            boolean result = constraint.eval(data, index);
            if (!result)
                return false;
        }

        return true;
    }

    public int compareTo(Pattern o) {
        return getId() - o.getId();
    }

    public int compareTo(Object x0) {
        return compareTo((Pattern) x0);
    }

    private final int id;
    private String forecast;
    private String name;
    private int count;
    private Iterable constraints;

    @Override
    public String toString() {
        return "Pattern{" +
                "id=" + id +
                ", forecast='" + forecast + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
