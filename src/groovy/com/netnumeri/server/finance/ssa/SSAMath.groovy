package com.netnumeri.server.finance.ssa

import com.netnumeri.server.finance.beans.NRError
import com.netnumeri.server.finance.beans.TimeSeries
import com.netnumeri.server.finance.ta.Indicator
import com.netnumeri.server.finance.ta.MovingAverage
import com.netnumeri.server.finance.ta.SSAComponentsIndicator

public class SSAMath {

    private static double epsilon = 0.002F;

    public static double[] computeSeriesForecast(TimeSeries series,
                                                 Integer numberOfDaysInTheFuture,
                                                 int window,
                                                 int order,
                                                 List<Integer> components) throws NRError {

        double[] predictedSerie = new double[numberOfDaysInTheFuture];
        double[] seriesAsArray = series.convertToArray();

        double[] augmented = getAugmentedByMovingAverage(seriesAsArray, order)

        for (int i = 0; i < numberOfDaysInTheFuture; i++) {

            double[] componentsForecastValues = new double[components.size()]
            println "SSAMath.computeForecast no : " + i
            for (int j = 0; j < components.size(); j++) {
                Integer component = components.get(j);
                double componentForecast = forecastSingleComponent(augmented, component, window)
                componentsForecastValues[j] = componentForecast
            }

            double lastPrediction = arraySum(componentsForecastValues);
            println "lastPrediction = $lastPrediction"

            predictedSerie[i] = lastPrediction
            augmented[augmented.size() - 1] = lastPrediction
            augmented = getAugmentedByMovingAverage(augmented, order)
        }
        return predictedSerie;
    }

    static double arraySum(double[] doubles) {
        double sum = 0;
        for (int i = 0; i < doubles.length; i++) {
            double d = doubles[i];
            sum = sum + d;
        }
        return sum;
    }

    private static double forecastSingleComponent(double[] augmented, int component, int window) {

        SSAStudy ssa = new SSAStudy(augmented)
        List<SSAItem> items = ssa.analyze(window)
        List<Integer> components = new ArrayList<>()
        components.add(component)
        double[] singleComponentSeries = ssa.reconstructedGroup(items, components)
        double lastPrediction = singleComponentSeries[singleComponentSeries.size() - 1];
        while (true) {
            double prediction = singleComponentSeries[singleComponentSeries.size() - 1];
            if (Math.abs(prediction - lastPrediction) < epsilon) {
                return prediction
            }
            lastPrediction = prediction;
        }
    }

//    private static List<Double> getAugmentedByMovingAverage(double[] array, int order) {
//        double[] average = MovingAverage.simpleMovingAverage(array, order)
//        double movingAverage = average[average.length - 1];
//        List<Double> augmented = new ArrayList<Double>();
//        for (int i = 0; i < array.length; i++) {
//            double val = array[i];
//            augmented.add(val)
//        }
//        augmented.add(movingAverage);
//        return augmented
//    }

    private static double[] getAugmentedByMovingAverage(double[] array, int order) {
        double[] average = MovingAverage.simpleMovingAverage(array, order)
        double[] augmented = new double[array.length + 1];

        println "average[average.length-1] = " + average[average.length - 1]
        augmented[array.length] = average[average.length - 1]
        return augmented
    }

    private static List<Double> getAugmentedByMovingAverage(List<Double> array, int order) {
        double[] ar = new double[array.size()];

        for (int i = 0; i < array.size(); i++) {
            Double o = array.get(i);
            ar[i] = o;
        }

        return getAugmentedByMovingAverage(ar, order)
    }

//    public static double[] computeForecast(int numberOfDaysInTheFuture, int window, List<Integer> components,
//                                           SSAAnalysis analysis) throws NRError {
//        long start = System.currentTimeMillis();
//
//        double[] serie = analysis.getEigenComponents(components);
//
//        double[] predictedSerie = Util.vector(1, numberOfDaysInTheFuture);
//        double[] augmentedSerie = Util.augmentByMovingAverage(serie);
//        double lastPrediction;
//        for (int i = 1; i <= numberOfDaysInTheFuture; i++) {
//            println "SSAMath.computeForecast no : " + i
//
//            lastPrediction = Util.lastValue(augmentedSerie);
//            while (true) {
//                println "SSAMath.computeForecast"
//                SSAAnalysis eserie = new SSAAnalysis(StockUtils.toList(augmentedSerie), window);
//                double prediction = 0
//                for (int j = 0; j < components.size(); j++) {
//                    Integer o = components.get(j);
//                    double[] s1 = eserie.getEigenComponent(o);
//                    prediction = prediction + Util.lastValue(s1)
//                }
//                Util.setLastValue(augmentedSerie, prediction);
//
//                println "Math.abs(prediction - lastPrediction = " + (prediction - lastPrediction)
//
//                if (Math.abs(prediction - lastPrediction) < epsilon) {
//                    predictedSerie[i] = prediction;
//                    break;
//                }
//                lastPrediction = prediction;
//            }
//            augmentedSerie = Util.augmentByMovingAverage(augmentedSerie);
//        }
//        long end = System.currentTimeMillis();
//        Util.debug("DURATA = " + (end - start) / 1000);
//        return predictedSerie;
//    }

}
