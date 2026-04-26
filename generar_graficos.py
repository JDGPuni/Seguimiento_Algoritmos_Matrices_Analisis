import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# Configuración del estilo profesional para las gráficas
sns.set_theme(style="whitegrid")

def generar_graficos():
    # Rutas de archivos (asumiendo ejecución desde la raíz del proyecto)
    input_file = os.path.join("data", "tiempos_ejecucion.csv")
    output_dir = "docs"
    
    # Crear directorio docs/ si no existe
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
        
    # Verificar que exista el archivo de datos antes de continuar
    if not os.path.exists(input_file):
        print(f"Error: No se encontró el archivo de datos en la ruta esperada: {input_file}")
        print("Asegúrese de ejecutar primero el motor de pruebas en Java para que se genere el archivo.")
        return
        
    try:
        # Leer el CSV
        # Asumiendo las columnas: Algoritmo, Tamano_N, Tiempo_ms (o similares)
        df = pd.read_csv(input_file)
        
        # Limpieza básica de nombres de columnas por si acortaron nombres (strip)
        df.columns = df.columns.str.strip()
        
        # Detectamos la columna que se usaría para el tamaño de matriz (Tamano_N) y el tiempo
        col_algoritmo = next((col for col in df.columns if 'algoritmo' in col.lower()), 'Algoritmo')
        col_tamano = next((col for col in df.columns if 'tama' in col.lower() or 'tamano' in col.lower() or 'n' == col.lower()), 'Tamano_N')
        col_tiempo = next((col for col in df.columns if 'tiempo' in col.lower() or 'ms' in col.lower()), 'Tiempo_ms')
        
        # Obtener los diferentes tamaños de matriz (Casos de prueba)
        tamanos = sorted(df[col_tamano].unique())
        
        for i, tam in enumerate(tamanos, start=1):
            # Filtrar el dataframe por tamaño de matriz
            df_tam = df[df[col_tamano] == tam]
            
            # Ordenar por tiempo de ejecución para mejor visualización (opcional)
            df_tam = df_tam.sort_values(by=col_tiempo, ascending=False)
            
            # Crear la figura (resolución profesional y tamaño generoso)
            plt.figure(figsize=(14, 8))
            
            # Generar gráfico de barras
            ax = sns.barplot(
                data=df_tam, 
                x=col_algoritmo, 
                y=col_tiempo, 
                palette='viridis' # Paleta de colores atractiva y accesible
            )
            
            # Configurar títulos y etiquetas
            plt.title(f'Tiempos de Ejecución de Algoritmos - Matriz {tam}x{tam} (Caso {i})', fontsize=16, fontweight='bold', pad=20)
            plt.xlabel('Algoritmo de Multiplicación', fontsize=12, labelpad=15)
            plt.ylabel('Tiempo de Ejecución (ms)', fontsize=12, labelpad=15)
            
            # Rotar las etiquetas del eje X para garantizar su legibilidad con los 15 nombres
            plt.xticks(rotation=45, ha='right', fontsize=10)
            
            # Ajustar el diseño para que no se corten las etiquetas rotadas
            plt.tight_layout()
            
            # Guardar la imagen en formato PNG dentro de docs/
            output_path = os.path.join(output_dir, f'grafico_tamano_{tam}.png')
            plt.savefig(output_path, dpi=300, bbox_inches='tight')
            print(f"✅ Gráfico generado y exportado exitosamente a: {output_path}")
            
            # Cerrar la figura para liberar memoria antes de la siguiente iteración
            plt.close()
            
    except Exception as e:
        print(f"Ocurrió un error inesperado durante la generación de las gráficas: {e}")

if __name__ == "__main__":
    generar_graficos()
