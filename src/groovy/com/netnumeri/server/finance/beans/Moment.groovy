package com.netnumeri.server.finance.beans;

import com.netnumeri.server.finance.math.NumericalRecipes;

public class Moment {
    public static class MomentResult {
        public double ave;
        public double adev;
        public double sdev;
        public double var;
        public double skew;
        public double curt;
    }

    public static MomentResult moment(double[] data, int n) throws NRError {
        int j;
        double ep = 0.0F;
        double s;
        double p;
        MomentResult res = new MomentResult();
        if (n <= 1) {
            NumericalRecipes.nrerror("n must be at least 2 in moment");
        }
        s = 0.0F;
        for (j = 1; j <= n; j++) {
            s += data[j];
        }
        res.ave = s / n;
        res.adev = (res.var) = (res.skew) = (res.curt) = 0.0F;
        for (j = 1; j <= n; j++) {
            res.adev += Math.abs(s = data[j] - (res.ave));
            ep += s;
            res.var += (p = s * s);
            res.skew += (p *= s);
            res.curt += (p *= s);
        }
        res.adev /= n;
        res.var = (res.var - ep * ep / n) / (n - 1);
        res.sdev = (double) Math.sqrt(res.var);
        if (res.var != 0) {
            res.skew /= (n * (res.var) * (res.sdev));
            res.curt = (res.curt) / (n * (res.var) * (res.var)) - 3.0F;
        } else {
            NumericalRecipes.nrerror("No skew/kurtosis when variance = 0 (in moment)");
        }
        return res;
    }
}