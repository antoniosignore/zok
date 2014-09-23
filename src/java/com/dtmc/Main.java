package com.dtmc;


import com.dtmc.algorithms.doji.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String IN_DATE_FORMAT = "yyyyMMdd";
    private static final String OUT_DATE_FORMAT = "yyyy/MM/dd";
    private static String INPUT_FILE = "/home/antonio/sample_history_data.txt";
    private static String SIMPLE_OUT_FILE = "simple_out.txt";
    private static String SIMPLE_ALG_OUT_FILE = "simple_alg_out.txt";
    private static String PARAM_ALG_OUT_FILE = "param_alg_out.txt";

    private static void sample1() {
        try {
            // Load history
            final List<Value> history = loadHistory();
            // Init default doji engine
            final Engine doji = new Engine();
            // Process history using doji engine
            doji.processHistory(history);
            // Show results
            saveHistory(history, SIMPLE_OUT_FILE);
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private static void sample2() {
        try {
            // Load history
            final List<Value> history = loadHistory();
            // Init default doji engine
            final Engine doji = new Engine();
            // Add our algorithm
            doji.add(new SimpleAlgorithm());
            // Process history using doji engine
            doji.processHistory(history);
            // Show results
            saveHistory(history, SIMPLE_ALG_OUT_FILE);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private static void sample3() {
        try {
            // Load history
            final List<Value> history = loadHistory();
            // Init parameters
            final Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("unit", "0.3volatility_average");
            parameters.put("tolerance", "0.02volatility_average");
            parameters.put("inflation", "1.1");
            // Init default doji engine
            final Engine doji = new Engine(parameters);
            // Add our algorithm
            doji.add(new SimpleAlgorithm());
            // Process history using doji engine
            doji.processHistory(history);
            // Show results
            saveHistory(history, PARAM_ALG_OUT_FILE);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Sample using default parameters
        sample1();
        // Sample using default parameters with external algorithm
        sample2();
        // Sample using overrided parameters with external algorithm
        sample3();
    }

    private static List<Value> loadHistory() throws FileNotFoundException, IOException, ParseException {
        final SimpleDateFormat format = new SimpleDateFormat(IN_DATE_FORMAT);
        final BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
        final List<Value> history = new ArrayList<Value>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            final String[] columns = line.split("\t");
            if (columns.length >= 5) {
                final Value value = new Value(format.parse(columns[0]));
                value.set("OPEN", Double.parseDouble(columns[1]));
                value.set("HIGH", Double.parseDouble(columns[2]));
                value.set("LOW", Double.parseDouble(columns[3]));
                value.set("CLOSE", Double.parseDouble(columns[4]));
                history.add(value);
            }
        }

        // Sort history by date
        Collections.sort(history);
        return history;
    }

    private static void saveHistory(final List<Value> history, final String file) throws IOException {
        final SimpleDateFormat format = new SimpleDateFormat(OUT_DATE_FORMAT);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (final Value value : history) {
            final StringBuilder builder = new StringBuilder();
            builder.append("Date: ").append(format.format(value.date())).append(", ");

            System.out.println("format.format(value.date())  = " + format.format(value.date()));

            builder.append("O: ").append(value.get("OPEN")).append(", ");
            builder.append("H: ").append(value.get("OPEN")).append(", ");
            builder.append("L: ").append(value.get("OPEN")).append(", ");
            builder.append("C: ").append(value.get("OPEN")).append(", ");
            builder.append("P: ");
            for (final Pattern pattern : (Iterable<Pattern>) value.get("PATTERNS")) {
                //Date: 2009/03/10, O: 1288.95, H: 1288.95, L: 1288.95, C: 1288.95, P: 1012;, OHLC: 5294.46
                System.out.println(" ----------> pattern.getId() = " + pattern.getId() + " - " + pattern.getName());
                builder.append(pattern.getId()).append(';');
            }
            builder.append(", OHLC: ");
            final Double ohlc = (Double) value.get("OHLC");
            if (ohlc == null)
                builder.append("null");
            else
                builder.append(ohlc);
            writer.write(builder.toString());
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
