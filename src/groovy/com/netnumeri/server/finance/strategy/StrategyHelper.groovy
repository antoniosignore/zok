package com.netnumeri.server.finance.strategy

import com.dtmc.finance.finpojo.asset.Stock
import com.dtmc.finance.finpojo.derivative.equity.Vanilla
import com.netnumeri.server.enums.OptionType

class StrategyHelper {

    public static boolean isInTheMoney(Vanilla option) {
        if (option.type == OptionType.CALL)
            if (option.strike < option.underlying().last) return true
        if (option.type == OptionType.PUT)
            if (option.strike > option.underlying().last) return true
        return false
    }

    public static boolean isOutOfTheMoney(Vanilla option) {
        if (option.type == OptionType.CALL)
            if (option.strike > option.underlying().last) return true
        if (option.type == OptionType.PUT)
            if (option.strike < option.underlying().last) return true
        return false

    }

    public static boolean isAtTheMoney(Vanilla option) {
        if (option.type == OptionType.CALL)
            if (option.strike == option.underlying().last) return true
        if (option.type == OptionType.PUT)
            if (option.strike == option.underlying().last) return true
        return false
    }

    public static List<Vanilla> getAtTheMoneyList(Stock stock, OptionType type) {

        List<Vanilla> atmList = new ArrayList<Vanilla>();

        def lastValue = stock.getLast();

        Map<Date, List<Vanilla>> optionsMap;

        if (type == OptionType.CALL)
            optionsMap = stock.chain.calls;
        else
            optionsMap = stock.chain.puts;

        Set<Date> strings = optionsMap.keySet();

        Vanilla atTheMoneyOption = null
        Double minDistance = Double.MAX_VALUE

        for (Iterator<Date> iterator = strings.iterator(); iterator.hasNext();) {
            Date next = iterator.next();
            List<Vanilla> options = optionsMap.get(next);
            for (int i = 0; i < options.size(); i++) {
                Vanilla option = options.get(i);
                def strike = option.strike();
                if (Math.abs(strike - lastValue) < minDistance) {
                    atTheMoneyOption = option;
                    minDistance = Math.abs(strike - lastValue)
                }
            }
            atmList.add(atTheMoneyOption);
            minDistance = Double.MAX_VALUE
        }
        return atmList
    }


}
