package com.netnumeri.server.finance.indicator.utils;

import com.netnumeri.server.finance.beans.TimeSeries;

import java.text.DecimalFormat;

public class CandlestickUtils {
    private TimeSeries opens;
    private TimeSeries closes;
    private TimeSeries highs;
    private TimeSeries lows;
    private TimeSeries volumes;

    public CandlestickUtils(TimeSeries opens,
                            TimeSeries closes,
                            TimeSeries highs,
                            TimeSeries lows,
                            TimeSeries volumes) {
        this.opens = opens
        this.closes = closes
        this.highs = highs
        this.lows = lows
        this.volumes = volumes
    }

    public double Close(int candle) {
        return closes.matrix.value(candle)
    }

    // Retorna a dimensao do corpo do candle
    public double CorpoCandle(int candle) {
        double CorpoCandle = 0;

        double Open = opens.getValue(candle)
        double Close = closes.getValue(candle)
        double High = highs.getValue(candle)

        if (Close > Open) {
            CorpoCandle = Math.abs(Open / Close * 100 - 100);
        } else {
            // CorpoCandle = ABS(((Close[candle] / Open[candle])*100)-100)
            CorpoCandle = Math.abs(High / Close * 100 - 100);
        }
        return CorpoCandle;
    }

    public boolean Doji(int candle) {
        boolean result = false;
        double range = Math.abs(High(candle) - Low(candle));
        result = range > Math.abs(Open(candle) - Close(candle) * 5) || Open(candle) == Close(candle);
        return result;
    }

    // Retorna a dimensao do candle
    public double getCandleSize(int candle, int type, int typeSombra) {
        double result = 0;

        double Open = opens.getValue(candle)
        double Close = closes.getValue(candle)
        double High = highs.getValue(candle)
        double Low = lows.getValue(candle)

        boolean SinalCandlePositivo = Close > Open;
        if (type == 1) {

            if (SinalCandlePositivo) {
                result = Math.abs(Close / Open * 100 - 100);
            } else {
                result = Math.abs(Open / Close * 100 - 100);
            }
        } else if (type == 2) {

            if (SinalCandlePositivo) {
                if (typeSombra == 1) {
                    result = Math.abs(High / Close * 100 - 100);
                } else if (typeSombra == 2) {
                    result = Math.abs(Open / Low * 100 - 100);
                }
            } else {
                if (typeSombra == 1) {
                    result = Math.abs(High / Open * 100 - 100);
                } else if (typeSombra == 2) {
                    result = Math.abs(Close / Low * 100 - 100);
                }
            }
        }

        if (type == 3) {
            result = Math.abs(High / Low * 100 - 100);
        }
        return result;
    }

    // Retorna a tendencia de alta, baixa e sem tendencia
    public int getTendencia(int begin, int end) {
        int result = 0;

        int QtdeTendenciaBaixa = 0;
        int QtdeTendenciaAlta = 0;

        for (int x = begin; x < end - 1; x++) {
            int resultTendenciaCandle = getTendenciaAltaBaixa(x);

            if (resultTendenciaCandle == 0) {
                QtdeTendenciaBaixa = QtdeTendenciaBaixa + 1;
            }

            if (resultTendenciaCandle == 1) {
                QtdeTendenciaAlta = QtdeTendenciaAlta + 1;
            }
        }

        boolean ExistTendencia = (QtdeTendenciaBaixa != 0) && (QtdeTendenciaAlta != 0);

        if (ExistTendencia) {
            result = -1;
        } else if (QtdeTendenciaBaixa == 0) {
            result = 1;
        } else if (QtdeTendenciaAlta == 0) {
            result = 0;
        }
        return result;
    }

    // Retorna a tendencia de alta ou de baixa
    public int getTendenciaAltaBaixa(int candleposition) {
        int result = 0;

        boolean SinalCandle1 = false;
        boolean SinalCandle2 = false;
        boolean TendenciaCandleAlta = false;
        boolean TendenciaCandleBaixa = false;
        int QtdeTendenciaBaixa = 0;
        int QtdeTendenciaAlta = 0;

        int x = candleposition;

        SinalCandle1 = Close(x) > Open(x);
        SinalCandle2 = Close(x + 1) > Open(x + 1);

        // Verificando Tendencia de Baixa dos Candles
        if (SinalCandle1 && SinalCandle2) {
            TendenciaCandleAlta = Close(x) > Close(x + 1);
            TendenciaCandleBaixa = Close(x) < Close(x + 1);
        } else if (SinalCandle1 && !SinalCandle2) {
            TendenciaCandleAlta = Close(x) > Open(x + 1) || Close(x) > Close(x + 1);
            TendenciaCandleBaixa = Close(x) < Open(x + 1) && Close(x) < Close(x + 1);
        } else if (!SinalCandle1 && SinalCandle2) {
            TendenciaCandleAlta = Open(x) > Close(x + 1) && Close(x) > Open(x + 1);
            TendenciaCandleBaixa = Open(x) < Close(x + 1) || Close(x) < Open(x + 1);
        } else if (!SinalCandle1 && !SinalCandle2) {
            TendenciaCandleAlta = Open(x) > Open(x + 1) && Close(x) > Open(x + 1);
            TendenciaCandleBaixa = Open(x) < Open(x + 1) || Close(x) < Close(x + 1);
        }

        if (TendenciaCandleBaixa) {
            QtdeTendenciaBaixa = QtdeTendenciaBaixa + 1;
        }

        if (TendenciaCandleAlta) {
            QtdeTendenciaAlta = QtdeTendenciaAlta + 1;
        }

        if (QtdeTendenciaBaixa == 0) { // QtdeTendenciaBaixa = 0 significa que a
            // tendencia � de ALTA
            result = 1;
        } else if (QtdeTendenciaAlta == 0) { // QtdeTendenciaAlta = 0 significa
            // que a tendencia � de BAIXA
            result = 0;
        }

        return result;
    }

    // Retorna o valor medio do volume
    public double getVolumeMedio(int ncandles) {

        double MediaVolume = 0;
        for (int x = 1; x < ncandles; x++) {
            MediaVolume = MediaVolume + Volume(x);
        }
        double Media = MediaVolume / ncandles;
        // MediaMin = ((MediaVolume / ncandles) / 4)*2
        return Media;// , MediaMin AS "Media Minima"
    }

    // Retorna o valor medio do volume
    public double getVolumeMedioMinimo(int ncandles) {

        double MediaVolume = 0;
        for (int x = 1; x < ncandles; x++) {
            MediaVolume = MediaVolume + Volume(x);
        }
        double MediaMin = MediaVolume / ncandles / 2.55 * 2;
        return MediaMin;
    }

    // Retorna o valor medio do volume, utilizado pelos scripts
    public double getVolumeMedioMinimo(int candleposition, int ncandles) {

        double MediaVolume = 0;
        for (int x = candleposition + 1; x < candleposition + ncandles; x++) {
            MediaVolume = MediaVolume + Volume(x);
        }
        double MediaMin = MediaVolume / ncandles / 2.55 * 2;
        return MediaMin;
    }

    public double High(int candle) {
        return highs.getValue(candle)
    }

    public int isCandleLongTrend(int lastbar, int candles) {

        int result = 0;

        double CorpoCandle1 = getCandleSize(lastbar, 1, 0);
        double SombraSup1 = getCandleSize(lastbar, 2, 1);
        double SombraInf1 = getCandleSize(lastbar, 2, 2);

        int TendenciaUltimosCandles = getTendencia(lastbar, lastbar + candles);

        boolean PrimeiroCandleBaixa = (Open(lastbar + 1) > Close(lastbar + 1)) && (CorpoCandle1 > (SombraSup1 + SombraInf1) * 1.2);

        boolean PrimeiroCandleAlta = (Open(lastbar + 1) < Close(lastbar + 1)) && (CorpoCandle1 > (SombraSup1 + SombraInf1) * 1.2);

        boolean CandleLongDownTrend = (TendenciaUltimosCandles == 0) && (PrimeiroCandleBaixa);

        boolean CandleLongUpTrend = (TendenciaUltimosCandles == 1) && (PrimeiroCandleAlta);

        if (CandleLongDownTrend) {
            result = -1;
        }

        if (CandleLongUpTrend) {
            result = 1;
        }

        return result;

    }

//	// Verifica se o candle eh qualquer tipo de martelo ou enforcado
//	public boolean isHangingManORHammer(QuoteHistory history) {
//		return isHangingManORHammer(history,-1);
//	}
//
//    public int isHangingManORHammer(int lastbar) {
//		return (isHangingManORHammer(null, lastbar))?1:0;
//	}
//
    public boolean isHangingManORHammer(int lastbar) {

        boolean result = false;
        double Open1, Close1, CorpoCandle1, SombraSup1, SombraInf1, LarguraCandle1;

        if (lastbar != -1) {
            // Hanging Man OR Hammer

            Open1 = opens.getValue(lastbar + 1)
            Close1 = closes.getValue(lastbar + 1)

            CorpoCandle1 = getCandleSize(lastbar, 1, 0);
            SombraSup1 = getCandleSize(lastbar, 2, 1);
            SombraInf1 = getCandleSize(lastbar, 2, 2);

            LarguraCandle1 = getCandleSize(lastbar, 3, 0);

        } else {
            Open1 = opens.getValue(1)
            Close1 = closes.getValue(1)

            // Hanging Man OR Hammer

            CorpoCandle1 = getCandleSize(0, 1, 0);
            SombraSup1 = getCandleSize(0, 2, 1);
            SombraInf1 = getCandleSize(0, 2, 2);

            LarguraCandle1 = getCandleSize(0, 3, 0);
        }

        double percLargCandle1 = 100 - Math.abs(CorpoCandle1 / LarguraCandle1 * 100 - 100);

        boolean HangingMan = (Open1 != Close1 && percLargCandle1 > 10
                && percLargCandle1 <= 30 && SombraInf1 > CorpoCandle1 * 2
                && SombraSup1 / (SombraInf1 + CorpoCandle1) < 0.10);

        if (HangingMan) {
            result = true;
        }

        return result;

    }

    public int size() {
        return closes.getSize();
    }

    public double Low(int candle) {
        return lows.getValue(candle)
    }

    public double Open(int candle) {
        return opens.getValue(candle)
    }

    public long Date(int candle) {
        return opens.matrix.dateByIndex(candle).getTime()
    }

    public double Volume(int candle) {
        return volumes.getValue(candle)
    }

    /*
        if(type.equals("L"))
        value = Low(x);
    if(type.equals("O"))
        value = Open(x);
    if(type.equals("C"))
        value = Close(x);

*/

    public double Peak(int lastbar, int ncandles, String type, double perc) {
        double value = 0;
        double lastValue = 0;
        double diffPerc = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (type.equals("H")) {
                diffPerc = Math.abs(((High(x) / High(x + 1)) * 100) - 100);
                if (diffPerc >= perc)
                    value = (High(x) > High(x + 1)) ? High(x) : High(x + 1);
            }
            if (type.equals("L")) {
                diffPerc = Math.abs(((Low(x) / Low(x + 1)) * 100) - 100);
                if (diffPerc >= perc)
                    value = (Low(x) > Low(x + 1)) ? Low(x) : Low(x + 1);
            }

            value = (value > lastValue) ? value : lastValue;

            lastValue = value;

        }
        return value;
    }


    public double Trough(int lastbar, int ncandles, String type, double perc) {
        double value = 0;
        double lastValue = 0;
        double diffPerc = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (type.equals("H")) {
                diffPerc = Math.abs(((High(x) / High(x + 1)) * 100) - 100);
                if (diffPerc <= perc)
                    value = (High(x) > High(x + 1)) ? High(x) : High(x + 1);
            }
            if (type.equals("L")) {
                diffPerc = Math.abs(((Low(x) / Low(x + 1)) * 100) - 100);
                if (diffPerc <= perc)
                    value = (Low(x) > Low(x + 1)) ? Low(x) : Low(x + 1);
            }

            value = (value < lastValue) ? value : lastValue;

            lastValue = value;

        }
        return value;
    }

    public double LLV(int lastbar, int ncandles) {

        if ((lastbar + ncandles) > size()) {
            return 0;
        }
        double LLV = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (LLV > Low(x)) {
                LLV = Low(x);
            }
        }
        return LLV;
    }

    public boolean isLLV(int lastbar, int ncandles) {

        if ((lastbar + ncandles) > size()) {
            return false;
        }
        double LLV = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (LLV > Low(x)) {
                LLV = Low(x);
            }
        }

        return (Low(lastbar) < LLV) ? true : false;
    }

    public double HHV(int lastbar, int ncandles) {
        if ((lastbar + ncandles) > size()) {
            return 0;
        }
        double HHV = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (HHV < High(x)) {
                HHV = High(x);
            }
        }
        return HHV;
    }

    public boolean isHHV(int lastbar, int ncandles) {
        if ((lastbar + ncandles) > size()) {
            return false;
        }
        double HHV = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            if (HHV < High(x)) {
                HHV = High(x);
            }
        }
        return (High(lastbar) > HHV) ? true : false;
    }

    // Truncate decimale
    public double trunc(double value, int dec) {
        String strDec = "0.";
        for (int x = 0; x < dec; x++)
            strDec += "0";
        DecimalFormat df = new DecimalFormat("0.000");
        String d = df.format(value);
        d = d.replaceAll(",", ".");
        Double dbl = new Double(d);
        return dbl.doubleValue();
    }

    public double Wilders(int lastbar, int ncandles) {
        double sumC = 0;
        double sumP = 0;
        for (int x = lastbar; x < lastbar + ncandles; x++) {
            sumC += Close(x);
            sumP += Close(x + 1);
        }
        double MA_curr = sumC / ncandles;
        double MA_prec = sumP / ncandles;
        return MA_curr + 1 / ncandles * (Close(lastbar) - MA_prec);
    }

}
