package com.netnumeri.server.finance.data

import com.dtmc.finance.finpojo.Instrument
import com.dtmc.finance.finpojo.Trade
import com.netnumeri.server.finance.beans.FinConstants
import com.netnumeri.server.finance.beans.GenericTimeSeries
import com.netnumeri.server.finance.beans.TradeEnum

public class TransactionSeries implements Serializable {

    protected GenericTimeSeries<Trade> transactionArray = new GenericTimeSeries<Trade>();

    public TransactionSeries() {
    }

    public void add(Trade transaction) {
        transactionArray.put(transaction.getTradeDate(), transaction);
    }

    public int getN() {
        return getNTransactions();
    }

    public int getNTransactions() {
        return transactionArray.size();
    }

    public Trade getTransaction(Date date) {
        return transactionArray.get(date);
    }

    public Instrument getInstrument(Date date) {
        return getTransaction(date).getInstrument();
    }

    public FinConstants getAction(Date date) {
        return getTransaction(date).getTradeAction();
    }

    public double getAmount(Date date) {
        return getTransaction(date).getAmount();
    }

    public double getPrice(Date date) {
        return getTransaction(date).getPrice();
    }

    public Date getDateTime(Date date) {
        return getTransaction(date).getTradeDate();
    }

    public Date getDate(Date date) {
        return getTransaction(date).getTradeDate();
    }

    public void clear() {
        transactionArray.clear()
    }

    public void delete() {
        transactionArray.clear()
    }

    public Trade getFirstTransaction() {
        return transactionArray.firstValue()
    }

    public Trade getLastTransaction() {
        return transactionArray.lastValue()
    }

    public Date getFirstDate() {
        return transactionArray.firstDate()
    }

    public Date getLastDate() {
        return transactionArray.lastDate()
    }

    // Return a "pair" of i-th transactions
    // Return next sell transactions for the same Instrument of buy transactions
    // Return previous buy transactions for the same Instrument of sell transactions
    // Return null if there is no pair transactions
    public Trade getPair(Date date, TradeEnum action) {

        if (action == TradeEnum.BUY) {
            Trade transaction = transactionArray.nextValue(date)
            if (transaction == null)
                return null
            else if (transaction.tradeAction == TradeEnum.SELL)
                return transaction
            else
                return getPair(transaction.tradeDate, action)
        }

        if (action == TradeEnum.SELL) {
            Trade transaction = transactionArray.prevValue(date)
            if (transaction == null)
                return null
            else if (transaction.tradeAction == TradeEnum.BUY)
                return transaction
            else
                return getPair(transaction.tradeDate, action)
        }

        if (action == TradeEnum.SELLSHORT) {
            Trade transaction = transactionArray.nextValue(date)
            if (transaction == null)
                return null
            else if (transaction.tradeAction == TradeEnum.BUYSHORT)
                return transaction
            else
                return getPair(transaction.tradeDate, action)
        }

        if (action == TradeEnum.SELLSHORT) {
            Trade transaction = transactionArray.prevValue(date)
            if (transaction == null)
                return null
            else if (transaction.tradeAction == TradeEnum.BUYSHORT)
                return transaction
            else
                return getPair(transaction.tradeDate, action)
        }
    }

    public Object clone() {
        TransactionSeries ts = new TransactionSeries();
        transactionArray.iterator();
        for (Iterator<Trade> iterator = transactionArray.iterator(); iterator.hasNext();) {
            Trade transaction = iterator.next();
            ts.transactionArray.put(transaction.getTradeDate(), transaction);
        }
        return ts;
    }
}