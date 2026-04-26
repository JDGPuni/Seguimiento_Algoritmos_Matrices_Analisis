package com.universidad.matrices.algorithms;

public class WinogradScaled implements MatrixMultiplier {

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int p = a[0].length;
        int m = b[0].length;
        
        // Simulación de preacondicionamiento utilizando factores de escala en potencias de 2.
        // Simularemos este paso realizando desplazamientos de bits << 1 y >> 1 lógicos.
        int[][] aScaled = new int[n][p];
        int[][] bScaled = new int[p][m];
        
        // Escalando (<< 1 sería potencias de 2; usamos 1 empíricamente como factor)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                aScaled[i][j] = a[i][j] << 1;     // Equivalente A * 2
            }
        }
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < m; j++) {
                bScaled[i][j] = b[i][j] >> 1;     // Equivalente B / 2
            }
        }

        int[][] result = new int[n][m];

        int d = p / 2;
        int[] rowFactors = new int[n];
        int[] colFactors = new int[m];

        for (int i = 0; i < n; i++) {
            rowFactors[i] = 0;
            for (int k = 0; k < d; k++) {
                rowFactors[i] += aScaled[i][2 * k] * aScaled[i][2 * k + 1];
            }
        }

        for (int j = 0; j < m; j++) {
            colFactors[j] = 0;
            for (int k = 0; k < d; k++) {
                colFactors[j] += bScaled[2 * k][j] * bScaled[2 * k + 1][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int aux = -rowFactors[i] - colFactors[j];
                for (int k = 0; k < d; k++) {
                    aux += (aScaled[i][2 * k] + bScaled[2 * k + 1][j]) * (aScaled[i][2 * k + 1] + bScaled[2 * k][j]);
                }
                
                if (p % 2 != 0) {
                    aux += aScaled[i][p - 1] * bScaled[p - 1][j];
                }
                
                result[i][j] = aux;
            }
        }

        return result;
    }
}
