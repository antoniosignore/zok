package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.ssa.*

public class SSAComponentsIndicator extends Indicator {

    public SSAComponentsIndicator(TimeSeries series, String name, Integer window, List<Integer> components) {
        super(series, name)

        series.normalize()

        double[] arrayOfDoubles = series.convertToArray()
        SSAStudy ssa = new SSAStudy(arrayOfDoubles)
        List<SSAItem> items = ssa.analyze(window)
        double[] group = ssa.reconstructedGroup(items, components)
        copyBackwords group
    }

}


