/* paquete */
package ux.mx.listi.Backend_Foro.services.interfaces;

/* Importaciones */
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ux.mx.listi.Backend_Foro.models.Post;

/**
 * Repositorio para la colección de posts
 * Proporciona métodos CRUD y consultas personalizadas para las publicaciones
 */
public interface PostRepository extends MongoRepository<Post, String> {
    /**
     * Busca posts por ID del autor
     * @param autorId ID del usuario que creó los posts
     * @return Lista de posts creados por el autor
     */
    List<Post> findByAutorId(String autorId);
    
    /**
     * Busca posts por título (búsqueda parcial)
     * @param titulo Título o parte del título a buscar
     * @return Lista de posts que coinciden con el título
     */
    List<Post> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Obtiene todos los posts ordenados por fecha de creación descendente
     * @return Lista de posts del más nuevo al más antiguo
     */
    List<Post> findAllByOrderByFechaCreacionDesc();
    
    /**
     * Busca posts por autor y los ordena por fecha de creación descendente
     * @param autorId ID del autor
     * @return Lista de posts del autor ordenados del más nuevo al más antiguo
     */
    List<Post> findByAutorIdOrderByFechaCreacionDesc(String autorId);
}
