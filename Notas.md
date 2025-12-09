# Por parte de spring
El comando ./mvnw spring-boot:run se usa para ejecutar la aplicación localmente
El comando ./mvnw clean package -DskipTests se usa para construir la aplicación

# Por parte de docker
El comando docker-compose up se usa para ejecutar la aplicación en un contenedor
Se usará la versión de spring del profe
Se construirá la aplicación mediante el dockerfile para render, ya que no tiene una versión nativa para poder correr proyectos en java
Se usará el docker-compose.yml para ejecutar la aplicación en un contenedor

Guardar mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro?retryWrites=true&w=majority&appName=foro

# Comandos

## ./mvnw clean package
- Limpia la carpeta `target` y recompila todo el proyecto.
- Ejecuta los tests y genera el JAR en `target/Backend-Foro-0.0.1-SNAPSHOT.jar`.

## ./mvnw spring-boot:run
- Arranca la aplicación Spring Boot en modo desarrollo.
- Usa la configuración de `application.properties` y expone el backend en `http://localhost:8080`

