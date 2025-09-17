# Usa una imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Crea el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el .jar generado en target/
COPY target/*.jar app.jar

# Expone el puerto que Railway asigna en runtime
EXPOSE $PORT

# Arranca la app leyendo la variable $PORT
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
