package com.universidad.matrices.algorithms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RowByColumn_III_5_EnhancedParallelBlock implements MatrixMultiplier {
    private static final int bsize = 64;

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        ExecutorService executor = Executors.newFixedThreadPool(2);
        int half = n / 2;

        executor.submit(() -> computeHalf(a, b, result, 0, half, n));
        executor.submit(() -> computeHalf(a, b, result, half, n, n));

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return result;
    }

    private void computeHalf(int[][] a, int[][] b, int[][] result, int startRow, int endRow, int n) {
        for (int ii = startRow; ii < endRow; ii += bsize) {
            for (int jj = 0; jj < n; jj += bsize) {
                for (int kk = 0; kk < n; kk += bsize) {
                    for (int i = ii; i < Math.min(ii + bsize, endRow); i++) {
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
        }
    }
}
