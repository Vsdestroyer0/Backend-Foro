/* paquete */
package ux.mx.listi.Backend_Foro.repositories;

/* Importaciones */
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ux.mx.listi.Backend_Foro.models.Comment;

/**
 * Repositorio para la colección de comentarios
 * Proporciona métodos CRUD y consultas personalizadas para los comentarios
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    // Busca comentarios por ID del post al que pertenecen
    List<Comment> findByPostId(String postId);
    
    //Busca comentarios por ID del autor
    List<Comment> findByAutorId(String autorId);
    
    //Busca comentarios por post y los ordena por fecha de creación ascendente
    List<Comment> findByPostIdOrderByFechaCreacionAsc(String postId);
    
    //Busca comentarios por autor y los ordena por fecha de creación descendente
    List<Comment> findByAutorIdOrderByFechaCreacionDesc(String autorId);
    
    //Cuenta cuántos comentarios tiene un post
    long countByPostId(String postId);
}
