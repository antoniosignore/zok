// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConstraintLinearNegative.java

package com.dtmc.algorithms.doji;

import java.util.Iterator;
import java.util.List;

class ConstraintLinearNegative extends ConstraintLinear {

    public ConstraintLinearNegative(Scalar factor, Iterable scalars) {
        super(factor, scalars);
    }

    protected boolean process(List data, int index) {
        double left = 0.0D;
        for (Iterator i$ = getScalars().iterator(); i$.hasNext(); ) {
            Scalar scalar = (Scalar) i$.next();
            left += scalar.calculateValue(data, index);
        }

        double right = 0.0D + getTolerance().calculateValue(data, index);
        return Double.compare(left, right) < 0;
    }
}