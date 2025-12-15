# Arquitectura
- Spring Boot + MongoDB Atlas

# Por parte de spring
El comando ./mvnw spring-boot:run se usa para ejecutar la aplicación localmente
El comando ./mvnw clean package -DskipTests se usa para construir la aplicación

# Por parte de docker
El comando docker-compose up se usa para ejecutar la aplicación en un contenedor
Se usará la versión de spring del profe
Se construirá la aplicación mediante el dockerfile para render, ya que no tiene una versión nativa para poder correr proyectos en java
Se usará el docker-compose.yml para ejecutar la aplicación en un contenedor

docker-compose up --build // construye todo el proyecto y lo levanta

# Colecciones de DB (sujeto a cambios)


# Anotaciones X
- Se usan los @NonNull para prevenir excepciones como la que te dice de llamar un método con un parámetro o campo null, creo que era algo como ExceptionNullPointer (Se ocupa en muchas partes del código, y se implementó más que nada para no ver los mensajes de advertencia JASJDJA)
- El contexto en Spring es aquel crea y guarda los [[Beans]]. Encargandose de detectar las clases, resolver dependencias y manejar los objetos
- Los beans son los @algo arriba de una clase, por ejemplo una con @RestController, hace que spring la detecte y crea instancias de las clases
- Tomcat va por default cuando se arranca la clase usando la dependencia spring-boot-starter-web, tomcat lo que hace es que levanta
el servidor y escucha el puerto 8080 (A menos que se cambie con una propiedad de server.port en las propiedades)
- Los enum sirven para poder declarar varioos tipos de datos para una variable
- para forzar la construccion de el contenedor, haz esto:
        # Eliminar caché y reconstruir
        docker-compose down
        docker-compose build --no-cache
        docker-compose up




