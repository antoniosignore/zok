package com.netnumeri.server.finance.ta

import com.netnumeri.server.finance.beans.TimeSeries

import java.util.logging.Logger

import static com.netnumeri.server.finance.ssa.SSAMath.computeSeriesForecast

public class SSASeriesPredictionIndicator extends Indicator {

    private static final Logger logger = Logger.getLogger(SSASeriesPredictionIndicator.class.getName());

    List<Integer> components

    public SSASeriesPredictionIndicator(TimeSeries timeseries,
                                        String name,
                                        int window,
                                        int order,
                                        List<Integer> components,
                                        Integer noFuture) {
        super(timeseries, name)
        series.normalize()
        double[] prediction = computeSeriesForecast(series, noFuture, window, order, components)

        logger.info "\n\n\n\n\n*********************prediction = $prediction"

        copyInTheFuture(prediction)
    }

}


