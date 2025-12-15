/* paquete */
package ux.mx.listi.Backend_Foro.repositories;

/* Importaciones */
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ux.mx.listi.Backend_Foro.models.Post;

/**
 * Repositorio para la colección de posts
 * Proporciona métodos CRUD y consultas personalizadas para las publicaciones
 */
public interface PostRepository extends MongoRepository<Post, String> {
    //Busca posts por ID del autor
    List<Post> findByAutorId(String autorId);
    
    //Busca posts por título (búsqueda parcial)
    List<Post> findByTituloContainingIgnoreCase(String titulo);
    
    //Obtiene todos los posts ordenados por fecha de creación descendente
    List<Post> findAllByOrderByFechaCreacionDesc();

    //Busca posts por autor y los ordena por fecha de creación descendente
    List<Post> findByAutorIdOrderByFechaCreacionDesc(String autorId);
}
