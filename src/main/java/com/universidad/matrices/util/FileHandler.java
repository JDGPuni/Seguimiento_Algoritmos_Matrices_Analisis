package com.universidad.matrices.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DATA_DIR = "data/";

    /**
     * Guarda la matriz en un archivo de texto en la carpeta data/
     * @param matrix la matriz bidimensional a guardar
     * @param filename el nombre del archivo
     */
    public static void saveMatrixToFile(int[][] matrix, String filename) {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(String.valueOf(matrix[i][j]));
                    if (j < matrix[i].length - 1) {
                        writer.write("\t"); // Separador por tabulación
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo de texto y reconstruye la matriz
     * @param filename el nombre del archivo a cargar
     * @return la matriz reconstruida, o un arreglo vacío si hay un error
     */
    public static int[][] loadMatrixFromFile(String filename) {
        File file = new File(DATA_DIR, filename);
        List<int[]> rowsList = new ArrayList<>();

        if (!file.exists()) {
            System.err.println("El archivo no existe: " + file.getAbsolutePath());
            return new int[0][0];
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] values = line.split("\t");
                int[] row = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Integer.parseInt(values[i].trim());
                }
                rowsList.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new int[0][0];
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato de los números en el archivo: " + e.getMessage());
            return new int[0][0];
        }

        if (rowsList.isEmpty()) {
            return new int[0][0];
        }

        // Convertir la lista a una matriz primitiva int[][]
        int rows = rowsList.size();
        int cols = rowsList.get(0).length;
        int[][] matrix = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            matrix[i] = rowsList.get(i);
        }

        return matrix;
    }

    /**
     * Guarda el resultado del benchmark en un CSV en modo append
     * @param algorithmName nombre del algoritmo ejecutado
     * @param n tamaño de la matriz (N x N)
     * @param timeInMillis tiempo de ejecución en milisegundos
     * @param caseName nombre del caso de prueba
     */
    public static void appendBenchmarkResult(String algorithmName, int n, long timeInMillis, String caseName) {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "tiempos_ejecucion.csv");
        boolean isNewFile = !file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                // Escribir cabecera
                writer.write("Algoritmo,Tamano_N,Caso,Tiempo_ms");
                writer.newLine();
            }
            // Escribir línea de datos
            writer.write(String.format("%s,%d,%s,%d", algorithmName, n, caseName, timeInMillis));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el resultado de benchmark: " + e.getMessage());
        }
    }
}
