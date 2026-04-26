package com.universidad.matrices.algorithms;

public class NaivOnArray implements MatrixMultiplier {
    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int p = a[0].length; // Asumiendo que el arreglo es de dimensiones válidas
        int m = b[0].length;
        int[][] result = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
}
