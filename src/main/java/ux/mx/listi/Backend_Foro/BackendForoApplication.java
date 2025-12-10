package ux.mx.listi.Backend_Foro;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esto marcará el punto de inicio de mi app, además de la autoconfiguración de spring
@SpringBootApplication
public class BackendForoApplication {

	// Esto de una vez arranca el contexto de la app y el servidor embebido
	public static void main(String[] args) {
		SpringApplication.run(BackendForoApplication.class, args);
	}

@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*") // permite acceso desde cualquier URL 
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
        }
    };
}

}

// El contexto en Spring es aquel crea y guarda los [[Beans]]. Encargandose de detectar las clases, resolver dependencias y manejar los objetos
// Los beans son los @algo arriba de una clase, por ejemplo una con @RestController, hace que spring la detecte y crea instancias de las clases
/* Tomcat va por default cuando se arranca la clase usando la dependencia spring-boot-starter-web, tomcat lo que hace es que levanta
    el servidor y escucha el puerto 8080 (A menos que se cambie con una propiedad de server.port en las propiedades)*/
