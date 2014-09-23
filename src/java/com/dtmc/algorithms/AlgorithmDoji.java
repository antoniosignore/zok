package com.dtmc.algorithms;

import com.dtmc.Value;
import com.dtmc.algorithms.doji.Pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public final class AlgorithmDoji implements IAlgorithm {

    private Iterable patterns;
    private final String target;
    private final int startPosition;

    public AlgorithmDoji(Iterable patterns, String target, int startPosition) {
        this.patterns = patterns;
        this.target = target;
        this.startPosition = startPosition;
    }

    public void execute(List data, int index) {
        Value current = (Value) data.get(index);
        Collection result = new ArrayList();
        if (index >= startPosition) {
            Iterator i$ = patterns.iterator();
            do {
                if (!i$.hasNext())
                    break;
                Pattern pattern = (Pattern) i$.next();
                if (pattern.eval(data, index)) {
                    result.add(pattern);
                }
            } while (true);
        }
        current.set(target, result);
    }


}
