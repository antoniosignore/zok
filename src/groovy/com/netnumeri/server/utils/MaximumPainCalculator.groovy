package com.netnumeri.server.utils

import com.dtmc.finance.finpojo.derivative.equity.Vanilla;
import com.netnumeri.server.enums.OptionType
import com.netnumeri.server.finance.data.MaximumPainBean

public class MaximumPainCalculator {

    public static Double calculate(String ticker, Date date) throws Exception {

        OptionsDocuments optionsDocuments = YahooOptions.scrape(ticker, YahooOptions.getOptionsDocuments(ticker, date));
        optionsDocuments.setExpirationDate(date)

        List<Vanilla> callsOptions = YahooOptions.getChain(optionsDocuments, OptionType.CALL);
        List<Vanilla> putsOptions = YahooOptions.getChain(optionsDocuments, OptionType.PUT);

        double lastPrice = YahooOptions.getLastPrice(ticker);

        List<MaximumPainBean> callBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < callsOptions.size(); i++) {
            Vanilla option = callsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(option.strike, callsOptions);
            bean.setStrike(option.strike);
            bean.setCumulative(cumul);
            callBeans.add(bean);
        }

        Map map = new HashMap();
        for (int i = 0; i < callBeans.size(); i++) {
            MaximumPainBean maximumPainBean = callBeans.get(i);
            map.put(maximumPainBean.getStrike(), maximumPainBean.getCumulative());
        }

        List<MaximumPainBean> putsBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < putsOptions.size(); i++) {
            Vanilla option = putsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(option.strike, putsOptions);
            bean.setStrike(option.strike);
            bean.setCumulative(cumul);
            putsBeans.add(bean);
        }

        Double maxPainCumulative = Double.MAX_VALUE;
        Double maxPainStrike = Double.MAX_VALUE;
        for (int i = 0; i < putsBeans.size(); i++) {
            MaximumPainBean maximumPainBean = putsBeans.get(i);

            if (map.get(maximumPainBean.getStrike()) != null) {
                Double d = (Double) map.get(maximumPainBean.getStrike());
                Double d2 = d + maximumPainBean.getCumulative();

                if (d2 < maxPainCumulative) {
                    maxPainCumulative = d2;
                    maxPainStrike = maximumPainBean.getStrike();
                }
            }
        }
        System.out.println("last price for " + ticker + " : " + lastPrice);
        System.out.println("maxPainStrike for " + ticker + " : " + maxPainStrike);
        return maxPainStrike;
    }

    private static double computeCumulativeValue(double underSpotPrice, List<Vanilla> options) {
        double cumulativeValue = 0;
        if (options != null)
            for (int i = 0; i < options.size(); i++) {
                Vanilla option = options.get(i);
                int openInt = option.openInterest;
                if (option.type == (OptionType.CALL)) {
                    if (underSpotPrice > option.strike()) {
                        double value = underSpotPrice - option.strike;
                        cumulativeValue = cumulativeValue + (value * openInt);
                    }
                } else if (option.type.equals(OptionType.PUT)) {
                    if (underSpotPrice < option.strike()) {
                        double value = option.strike - underSpotPrice;
                        cumulativeValue = cumulativeValue + (value * openInt);
                    }
                }
            }
        return cumulativeValue;
    }

}
