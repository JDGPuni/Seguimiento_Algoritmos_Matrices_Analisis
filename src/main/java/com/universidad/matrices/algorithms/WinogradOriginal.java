package com.universidad.matrices.algorithms;

public class WinogradOriginal implements MatrixMultiplier {

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int p = a[0].length;
        int m = b[0].length;
        int[][] result = new int[n][m];

        int d = p / 2;
        int[] rowFactors = new int[n];
        int[] colFactors = new int[m];

        // 1. Calcular precondicionadores (factores) de fila
        for (int i = 0; i < n; i++) {
            rowFactors[i] = 0;
            for (int j = 0; j < d; j++) {
                rowFactors[i] += a[i][2 * j] * a[i][2 * j + 1];
            }
        }

        // 2. Calcular precondicionadores (factores) de columna
        for (int i = 0; i < m; i++) {
            colFactors[i] = 0;
            for (int j = 0; j < d; j++) {
                colFactors[i] += b[2 * j][i] * b[2 * j + 1][i];
            }
        }

        // 3. Multiplicación real optimizando los productos punto a operaciones lineales
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int aux = -rowFactors[i] - colFactors[j];
                
                for (int k = 0; k < d; k++) {
                    aux += (a[i][2 * k] + b[2 * k + 1][j]) * (a[i][2 * k + 1] + b[2 * k][j]);
                }
                
                // Si la dimensión es impar, ajustar con el último elemento
                if (p % 2 != 0) {
                    aux += a[i][p - 1] * b[p - 1][j];
                }
                
                result[i][j] = aux;
            }
        }

        return result;
    }
}
