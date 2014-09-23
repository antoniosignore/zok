
package com.dtmc.algorithms.doji;

import java.util.Iterator;
import java.util.List;


class ConstraintLinearPositive extends ConstraintLinear {

    public ConstraintLinearPositive(Scalar factor, Iterable scalars) {
        super(factor, scalars);
    }

    protected boolean process(List data, int index) {
        double left = 0.0D;
        for (Iterator i$ = getScalars().iterator(); i$.hasNext(); ) {
            Scalar scalar = (Scalar) i$.next();
            left += scalar.calculateValue(data, index);
        }

        left *= -1D;
        double right = 0.0D + getTolerance().calculateValue(data, index);
        return Double.compare(left, right) < 0;
    }
}
