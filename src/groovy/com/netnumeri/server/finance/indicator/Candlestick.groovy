package com.netnumeri.server.finance.indicator

import com.netnumeri.server.finance.beans.TimeSeries

/**
 * Lower Bollinger Band
 */
public class Candlestick {

    // todo
    public static double calculate(TimeSeries qh, Date date, int periodLength, List<String> lsCandle) {
//		double result = 0;
//		double lastResult = 0;
//		try {
//			for (int x = 0; x < lsCandle.size(); x++) {
//
//				Object obj = Class.forName(
//						"com.netnumeri.server.finance.indicator.candles." + lsCandle.get(x))
//						.newInstance();
//				((Indicator) obj).init(qh);
//				result = ((Indicator) obj).calculate();
//				if (result != 0) {
//					lastResult = result;
//					System.out.println(qh.getStrategyName() + " --> "
//							+ lsCandle.get(x) + " = " + result);
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return lastResult;
    }

}
