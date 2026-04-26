package com.universidad.matrices;

import com.universidad.matrices.algorithms.*;
import com.universidad.matrices.util.BenchmarkUtil;
import com.universidad.matrices.util.FileHandler;
import com.universidad.matrices.util.MatrixGenerator;

public class Main {
    public static void main(String[] args) {
        int[] testSizes = { 512, 1024 };

        System.out.println("--- Proyecto de Análisis de Algoritmos ---");

        MatrixMultiplier[] algorithms = {
                new NaivOnArray(),
                new NaivLoopUnrollingTwo(),
                new NaivLoopUnrollingFour(),
                new StrassenNaiv(),
                new StrassenWinograd(),
                new WinogradOriginal(),
                new WinogradScaled(),
                new RowByColumn_III_3_SequentialBlock(),
                new RowByColumn_III_4_ParallelBlock(),
                new RowByColumn_III_5_EnhancedParallelBlock(),
                new RowByRow_IV_3_SequentialBlock(),
                new RowByRow_IV_4_ParallelBlock(),
                new RowByRow_IV_5_EnhancedParallelBlock(),
                new ColumnByColumn_V_3_SequentialBlock(),
                new ColumnByColumn_V_4_ParallelBlock()
        };

        String[] algoNames = {
                "NaivOnArray", "NaivLoopUnrollingTwo", "NaivLoopUnrollingFour",
                "StrassenNaiv", "StrassenWinograd", "WinogradOriginal", "WinogradScaled",
                "RowByColumn_III_3_SequentialBlock", "RowByColumn_III_4_ParallelBlock",
                "RowByColumn_III_5_EnhancedParallelBlock",
                "RowByRow_IV_3_SequentialBlock", "RowByRow_IV_4_ParallelBlock", "RowByRow_IV_5_EnhancedParallelBlock",
                "ColumnByColumn_V_3_SequentialBlock", "ColumnByColumn_V_4_ParallelBlock"
        };

        for (int n : testSizes) {
            System.out.println("\n-------------------------------------------------------------");
            System.out.println("Preparando caso de prueba para tamaño N = " + n);

            // Generación de matrices
            int[][] matrizA = MatrixGenerator.generate(n);
            int[][] matrizB = MatrixGenerator.generate(n);

            // Persistencia
            FileHandler.saveMatrixToFile(matrizA, "matrizA_" + n + ".txt");
            FileHandler.saveMatrixToFile(matrizB, "matrizB_" + n + ".txt");
            System.out.println("Matrices generadas y persistidas en disco correctamente.");

            // Carga
            int[][] loadedMatrizA = FileHandler.loadMatrixFromFile("matrizA_" + n + ".txt");
            int[][] loadedMatrizB = FileHandler.loadMatrixFromFile("matrizB_" + n + ".txt");

            if (loadedMatrizA.length == n && loadedMatrizB.length == n) {
                System.out.println("¡Validación exitosa! Ejecutando Benchmarks...\n");

                for (int i = 0; i < algorithms.length; i++) {
                    System.out.println("Evaluando " + algoNames[i] + "...");
                    long time = BenchmarkUtil.measureExecutionTime(algorithms[i], loadedMatrizA, loadedMatrizB);
                    FileHandler.appendBenchmarkResult(algoNames[i], n, time, "Caso " + n + "x" + n);
                    System.out.println("  -> Completado en " + time + " ms (Guardado en CSV)");
                }
            } else {
                System.out.println("Error grave: No coinciden las dimensiones leídas para el caso n=" + n);
            }
        }

        System.out.println("\n=============================================================");
        System.out.println("[!] PROCESO TOTAL DE BENCHMARKING FINALIZADO CON ÉXITO.");
        System.out.println("Los resultados se encuentran en data/tiempos_ejecucion.csv");
        System.out.println("=============================================================");
    }
}
