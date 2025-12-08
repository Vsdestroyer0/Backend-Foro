# Usar imagen del profe
FROM rrojano/spring-boot

# Establecer el directorio de trabajo
WORKDIR /app

# Instalar Maven (ya que la imagen del profe no lo tiene)
RUN apk update && apk add maven

# Copiar el archivo pom.xml
COPY pom.xml .

# Descargar dependencias
# Se usa go-offline para que no descargue dependencias que no se usen
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Limpiar target antes de construir
RUN rm -rf target

# Construir la aplicación
# Se usa clean para limpiar la carpeta target
RUN mvn clean package -DskipTests

# Exponer el puerto
EXPOSE 8080

# Variables de entorno para MongoDB
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro?retryWrites=true&w=majority&appName=foro

# Comando para ejecutar la aplicación
# Se usará target/Backend-Foro-0.0.1-SNAPSHOT.jar ya que es el nombre que se le dio al archivo jar (y basandonos en el proyecto que hicimos en clase)
CMD ["java", "-Dspring.data.mongodb.uri=mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro?retryWrites=true&w=majority&appName=foro", "-jar", "target/Backend-Foro-0.0.1-SNAPSHOT.jar"]
