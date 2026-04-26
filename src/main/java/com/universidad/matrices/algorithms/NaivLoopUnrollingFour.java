package com.universidad.matrices.algorithms;

public class NaivLoopUnrollingFour implements MatrixMultiplier {
    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int p = a[0].length;
        int m = b[0].length;
        int[][] result = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int sum = 0;
                int k = 0;
                // Loop unrolling 4x en el bucle más interno
                for (; k <= p - 4; k += 4) {
                    sum += a[i][k] * b[k][j] 
                         + a[i][k + 1] * b[k + 1][j]
                         + a[i][k + 2] * b[k + 2][j]
                         + a[i][k + 3] * b[k + 3][j];
                }
                // Proceso de los residuos si p no es múltiplo de 4
                for (; k < p; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
}
