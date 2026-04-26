package com.universidad.matrices.util;

import com.universidad.matrices.algorithms.MatrixMultiplier;

public class BenchmarkUtil {
    
    /**
     * Mide el tiempo de ejecución en milisegundos de un algoritmo de multiplicación de matrices.
     * @param algorithm instancia del algoritmo
     * @param a matriz A
     * @param b matriz B
     * @return tiempo en ms que tomó la ejecución de multiplicar a y b
     */
    public static long measureExecutionTime(MatrixMultiplier algorithm, int[][] a, int[][] b) {
        long startTime = System.currentTimeMillis();
        algorithm.multiply(a, b);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
