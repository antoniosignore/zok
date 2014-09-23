package com.netnumeri.server.finance.ssa

import com.netnumeri.server.finance.matrix.*


class SSAStudy {

    boolean debug = false

    Integer N;
    Integer L;
    Matrix Y
    List<Double> eigenvalueList
    double[] s

    SSAStudy(double[] s) {
        this.s = s
    }

    public List<SSAItem> analyze(int window) {

        N = s.size();
        L = window
        Y = new Matrix(N, L);

        for (int i = 0; i < N; i++) {
            for (int k = 0; k < L; k++) {
                Y.set(i, k, getVal(s, i, k));
            }
        }

        if (debug) {
            println "Trajectory matrix Y"
            Y.print(4, 7)
        }

        Matrix Ytranspose = Y.transpose()

        if (debug) {
            println "Trajectory matrix Xt"
            Ytranspose.print(4, 7)
        }

        Matrix C = Ytranspose.times(Y).times(1 / N)
        if (debug) {
            println "C = Yt * Y / N"
            C.print(4, 7)
        }

        EigenvalueDecomposition decomposition = C.eig()
        Matrix RHO = decomposition.getV();

        if (debug) {
            println "RHO eigenvectors"
            RHO.print(4, 7)
        }

        Matrix eigenvalue = decomposition.getD();
        if (debug) {
            println "eigenvalue"
            eigenvalue.print(4, 7)
        }

        eigenvalueList = new ArrayList<Double>();
        //form the set of eigenvalues​​, standing on the diagonal
        for (int i = 0; i < eigenvalue.getRowDimension(); i++) {
            for (int j = 0; j < eigenvalue.getRowDimension(); j++) {
                if (i == j) {
                    eigenvalueList.add(eigenvalue.get(i, j));
                }
            }
        }
        Comparator comparator = Collections.reverseOrder();
        Collections.sort(eigenvalueList, comparator);

        Matrix PC = Y.times(RHO)
        if (debug) {
            println "PC Matrix"
            PC.print(4, 7)
        }
//        PC'*PC/N
        Matrix ZCovariance = PC.transpose().times(PC).times(1 / N)
        if (debug) {
            println "ZCovariance PC'*PC/N"
            ZCovariance.print(4, 7)
        }

        int size = RHO.getColumnDimension();
        Matrix[] U = new Matrix[size];

        for (int j = 0; j < size; j++) {
            double[][] uVec = new double[size][1];
            for (int k = 0; k < size; k++) {
                uVec[k][0] = RHO.get(k, RHO.getColumnDimension() - j - 1);
            }
            U[j] = new Matrix(uVec);
        }

        List<SSAItem> itemList = new ArrayList<SSAItem>()
        int jj = 0;

        for (int x = PC.columnDimension - 1; x >= 0; x--) {
            SSAItem item = new SSAItem()

            double[] pc = getColumn(PC, x)
            if (debug) {
                println "eigenvector order " + x
                println pc
            }

            Matrix Z = buildPCReconstructionMatrix(pc, L)
            if (debug) {
                println "reconstructionMatrix Z"
                Z.print(4, 7)
            }

            Matrix reconstructed = Z.times(U[jj++]).times(1 / L)
            if (debug) {
                println "reconstructed"
                reconstructed.print(4, 7)
            }

            item.reconstructionMatrix = Z
            item.reconstructed = reconstructed

            itemList.add(item)
        }

        return itemList

    }

    static def Matrix buildPCReconstructionMatrix(double[] doubles, int columns) {
        Matrix Z = new Matrix(doubles.size(), columns)
        for (int j = 0; j < columns; j++)
            for (int i = 0; i < doubles.size(); i++) {
                try {
                    Z.set(i + j, j, doubles[i])
                } catch (Throwable ex) {
                }
            }
        return Z
    }

    static double[] getColumn(Matrix matrix, int column) {
        double[] result = new double[matrix.rowDimension];
        for (int i = 0; i < matrix.rowDimension; i++) {
            result[i] = matrix.get(i, column)
        }
        return result
    }

    static private double getVal(double[] s, int i, int k) {
        try {
            double x = s[i + k]
            return x
        } catch (Throwable x) {
            return 0
        }
    }

//    static Matrix getRowMatrix(Matrix from, int column) {
//        Matrix res = new Matrix(1, from.getColumnDimension())
//        for (int index = 0; index < res.getColumnDimension(); index++) {
//            res.set(0, index, from.get(column, index))
//        }
//        return res
//    }
//
//    static Matrix getColumnMatrix(Matrix from, int column) {
//        Matrix res = new Matrix(from.getRowDimension(), 1)
//        for (int index = 0; index < res.getRowDimension(); index++) {
//            res.set(index, 0, from.get(index, column))
//        }
//        return res
//    }

//    static double[] reconstructed(List<SSAItem> analyze, int i) {
//        return getColumn(analyze.get(i).reconstructed, 0)
//    }

    static double[] reconstructedAll(List<SSAItem> analyze) {
        int size = analyze.size()
        double[] ret
        for (int i = 0; i < size; i++) {
            double[] column = getColumn(analyze.get(i).reconstructed, 0)
            if (ret == null) ret = new double[column.length];
            for (int j = 0; j < column.length; j++) {
                ret[j] = ret[j] + column[j]
            }
        }
        return ret
    }

//    static double[] reconstructedN(List<SSAItem> analyze, int N) {
//        int size = analyze.size()
//        double[] ret
//        for (int i = 0; i < N; i++) {
//            double[] column = getColumn(analyze.get(i).reconstructed, 0)
//            if (ret == null) ret = new double[column.length];
//            for (int j = 0; j < column.length; j++) {
//                ret[j] = ret[j] + column[j]
//            }
//        }
//        return ret
//    }

    static double[] reconstructedGroup(List<SSAItem> analyze, List<Integer> numbers) {
        int size = analyze.size()
        double[] ret = null
        for (int i = 0; i < size; i++) {
            if (numbers.contains(i)) {
                double[] column = getColumn(analyze.get(i).reconstructed, 0)
                if (ret == null) ret = new double[column.length];
                for (int j = 0; j < column.length; j++) {
                    ret[j] = ret[j] + column[j]
                }
            }
        }
        return ret
    }

}
