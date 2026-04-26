# Análisis Empírico de Algoritmos de Multiplicación de Matrices Grandes

## 📋 Descripción General

Este proyecto implementa y analiza **15 variaciones algorítmicas diferentes** para la multiplicación de matrices de gran tamaño. El objetivo principal es evaluar empíricamente el rendimiento de cada enfoque mediante mediciones precisas de tiempo de ejecución, comparando sus resultados teóricos con el comportamiento real en hardware moderno.

### Características Principales

- **15 Algoritmos Implementados**: Desde enfoques clásicos hasta técnicas avanzadas de paralelización
- **Medición de Alta Precisión**: Uso de `System.nanoTime()` para evitar sesgos por redondeo
- **Dataset Consistente**: Matrices de prueba persistidas en disco para garantizar condiciones idénticas en todos los algoritmos
- **Análisis Comparative**: Evaluación empírica vs. complejidad teórica ($O(n^3)$, $O(n^{2.81})$)
- **Resultados Persistidos**: Exportación de tiempos a CSV para análisis comparativo

## 🏗️ Estructura del Proyecto

```
.
├── README.md                           # Este archivo
├── generar_graficos.py                # Script para visualizar resultados
├── src/main/java/com/universidad/
│   └── matrices/
│       ├── Main.java                  # Punto de entrada del benchmark
│       ├── algorithms/                # Implementación de 15 algoritmos
│       │   ├── NaivOnArray.java
│       │   ├── NaivLoopUnrollingTwo.java
│       │   ├── NaivLoopUnrollingFour.java
│       │   ├── StrassenNaiv.java
│       │   ├── StrassenWinograd.java
│       │   ├── WinogradOriginal.java
│       │   ├── WinogradScaled.java
│       │   ├── RowByColumn_III_3_SequentialBlock.java
│       │   ├── RowByColumn_III_4_ParallelBlock.java
│       │   ├── RowByColumn_III_5_EnhancedParallelBlock.java
│       │   ├── RowByRow_IV_3_SequentialBlock.java
│       │   ├── RowByRow_IV_4_ParallelBlock.java
│       │   ├── RowByRow_IV_5_EnhancedParallelBlock.java
│       │   ├── ColumnByColumn_V_3_SequentialBlock.java
│       │   ├── ColumnByColumn_V_4_ParallelBlock.java
│       │   └── MatrixMultiplier.java  # Interfaz común
│       └── util/                      # Utilidades
│           ├── BenchmarkUtil.java
│           ├── FileHandler.java
│           └── MatrixGenerator.java
├── data/
│   └── tiempos_ejecucion.csv         # Resultados del benchmark
└── docs/
    └── Documento_Diseno.md           # Análisis detallado
```

## 📊 Algoritmos Implementados

| # | Algoritmo | Categoría | Complejidad Teórica |
|---|-----------|-----------|-------------------|
| 1 | Naiv Standard | Clásico Secuencial | $O(n^3)$ |
| 2 | Naiv On-Array | Clásico Secuencial | $O(n^3)$ |
| 3 | Naiv Loop Unrolling Two | Clásico Optimizado | $O(n^3)$ |
| 4 | Naiv Loop Unrolling Four | Clásico Optimizado | $O(n^3)$ |
| 5 | Winograd Original | Divide y Vencerás | $O(n^3)$ |
| 6 | Winograd Scaled | Divide y Vencerás | $O(n^3)$ |
| 7 | Strassen Naiv | Divide y Vencerás | $O(n^{2.81})$ |
| 8 | Strassen Winograd | Divide y Vencerás | $O(n^{2.81})$ |
| 9 | Sequential Block (Row by Column) | Optimizado por Bloques | $O(n^3)$ |
| 10 | Parallel Block (Row by Column) | Paralelizado por Bloques | $O(n^3)$ |
| 11 | Enhanced Parallel Block (Row by Column) | Paralelizado Avanzado | $O(n^3)$ |
| 12 | Sequential Block (Row by Row) | Reestructuración Lineal | $O(n^3)$ |
| 13 | Parallel Block (Row by Row) | Reestructuración Paralela | $O(n^3)$ |
| 14 | Enhanced Parallel Block (Row by Row) | Paralelización Avanzada | $O(n^3)$ |
| 15 | Parallel Block (Column by Column) | Reestructuración Paralela | $O(n^3)$ |

### Categorías de Algoritmos

#### **1. Clásicos Secuenciales**
Implementación directa del algoritmo de multiplicación de matrices basado en 3 bucles anidados. Baseline para comparación.

#### **2. Clásicos Optimizados**
Aplicación de **loop unrolling** para reducir overhead de iteración y maximizar operaciones aritméticas por ciclo de CPU.

#### **3. Divide y Vencerás**
- **Winograd**: Reducción de multiplicaciones a expensas de sumas adicionales
- **Strassen**: Reducción teórica de complejidad a $O(n^{2.81})$ mediante 7 multiplicaciones en lugar de 8

#### **4. Optimizados por Bloques**
Explotación de localidad espacial de caché mediante acceso a sub-matrices bloqueadas, mejorando cache hits.

#### **5. Paralelizados**
Uso de hilos Java (`Thread`, `ExecutorService`) para paralelizar operaciones independientes por bloques.

#### **6. Paralelización Avanzada**
Optimización adicional de paralelización considerando topología de hardware y sincronización eficiente.

## 🚀 Ejecución

### Requisitos
- **Java 8 o superior**
- **Python 3** (opcional, para generación de gráficos)

### Compilación

```bash
# Navegar al directorio del proyecto
cd src/main/java

# Compilar (desde raíz del proyecto)
javac -d bin com/universidad/matrices/**/*.java
```

### Ejecución del Benchmark

```bash
# Desde la raíz del proyecto
java -cp bin com.universidad.matrices.Main
```

### Salida Esperada

El programa genera:
- **Matrices persistidas**: `matrizA_*.txt` y `matrizB_*.txt` en la carpeta raíz
- **Resultados CSV**: `data/tiempos_ejecucion.csv` con formato:
  ```
  Algoritmo,Tamano_N,Caso,Tiempo_ms
  NaivOnArray,512,Caso 512x512,1245
  ...
  ```

### Generación de Gráficos

```bash
# Ejecutar el script Python para visualizar resultados
python generar_graficos.py
```

Esto genera gráficos comparativos en PNG:
- `grafico_tamano_1024.png`
- `grafico_tamano_2048.png`

## 🔬 Metodología de Análisis

### 1. **Generación Consistente de Datos**
- Matrices de tamaño $n \times n$ con elementos enteros aleatorios en rango [100,000, 999,999]
- Generadas una sola vez y persistidas para garantizar que todos los algoritmos operen sobre el mismo dataset
- Esto elimina sesgos por variación de datos

### 2. **Medición de Tiempo**
- Uso de `System.nanoTime()` para precisión máxima
- Se evita `System.currentTimeMillis()` que redondea a milisegundos
- Cada algoritmo se ejecuta una vez por tamaño de matriz
- Tiempo se registra en milisegundos para legibilidad

### 3. **Casos de Prueba**
- Tamaños evaluados: 512×512, 1024×1024, 2048×2048 (potencias de 2 para compatibilidad con Strassen/Winograd)
- Múltiples casos para observar escalabilidad

### 4. **Persistencia de Resultados**
- Todos los tiempos se guardan en CSV (`data/tiempos_ejecucion.csv`)
- Formato estandarizado para análisis posterior

## 📈 Resultados Esperados

### Tendencias Observables

1. **Loop Unrolling**: Mejora modesta en clásicos secuenciales (~5-15% más rápido)
2. **Divide y Vencerás**: 
   - Strassen ligeramente más rápido en matrices grandes, pero overhead recursivo significativo
   - Winograd competitivo a partir de $n \geq 512$
3. **Optimización por Bloques**: Mejora sustancial por aprovechar caché L1/L2
4. **Paralelización**: Mayor beneficio con $n > 1024$ y múltiples cores disponibles
5. **Escalabilidad**: Diferencias se acentúan con tamaño de matriz

## 📋 Detalles Técnicos

### Interfaz `MatrixMultiplier`
Todos los algoritmos implementan:
```java
public interface MatrixMultiplier {
    int[][] multiply(int[][] a, int[][] b);
}
```

### Clase `BenchmarkUtil`
Mide tiempo de ejecución con precisión de milisegundos:
```java
public static long measureExecutionTime(MatrixMultiplier algorithm, 
                                        int[][] a, int[][] b)
```

### Clase `MatrixGenerator`
Genera matrices aleatorias consistentemente con elementos en rango [100,000, 999,999]

### Clase `FileHandler`
Persiste matrices y resultados:
- `saveMatrixToFile()`: Serializa matriz a archivo
- `loadMatrixFromFile()`: Carga matriz desde archivo
- `appendBenchmarkResult()`: Registra resultado en CSV

## 📝 Uso de Resultados

Los datos en `data/tiempos_ejecucion.csv` pueden analizarse para:

1. **Comparación de Rendimiento**: Identificar algoritmos más rápidos por tamaño
2. **Escalabilidad**: Observar cómo cambia el rendimiento relativo con $n$
3. **Validación Teórica**: Verificar si la complejidad empírica concuerda con predicciones teóricas
4. **Recomendaciones**: Determinar qué algoritmo elegir según contexto y restricciones

## 🎓 Aprendizajes Clave

- **Cache es Rey**: Acceso ordenado a memoria (bloques) supera algoritmos teóricamente superiores
- **Paralelismo Condicional**: Útil solo para matrices grandes; overhead de sincronización penaliza casos pequeños
- **Trade-offs de Implementación**: Complejidad teórica no siempre predice rendimiento práctico
- **Hardware Awareness**: Optimizaciones específicas de arquitectura pueden ser decisivas

## 📄 Documentación

Para análisis detallado, consulte [docs/Documento_Diseno.md](docs/Documento_Diseno.md) que contiene:
- Análisis de complejidad asintótica completo
- Resultados empíricos con gráficos
- Conclusiones técnicas por categoría de algoritmo

## 👨‍💻 Autor

Proyecto académico de análisis empírico de algoritmos - Universidad

## 📅 Última Actualización

2026

---

**Nota**: Para reproducir exactamente los mismos resultados, asegúrese de usar máquinas con especificaciones similares y configuración Java idéntica (JVM, opciones de compilación, etc.)
