# Usar imagen del profe
FROM rrojano/spring-boot

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .

# Descargar dependencias
# Se usa go-offline para que no descargue dependencias que no se usen
RUN ./mvnw dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Construir la aplicación
# Se usa clean para limpiar la carpeta target
RUN ./mvnw clean package -DskipTests

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
# Se usará target/Backend-Foro-0.0.1-SNAPSHOT.jar ya que es el nombre que se le dio al archivo jar (y basandonos en el proyecto que hicimos en clase)
CMD ["java", "-jar", "target/Backend-Foro-0.0.1-SNAPSHOT.jar"]
