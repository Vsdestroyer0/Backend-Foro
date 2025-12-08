# Por parte de spring
El comando ./mvnw spring-boot:run se usa para ejecutar la aplicación localmente
El comando ./mvnw clean package -DskipTests se usa para construir la aplicación

# Por parte de docker
El comando docker-compose up se usa para ejecutar la aplicación en un contenedor
Se usará la versión de spring del profe
Se construirá la aplicación mediante el dockerfile para render, ya que no tiene una versión nativa para poder correr proyectos en java
Se usará el docker-compose.yml para ejecutar la aplicación en un contenedor

Guardar mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro?retryWrites=true&w=majority&appName=foro


# Dirección de la base de datos
# spring.data.mongodb.uri=mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro?ssl=true&retryWrites=true&w=majority&appName=foro
# Prueba
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
#Puerto del servidor
server.port=8080



