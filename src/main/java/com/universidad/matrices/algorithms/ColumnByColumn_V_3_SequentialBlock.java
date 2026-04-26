package com.universidad.matrices.algorithms;

public class ColumnByColumn_V_3_SequentialBlock implements MatrixMultiplier {
    private static final int bsize = 64;

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int jj = 0; jj < n; jj += bsize) {
            for (int kk = 0; kk < n; kk += bsize) {
                for (int ii = 0; ii < n; ii += bsize) {
                    for (int j = jj; j < Math.min(jj + bsize, n); j++) {
                        for (int k = kk; k < Math.min(kk + bsize, n); k++) {
                            int b_kj = b[k][j];
                            for (int i = ii; i < Math.min(ii + bsize, n); i++) {
                                result[i][j] += a[i][k] * b_kj;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
