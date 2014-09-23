

package com.dtmc.algorithms.doji;

import java.util.List;

abstract class ConstraintLinear extends Constraint {

    public ConstraintLinear(Scalar factor, Iterable scalars) {
        super(factor, scalars);
    }

    protected abstract boolean process(List list, int i);
}
