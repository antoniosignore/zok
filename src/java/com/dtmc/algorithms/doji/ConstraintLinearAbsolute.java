
package com.dtmc.algorithms.doji;

import java.util.Iterator;
import java.util.List;

class ConstraintLinearAbsolute extends ConstraintLinear {

    public ConstraintLinearAbsolute(Scalar factor, Iterable scalars) {
        super(factor, scalars);
    }

    protected boolean process(List data, int index) {
        double left = 0.0D;
        double consts = 0.0D;
        for (Iterator i$ = getScalars().iterator(); i$.hasNext(); ) {
            Scalar scalar = (Scalar) i$.next();
            if (scalar.isConst())
                consts += scalar.calculateValue(data, index);
            else
                left += scalar.calculateValue(data, index);
        }

        double right = 0.0D + getTolerance().calculateValue(data, index);
        return Double.compare(Math.abs(left) + consts, right) <= 0;
    }
}
