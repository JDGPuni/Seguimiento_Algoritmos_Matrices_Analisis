package com.universidad.matrices.algorithms;

import java.util.stream.IntStream;

public class RowByColumn_III_4_ParallelBlock implements MatrixMultiplier {
    private static final int bsize = 64;

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        IntStream.iterate(0, i -> i + bsize)
                 .limit((n + bsize - 1) / bsize)
                 .parallel()
                 .forEach(ii -> {
            for (int jj = 0; jj < n; jj += bsize) {
                for (int kk = 0; kk < n; kk += bsize) {
                    for (int i = ii; i < Math.min(ii + bsize, n); i++) {
                        for (int j = jj; j < Math.min(jj + bsize, n); j++) {
                            int sum = 0;
                            for (int k = kk; k < Math.min(kk + bsize, n); k++) {
                                sum += a[i][k] * b[k][j];
                            }
                            result[i][j] += sum;
                        }
                    }
                }
            }
        });
        
        return result;
    }
}
