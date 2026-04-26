package com.universidad.matrices.util;

import java.util.Random;

public class MatrixGenerator {
    
    /**
     * Genera una matriz de n x n con números aleatorios de 6 dígitos.
     * @param n tamaño de la matriz (asumido como potencia de 2)
     * @return matriz bidimensional generada
     */
    public static int[][] generate(int n) {
        int[][] matrix = new int[n][n];
        Random random = new Random();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Genera un número aleatorio entre 100000 y 999999
                matrix[i][j] = 100000 + random.nextInt(900000);
            }
        }
        
        return matrix;
    }
}
