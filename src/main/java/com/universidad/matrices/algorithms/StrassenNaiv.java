package com.universidad.matrices.algorithms;

public class StrassenNaiv implements MatrixMultiplier {

    private final NaivOnArray fallback = new NaivOnArray();

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        return strassen(a, b);
    }

    private int[][] strassen(int[][] a, int[][] b) {
        int n = a.length;
        
        // Caso base: si el tamaño de la matriz llega a ser <= 64, usamos
        // algoritmo clásico nativo para no empeorar por el overhead de la recursividad.
        if (n <= 64) {
            return fallback.multiply(a, b);
        }

        int newSize = n / 2;
        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];

        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        // Rellenar las particiones
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                a11[i][j] = a[i][j];
                a12[i][j] = a[i][j + newSize];
                a21[i][j] = a[i + newSize][j];
                a22[i][j] = a[i + newSize][j + newSize];

                b11[i][j] = b[i][j];
                b12[i][j] = b[i][j + newSize];
                b21[i][j] = b[i + newSize][j];
                b22[i][j] = b[i + newSize][j + newSize];
            }
        }

        // P1 = (A11 + A22) * (B11 + B22)
        int[][] p1 = strassen(add(a11, a22), add(b11, b22));
        // P2 = (A21 + A22) * B11
        int[][] p2 = strassen(add(a21, a22), b11);
        // P3 = A11 * (B12 - B22)
        int[][] p3 = strassen(a11, subtract(b12, b22));
        // P4 = A22 * (B21 - B11)
        int[][] p4 = strassen(a22, subtract(b21, b11));
        // P5 = (A11 + A12) * B22
        int[][] p5 = strassen(add(a11, a12), b22);
        // P6 = (A21 - A11) * (B11 + B12)
        int[][] p6 = strassen(subtract(a21, a11), add(b11, b12));
        // P7 = (A12 - A22) * (B21 + B22)
        int[][] p7 = strassen(subtract(a12, a22), add(b21, b22));

        // Combinando sub-matrices
        // C11 = P1 + P4 - P5 + P7
        int[][] c11 = add(subtract(add(p1, p4), p5), p7);
        // C12 = P3 + P5
        int[][] c12 = add(p3, p5);
        // C21 = P2 + P4
        int[][] c21 = add(p2, p4);
        // C22 = P1 - P2 + P3 + P6
        int[][] c22 = add(add(subtract(p1, p2), p3), p6);

        int[][] result = new int[n][n];

        // Ensamblando el resultado
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = c11[i][j];
                result[i][j + newSize] = c12[i][j];
                result[i + newSize][j] = c21[i][j];
                result[i + newSize][j + newSize] = c22[i][j];
            }
        }
        return result;
    }

    private int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private int[][] subtract(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }
}
