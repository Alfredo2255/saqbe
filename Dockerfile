# Etapa de build: compila la aplicación con Maven + JDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos necesarios para descargar dependencias
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Descargar dependencias offline (acelera builds posteriores)
RUN mvn -B -f pom.xml dependency:go-offline

# Copiar el código fuente y construir (omite tests para acelerar)
COPY src ./src
RUN mvn -B package -DskipTests

# Etapa de ejecución: imagen más liviana con JRE/JDK 17
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Si tu aplicación usa otra variable de entorno de puerto, ajusta aquí
EXPOSE 8080

# Comando por defecto para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]