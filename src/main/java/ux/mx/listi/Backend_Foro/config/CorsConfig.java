/* Paquete */
package ux.mx.listi.Backend_Foro.config;

/* Importaciones */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* Esto es una configuración global de CORS (Cross-Origin Resource Sharing), esto lo que hace es poder recibir peticiones http de 
diferentes lugares (origins), es decir, que pueda recibir peticiones de diferentes sitios web, por ejemplo del localhost o netlify, vercel creo, etc */
@Configuration
// Aquí se implementa WebMvcConfigurer para poder configurar CORS, es decir, para poder configurar las peticiones http
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:4321",
                    "http://localhost:3000",
                    "https://front-foro-production-d071.up.railway.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-User-Id")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
