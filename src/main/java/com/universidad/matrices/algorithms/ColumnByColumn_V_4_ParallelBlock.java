package com.universidad.matrices.algorithms;

import java.util.stream.IntStream;

public class ColumnByColumn_V_4_ParallelBlock implements MatrixMultiplier {
    private static final int bsize = 64;

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        IntStream.iterate(0, j -> j + bsize)
                 .limit((n + bsize - 1) / bsize)
                 .parallel()
                 .forEach(jj -> {
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
        });
        
        return result;
    }
}
