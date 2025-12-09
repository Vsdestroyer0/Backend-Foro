/* paquete */
package ux.mx.listi.Backend_Foro.services.interfaces;

/* Importaciones */
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ux.mx.listi.Backend_Foro.models.Comment;

/**
 * Repositorio para la colección de comentarios
 * Proporciona métodos CRUD y consultas personalizadas para los comentarios
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    /**
     * Busca comentarios por ID del post al que pertenecen
     * @param postId ID de la publicación
     * @return Lista de comentarios del post
     */
    List<Comment> findByPostId(String postId);
    
    /**
     * Busca comentarios por ID del autor
     * @param autorId ID del usuario que creó los comentarios
     * @return Lista de comentarios creados por el autor
     */
    List<Comment> findByAutorId(String autorId);
    
    /**
     * Busca comentarios por post y los ordena por fecha de creación ascendente
     * @param postId ID de la publicación
     * @return Lista de comentarios del post ordenados del más antiguo al más nuevo
     */
    List<Comment> findByPostIdOrderByFechaCreacionAsc(String postId);
    
    /**
     * Busca comentarios por autor y los ordena por fecha de creación descendente
     * @param autorId ID del autor
     * @return Lista de comentarios del autor ordenados del más nuevo al más antiguo
     */
    List<Comment> findByAutorIdOrderByFechaCreacionDesc(String autorId);
    
    /**
     * Cuenta cuántos comentarios tiene un post
     * @param postId ID de la publicación
     * @return Número de comentarios del post
     */
    long countByPostId(String postId);
}
