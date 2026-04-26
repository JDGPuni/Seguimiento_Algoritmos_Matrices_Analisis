package com.universidad.matrices.algorithms;

public class StrassenWinograd implements MatrixMultiplier {

    private final NaivOnArray fallback = new NaivOnArray();

    @Override
    public int[][] multiply(int[][] a, int[][] b) {
        return strassenWinograd(a, b);
    }

    private int[][] strassenWinograd(int[][] a, int[][] b) {
        int n = a.length;
        
        // Caso base (recae en la multiplicación clásica para evitar excesivo overhead)
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

        // Winograd's variant of Strassen, reducing total matrix add/subs
        int[][] S1 = add(a21, a22);
        int[][] S2 = subtract(S1, a11);
        int[][] S3 = subtract(a11, a21);
        int[][] S4 = subtract(a12, S2);
        
        int[][] T1 = subtract(b12, b11);
        int[][] T2 = subtract(b22, T1);
        int[][] T3 = subtract(b22, b12);
        int[][] T4 = subtract(T2, b21);

        int[][] P1 = strassenWinograd(a11, b11);
        int[][] P2 = strassenWinograd(a12, b21);
        int[][] P3 = strassenWinograd(S4, b22);
        int[][] P4 = strassenWinograd(a22, T4);
        int[][] P5 = strassenWinograd(S1, T1);
        int[][] P6 = strassenWinograd(S2, T2);
        int[][] P7 = strassenWinograd(S3, T3);

        int[][] U1 = add(P1, P2);
        int[][] U2 = add(P1, P6);
        int[][] U3 = add(U2, P7);
        int[][] U4 = add(U2, P5);
        int[][] U5 = add(U4, P3);
        int[][] U6 = subtract(U3, P4);
        int[][] U7 = add(U3, P5);

        int[][] result = new int[n][n];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = U1[i][j];
                result[i][j + newSize] = U5[i][j];
                result[i + newSize][j] = U6[i][j];
                result[i + newSize][j + newSize] = U7[i][j];
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
