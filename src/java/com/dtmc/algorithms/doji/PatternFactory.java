
package com.dtmc.algorithms.doji;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

// Referenced classes of package com.dtmc.algorithms.doji:
//            Pattern, Scalar, Constraint, ConstraintLinearNegative, 
//            ConstraintLinearAbsolute, ConstraintLinearPositive

public final class PatternFactory {

    public PatternFactory() {
        _options = new HashMap();
        _currentPattern = null;
        _currentUnitScalar = null;
        _allConstraints = new ArrayList();
        _uniqueConstraints = new HashSet();
    }

    public PatternFactory(Map options) {
        _options = options;
        _currentPattern = null;
        _currentUnitScalar = null;
        _allConstraints = new ArrayList();
        _uniqueConstraints = new HashSet();
    }

    public Iterable loadPatterns()
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("patterns.xml"));
        ArrayList result = new ArrayList();
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling())
            if ("patterns".compareToIgnoreCase(node.getNodeName()) == 0)
                parsePatterns(node, result);

        if (_options.containsKey("debug") && ((String) _options.get("debug")).compareTo("true") == 0) {
            System.out.println("Constraint factory results");
            System.out.println((new StringBuilder()).append("Total constraint count: ").append(_allConstraints.size()).toString());
            System.out.println((new StringBuilder()).append("Unique constraint count: ").append(_uniqueConstraints.size()).toString());
        }
        return result;
    }

    private void parsePatterns(Node root, Collection result)
            throws ParserConfigurationException {
        for (Node patternNode = root.getFirstChild(); patternNode != null; patternNode = patternNode.getNextSibling()) {
            if ("pattern".compareToIgnoreCase(patternNode.getNodeName()) != 0)
                continue;
            _currentPattern = new Pattern(Integer.parseInt(patternNode.getAttributes().getNamedItem("id").getNodeValue()));
            _currentPattern.setForecast(patternNode.getAttributes().getNamedItem("forecast").getNodeValue());
            try {
                for (Node patternInfoNode = patternNode.getFirstChild(); patternInfoNode != null; patternInfoNode = patternInfoNode.getNextSibling()) {
                    if ("name".compareToIgnoreCase(patternInfoNode.getNodeName()) == 0) {
                        _currentPattern.setName(patternInfoNode.getTextContent());
                        continue;
                    }
                    if ("candlesticks".compareToIgnoreCase(patternInfoNode.getNodeName()) == 0)
                        parseCandelstick(patternInfoNode);
                }

                result.add(_currentPattern);
            } catch (Exception e) {

                e.printStackTrace();

                throw new ParserConfigurationException((new StringBuilder()).append("Unable to parse Pattern(").append(_currentPattern.getId()).append("). ").append(e.getMessage()).toString());
            }
        }

    }

    private void parseCandelstick(Node root) throws ParserConfigurationException {
        _currentPattern.setCount(Integer.parseInt(root.getAttributes().getNamedItem("num").getNodeValue()));
        Node unitNode = root.getAttributes().getNamedItem("unit");
        if (unitNode != null)
            _currentUnitScalar = parseUnit(_options.get("unit") != null ? (String) _options.get("unit") : unitNode.getNodeValue());
        else
            throw new ParserConfigurationException((new StringBuilder()).append("Pattern w/o unit defined. Pattern(").append(_currentPattern.getId()).append(")").toString());
        ArrayList constraints = new ArrayList();
        for (Node constraintNode = root.getFirstChild(); constraintNode != null; constraintNode = constraintNode.getNextSibling()) {
            if ("trend".compareToIgnoreCase(constraintNode.getNodeName()) == 0) {
                constraints.add(checkConstraint(parseTrend(constraintNode)));
                continue;
            }
            if ("constraint".compareToIgnoreCase(constraintNode.getNodeName()) == 0)
                constraints.add(checkConstraint(parseConstraint(constraintNode)));
        }

        _currentPattern.setConstraints(constraints);
    }

    private Constraint parseTrend(Node constraintNode) throws ParserConfigurationException {
        String curve = _options.get("trend") == null ? constraintNode.getAttributes().getNamedItem("curve").getNodeValue() : (String) _options.get("trend");
        Node daysNode = constraintNode.getAttributes().getNamedItem("days");
        int days = daysNode == null ? 10 : Integer.parseInt(daysNode.getNodeValue());
        int firstDayPos = -(_currentPattern.getCount() - 1);
        int secondDayPos = -((_currentPattern.getCount() - 1) + days);
        ArrayList scalars = new ArrayList(2);
        if (0 == "trend".compareToIgnoreCase(curve)) {
            scalars.add(new Scalar("TREND", firstDayPos, 1.0D));
            if (_options.get("inflation") != null) {
                Scalar inflation = parseTolerance((String) _options.get("inflation"));
                inflation.setValue(inflation.getValue() * -1D);
                inflation.setConst(true);
                scalars.add(inflation);
            } else {
                scalars.add(new Scalar("", firstDayPos, -0.94999999999999996D, true));
            }
        } else if (0 == "sma".compareToIgnoreCase(curve)) {
            scalars.add(new Scalar("SMA", firstDayPos, -1D));
            scalars.add(new Scalar("SMA", secondDayPos, 1.0D));
        } else {
            throw new ParserConfigurationException((new StringBuilder()).append("Not yet supported trend curve: ").append(curve).append(", on Pattern(").append(_currentPattern.getId()).append(")").toString());
        }
        String compare = constraintNode.getAttributes().getNamedItem("compare").getNodeValue();
        Class compareClass = parseCompareType("linear", compare);
        Scalar toleranceScalar = null;
        Node toleranceNode = constraintNode.getAttributes().getNamedItem("tolerance");
        if (toleranceNode != null) {
            if (toleranceNode.getNodeValue().compareTo("0") == 0)
                toleranceScalar = parseTolerance(_options.get("tolerance") == null ? toleranceNode.getNodeValue() : (String) _options.get("tolerance"));
            else
                toleranceScalar = parseTolerance(toleranceNode.getNodeValue());
        } else {
            toleranceScalar = parseTolerance("0");
        }
        Constraint result = createConstraint(compareClass, toleranceScalar, scalars);
        if (result == null)
            throw new ParserConfigurationException("Unable to create constraint");
        else
            return result;
    }

    private Constraint parseConstraint(Node constraintNode) throws ParserConfigurationException {
        String type = constraintNode.getAttributes().getNamedItem("type").getNodeValue();
        String compare = constraintNode.getAttributes().getNamedItem("compare").getNodeValue();
        Class compareClass = parseCompareType(type, compare);
        Scalar toleranceScalar = null;
        Node toleranceNode = constraintNode.getAttributes().getNamedItem("tolerance");
        if (toleranceNode != null) {
            if (toleranceNode.getNodeValue().compareTo("0") == 0)
                toleranceScalar = parseTolerance(_options.get("tolerance") == null ? toleranceNode.getNodeValue() : (String) _options.get("tolerance"));
            else
                toleranceScalar = parseTolerance(toleranceNode.getNodeValue());
        } else {
            toleranceScalar = parseTolerance("0");
        }
        ArrayList scalars = new ArrayList();
        for (Node scalarNode = constraintNode.getFirstChild(); scalarNode != null; scalarNode = scalarNode.getNextSibling()) {
            if ("const".compareToIgnoreCase(scalarNode.getNodeName()) == 0) {
                scalars.add(parseConst(scalarNode));
                continue;
            }
            if ("scalar".compareToIgnoreCase(scalarNode.getNodeName()) == 0)
                scalars.addAll(parseScalar(scalarNode));
        }

        Constraint result = createConstraint(compareClass, toleranceScalar, scalars);
        if (result == null)
            throw new ParserConfigurationException("Unable to create constraint");
        else
            return result;
    }

    private Scalar parseUnit(String unit) throws ParserConfigurationException {
        String splitted[] = splitString(unit);
        int index = getIndex();
        double value = splitted[0].isEmpty() ? 1.0D : Double.parseDouble(splitted[0]);
        String scalar = splitted[1];
        if (scalar.isEmpty())
            return new Scalar("", index, value);
        if (scalar.compareToIgnoreCase("volatility") == 0)
            return new Scalar("VOLATILITY", index, value);
        if (scalar.compareToIgnoreCase("volatility_average") == 0)
            return new Scalar("VOLATILITY_AVG", index, value);
        else
            throw new ParserConfigurationException((new StringBuilder()).append("Unable to parse unit property: ").append(unit).append(", of Pattern(").append(_currentPattern.getId()).append(")").toString());
    }

    private Scalar parseTolerance(String tolerance) throws ParserConfigurationException {
        String splitted[] = splitString(tolerance);
        int index = getIndex();
        double value = splitted[0].isEmpty() ? 0.0D : Double.parseDouble(splitted[0]);
        String type = splitted[1];
        if (type.isEmpty())
            return new Scalar("", index, value);
        if (type.compareToIgnoreCase("unit") == 0) {
            Scalar result = (Scalar) _currentUnitScalar.clone();
            result.setValue(result.getValue() * value);
            return result;
        }
        if (type.compareToIgnoreCase("volatility") == 0)
            return new Scalar("VOLATILITY", index, value);
        if (type.compareToIgnoreCase("volatility_average") == 0)
            return new Scalar("VOLATILITY_AVG", index, value);
        else
            throw new ParserConfigurationException((new StringBuilder()).append("Unable to parse tolerance property: ").append(tolerance).append(", of Pattern(").append(_currentPattern.getId()).append(")").toString());
    }

    private Scalar parseConst(Node scalarNode) throws ParserConfigurationException {
        int index = getIndex();
        double value = Double.parseDouble(scalarNode.getAttributes().getNamedItem("value").getNodeValue());
        String factor = scalarNode.getAttributes().getNamedItem("factor").getNodeValue();
        if (factor.compareToIgnoreCase("unit") == 0) {
            Scalar result = (Scalar) _currentUnitScalar.clone();
            result.setValue(result.getValue() * value);
            result.setConst(true);
            return result;
        }
        if (factor.compareToIgnoreCase("const") == 0)
            return new Scalar("", index, value, true);
        else
            throw new ParserConfigurationException((new StringBuilder()).append("Unable to parse constant of Pattern(").append(_currentPattern.getId()).append(")").toString());
    }

    private Collection parseScalar(Node scalarNode) throws ParserConfigurationException {
        int candle = Integer.parseInt(scalarNode.getAttributes().getNamedItem("candle").getNodeValue());
        int index = getIndex(candle);
        ArrayList result = new ArrayList();
        for (Node typeNode = scalarNode.getFirstChild(); typeNode != null; typeNode = typeNode.getNextSibling()) {
            String nodeName = typeNode.getNodeName();
            if (nodeName.compareTo("#text") != 0) {
                String scalarClass = nodeName.toUpperCase();
                double value = Double.parseDouble(typeNode.getAttributes().getNamedItem("value").getNodeValue());
                Scalar scalar = new Scalar(scalarClass, index, value);
                result.add(scalar);
            }
        }

        if (result.isEmpty())
            throw new ParserConfigurationException((new StringBuilder()).append("Unable to parser scalar of Pattern(").append(_currentPattern.getId()).append(")").toString());
        else
            return result;
    }

    private int getIndex() {
        return -_currentPattern.getCount() + 1;
    }

    private int getIndex(int offset) {
        if (offset < 0 || offset > _currentPattern.getCount())
            throw new IndexOutOfBoundsException("offset");
        else
            return -_currentPattern.getCount() + offset;
    }

    private Constraint checkConstraint(Constraint constraint) {
        _allConstraints.add(constraint);
        if (_uniqueConstraints.add(constraint))
            return constraint;
        for (Iterator i$ = _uniqueConstraints.iterator(); i$.hasNext(); ) {
            Constraint c = (Constraint) i$.next();
            if (c.equals(constraint))
                return c;
        }

        return null;
    }

    private Constraint createConstraint(Class compareClass, Scalar tolerance, Iterable scalars) {
        Constraint result = null;
        try {
            Constructor constructor = compareClass.getConstructor(new Class[]{
                    Scalar.class, Iterable.class
            });
            result = (Constraint) constructor.newInstance(new Object[]{
                    tolerance, scalars
            });
        } catch (Throwable ex) {

            ex.printStackTrace();
            result = null;
        }
        return result;
    }

    private static String[] splitString(String str)
    {
        String[] result = new String[2];
        result[0] = "";
        result[1] = "";

        int i = 0;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c) || c == '.')
                sb.append(c);
            else break;
        }

        if (sb.toString().length() > 0) {
            result[0] = sb.toString();
            result[1] = str.substring(i);
        }
        else{
            result[0] = "";
            result[1] = str;
        }

        System.out.println("result 0 = " + result[0]);
        System.out.println("result 1 = " + result[1]);
        return result;
    }

    private static Class parseCompareType(String type, String compare)
            throws ParserConfigurationException {
        if ("linear".compareToIgnoreCase(type) == 0) {
            if ("negative".compareToIgnoreCase(compare) == 0)
                return ConstraintLinearNegative.class;
            if ("absolute".compareToIgnoreCase(compare) == 0)
                return ConstraintLinearAbsolute.class;
            if ("positive".compareToIgnoreCase(compare) == 0)
                return ConstraintLinearPositive.class;
            else
                throw new ParserConfigurationException((new StringBuilder()).append("Unknown compare type: ").append(compare).toString());
        } else {
            throw new ParserConfigurationException((new StringBuilder()).append("Unknown type: ").append(type).toString());
        }
    }

    private static final int DAYS = 10;
    private final Map _options;
    private Pattern _currentPattern;
    private Scalar _currentUnitScalar;
    private Collection _allConstraints;
    private Set _uniqueConstraints;
}
