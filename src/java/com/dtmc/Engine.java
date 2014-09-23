package com.dtmc;

import com.dtmc.algorithms.*;
import com.dtmc.algorithms.doji.PatternFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;
import java.util.*;


public final class Engine {
    private static final int MIN_SIZE = 40;
    private static final int LENGTH_SMA = 20;
    private static final int LENGTH_RSI = 14;
    private static final int LENGTH_TREND = 14;
    private static final int LENGTH_AVG_VOLATILITY = 365;
    private final Collection<IAlgorithm> algorithms;
    private final Iterable patterns;

    public Engine() throws ParserConfigurationException {
        patterns = createPatterns(null, null);
        algorithms = new ArrayList<IAlgorithm>();
        addBaseAlgorithms();
    }

    public Engine(InputStream stream) throws ParserConfigurationException {
        patterns = createPatterns(stream, null);
        algorithms = new ArrayList<IAlgorithm>();
        addBaseAlgorithms();
    }

    public Engine(Map<String, String> options) throws ParserConfigurationException {
        patterns = createPatterns(null, options);
        algorithms = new ArrayList<IAlgorithm>();
        addBaseAlgorithms();
    }

    public Engine(InputStream stream, Map<String, String> options) throws ParserConfigurationException {
        patterns = createPatterns(stream, options);
        algorithms = new ArrayList<IAlgorithm>();
        addBaseAlgorithms();
    }

    public void add(IAlgorithm algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("algorithm must be not null");
        } else {
            algorithms.add(algorithm);
            return;
        }
    }

    public void processHistory(List<Value> history) {
        int size = history.size();
        for (int index = 0; index < size; index++) {
            IAlgorithm algorithm;
            for (Iterator<IAlgorithm> i$ = algorithms.iterator(); i$.hasNext(); algorithm.execute(history, index))
                algorithm = i$.next();
        }
    }

    private Iterable createPatterns(InputStream stream, Map<String, String> options) throws ParserConfigurationException {
        try {
            File file = new File(".");
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            PatternFactory factory = options != null ? new PatternFactory(options) : new PatternFactory();
            return factory.loadPatterns();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addBaseAlgorithms() {
        algorithms.add(new AlgorithmHelper());
        algorithms.add(new AlgorithmAverage("CLOSE", "SMA", 20));
        algorithms.add(new AlgorithmVolatility("CLOSE", "SMA", "VOLATILITY", 20, false));
        algorithms.add(new AlgorithmAverage("VOLATILITY", "VOLATILITY_AVG", 365));
        algorithms.add(new AlgorithmGainLoss("CLOSE", "GAIN", "LOSS"));
        algorithms.add(new AlgorithmAvgGainLoss("GAIN", "LOSS", "GAIN_AVG", "LOSS_AVG", 14));
        algorithms.add(new AlgorithmRSI("GAIN_AVG", "LOSS_AVG", "RSI", 14));
        algorithms.add(new AlgorithmTrendNormalized("CLOSE", "TREND", 14));
        algorithms.add(new AlgorithmDoji(patterns, "PATTERNS", 40));
    }


}
