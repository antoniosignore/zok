package com.netnumeri.server.utils

import com.dtmc.finance.finpojo.derivative.equity.Vanilla;
import com.netnumeri.server.enums.OptionType
import com.netnumeri.server.finance.utils.NetUtils
import com.netnumeri.server.finance.utils.YahooInstantSnapshot
import com.netnumeri.server.finance.utils.YahooUtils
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.XPath

public class YahooOptions {

    static final String LOGON_SITE = "finance.yahoo.com";
    static final int LOGON_PORT = 80;

    public static Double getLastPrice(String ticker) {
        Double lastPrice = Double.NaN;
        try {
            YahooInstantSnapshot snapshot = YahooUtils.getCompanySnapshot(ticker);
            lastPrice = Double.parseDouble(snapshot.getLastPrice());
            System.out.println("lastPrice = " + lastPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return lastPrice;
    }

    public static List<Vanilla> getChain(OptionsDocuments callsNode, OptionType direction) {

        XPath xpathSelector = DocumentHelper.createXPath("/table/tr/td/table/tr");
        List nodes = xpathSelector.selectNodes(callsNode.callsDocument);

        List<Vanilla> list = new ArrayList<Vanilla>();

        for (int i = 1; i < nodes.size(); i++) {

            Element node = (Element) nodes.get(i);

            Vanilla option = new Vanilla(callsNode.ticker);

            if (direction == OptionType.CALL)
                option.type = (OptionType.CALL);
            else
                option.type = (OptionType.PUT);

            List ns = node.elements();

            Element strikeNode = (Element) ns.get(0);

//            println "strikeNode = " + strikeNode.asXML()

            Element strong = strikeNode.element("a").element("strong");
            String strike = strong.getText();
            option.strike = (Double.parseDouble(strike.replaceAll(",", "")));

            Element isinNode = (Element) ns.get(1);

//            println "isinNode = " + isinNode.asXML()

            Element a = isinNode.element("a");
            option.name = (a.getText());

            Element lastNode = (Element) ns.get(2);
//            println "lastNode = " + lastNode.asXML()

            a = lastNode.element("b")
//            println "a.getText() = " + a.getText()
            option.premium = (Double.parseDouble(a.getText()));

            Element changeNode = (Element) ns.get(3);
//            println "changeNode = " + changeNode.asXML()

            a = changeNode.element("span").element("b");
            option.change = (Double.parseDouble(a.getText()));

            Element bidNode = (Element) ns.get(4);
//            println "bidNode = " + bidNode.getText()

            if (!bidNode.getText().equals("N/A"))
                option.bid = (Double.parseDouble(bidNode.getText()));
            else
                option.bid = (Double.NaN);

            Element askNode = (Element) ns.get(5);
//            println "askNode = " + askNode.getText()
            if (!askNode.getText().equals("N/A"))
                option.ask = (Double.parseDouble(askNode.getText()));
            else
                option.ask = (Double.NaN);

            Element volumeNode = (Element) ns.get(6);
//            println "volumeNode = " + volumeNode.getText()

            if (!volumeNode.getText().equals("N/A"))
                option.contractSize = (Integer.parseInt(volumeNode.getText().replaceAll(",", "")));
            else
                option.contractSize = (-1);

            Element openInterestNode = (Element) ns.get(7);
//            println "openInterestNode = " + openInterestNode.getText()
            option.openInterest = (Integer.parseInt(openInterestNode.getText().replaceAll(",", "")));

            option.expiration = callsNode.expirationDate
            list.add(option);
        }
        return list;
    }

    public static String getOptionsDocuments(String ticker, Date date) throws Exception {

        OptionsDocuments documents = null;
        StringBuilder sb = new StringBuilder();
        String s3;

        String url = getURL(ticker, date);
        System.out.println("option url = " + url);

        InputStream is = NetUtils.openURL(url);
        s3 = NetUtils.getLineFromURL(is);

        System.out.println("is = " + s3);

        while (s3 != null) {
            if (s3 == null) {
                break;
            }
            if (s3.startsWith("<!--")) {
                break;
            }
            if (s3.contains("Call Options")) {
                return s3;
            }
            s3 = NetUtils.getLineFromURL(is);
        }
        return null;
    }

    private static String getURL(String ticker, Date date) {
        return "http://" + LOGON_SITE + ":" + LOGON_PORT + "/q/op?s=" + ticker + "&m=" + YahooUtils.mapKey(date);
    }

    public static OptionsDocuments scrape(String ticker, String htlmScreen) throws Exception {
        OptionsDocuments documents = new OptionsDocuments(ticker);
        int index = htlmScreen.indexOf("<table class=\"yfnc_datamodoutline1\"", 0);
        String s = htlmScreen.substring(index);
        int end = s.indexOf("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        String callsTable = s.substring(0, end);
        callsTable = fixHtml(callsTable);
        documents.setCallsDocument(DocumentHelper.parseText(callsTable));
        String puts = s.substring(end);
        index = puts.indexOf("<table class=\"yfnc_datamodoutline1\"", 0);
        s = puts.substring(index);
        end = s.indexOf("<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\">");
        String putsTable = s.substring(0, end);
        putsTable = fixHtml(putsTable);
        documents.setPutsDocument(DocumentHelper.parseText(putsTable));
        return documents;
    }

    private static String fixHtml(String callsTable) {

        callsTable = callsTable.replaceAll("alt=\"Up\">", "/>");
        callsTable = callsTable.replaceAll("alt=\"Down\">", "/>");

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("nowrap");
        strings.add("class=\"yfnc_tabledata1\"");
        strings.add("align=\"right\"");
        strings.add("align=\"left\"");
        strings.add("class=\"yfnc_h\"");
        strings.add("style=\"color:#000000;\"");

        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            callsTable = callsTable.replaceAll(s, "");
        }

        return callsTable;
    }

    public static OptionsChain loadOptionChain(String ticker) {
        OptionsChain optionChain = new OptionsChain();

        Date date = new Date();
        getOptions(ticker, optionChain, date);

        date = YahooUtils.getNextMonth(date);

        while (true) {
            getOptions(ticker, optionChain, date);
            if (optionChain.calls.get(date).size() == 0 &&
                    optionChain.puts.get(date).size() == 0) break;
            date = YahooUtils.getNextMonth(date);
        }
        return optionChain;
    }

    private static void getOptions(String ticker, OptionsChain optionChain, Date date) throws Exception {
        String s = getOptionsDocuments(ticker, date);
        OptionsDocuments optionsDocuments = scrape(ticker, s);
        optionsDocuments.setExpirationDate(date)
        List<Vanilla> callsOptions = getChain(optionsDocuments, OptionType.CALL);
        List<Vanilla> putsOptions = getChain(optionsDocuments, OptionType.PUT);
        optionChain.calls.put(date, callsOptions);
        optionChain.puts.put(date, putsOptions);
    }

}

