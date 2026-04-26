package com.universidad.matrices.algorithms;

public class RowByRow_IV_3_SequentialBlock implements MatrixMultiplier {
    private static final int bsize = 64;

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int ii = 0; ii < n; ii += bsize) {
            for (int kk = 0; kk < n; kk += bsize) {
                for (int jj = 0; jj < n; jj += bsize) {
                    for (int i = ii; i < Math.min(ii + bsize, n); i++) {
                        for (int k = kk; k < Math.min(kk + bsize, n); k++) {
                            int a_ik = a[i][k];
                            for (int j = jj; j < Math.min(jj + bsize, n); j++) {
                                result[i][j] += a_ik * b[k][j];
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
