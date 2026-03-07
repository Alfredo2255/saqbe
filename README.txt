# SAQ B'E — Sistema de Inventario
=========================================

## ¿Qué necesitas tener instalado?

1. **Java 17 o superior**
   → Descarga desde: https://adoptium.net/
   → Elige: "Temurin 17 (LTS)" → Windows x64 .msi

2. **Maven** (para compilar el proyecto)
   → Descarga desde: https://maven.apache.org/download.cgi
   → Descarga "apache-maven-3.x.x-bin.zip"
   → Extrae en C:\maven
   → Agrega C:\maven\bin a la variable PATH del sistema

   ⚡ ALTERNATIVA FÁCIL: El proyecto ya incluye el wrapper "./mvnw"
      así que NO necesitas instalar Maven por separado.

---

## ¿Cómo ejecutar? (3 pasos)

### Paso 1 - Extrae el ZIP
Extrae la carpeta "saqbe" en tu escritorio o donde prefieras.

### Paso 2 - Abre una terminal
- En Windows: Presiona Win+R, escribe "cmd", Enter
- Navega hasta la carpeta: cd C:\Users\TuNombre\Desktop\saqbe

### Paso 3 - Ejecuta el proyecto
Windows:
  mvnw.cmd spring-boot:run

Mac/Linux:
  ./mvnw spring-boot:run

⏳ La primera vez descargará dependencias (necesitas internet).
   Puede tardar 2-3 minutos. Espera hasta ver:
   "Started InventarioApplication in X.XXX seconds"

---

## ¿Cómo abrir el sistema?

Abre tu navegador y ve a:
  http://localhost:8080/recursos

---

## ¿Qué puede hacer el sistema?

✅ Ver todos los productos en tarjetas
✅ Agregar nuevos productos con formulario
✅ Editar productos existentes
✅ Eliminar productos
✅ Subir fotografías de cada producto
✅ Ver cuántas unidades hay en stock
✅ Contador total de productos y stock

---

## Campos del formulario

| Campo       | Descripción                    |
|-------------|-------------------------------|
| ID          | Se genera automáticamente     |
| Nombre      | Nombre del producto           |
| Descripción | Detalle del producto          |
| Precio      | Precio en Quetzales (Q)       |
| Fotografía  | Imagen del producto (JPG/PNG) |
| Stock       | Cantidad disponible           |

---

## Problemas frecuentes

❌ Error "Java no reconocido"
   → Instala Java 17 desde adoptium.net y reinicia la terminal

❌ El puerto 8080 ya está en uso
   → Edita src/main/resources/application.properties
   → Cambia: server.port=8080 por server.port=8081

❌ La página no carga
   → Asegúrate de que el servidor esté corriendo (debes ver el mensaje "Started")
   → Verifica que vas a http://localhost:8080/recursos (no https://)

---

## Para detener el servidor
Presiona Ctrl+C en la terminal.

Los datos se guardan automáticamente en la carpeta "data/"
y las fotos en la carpeta "uploads/" — ¡no las borres!
