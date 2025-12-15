/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.services;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ux.mx.listi.Backend_Foro.models.Comment;
import ux.mx.listi.Backend_Foro.models.Post;
import ux.mx.listi.Backend_Foro.models.usuarios;
import ux.mx.listi.Backend_Foro.services.interfaces.CommentRepository;
import ux.mx.listi.Backend_Foro.services.interfaces.PostRepository;
import ux.mx.listi.Backend_Foro.services.interfaces.UsuarioRepository;

import java.util.List;
import java.util.Optional;

//Servicio que maneja la lógica de negocio para los comentarios del foro Proporciona métodos para crear, leer y eliminar comentarios
@Service
public class CommentService {
        @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Crea un nuevo comentario en una publicación
     * @param postId ID de la publicación donde se comentará
     * @param autorId ID del usuario que crea el comentario
     * @param contenido Contenido del comentario
     * @return El comentario creado
     * @throws IllegalArgumentException si el post o el autor no existen
     */
    public Comment crearComment(@NonNull String postId, @NonNull String autorId, @NonNull String contenido) {
        // Verificar que el post existe
        Optional<Post> postOpt = postRepository.findById(postId);
        if (!postOpt.isPresent()) {
            throw new IllegalArgumentException("La publicación no existe");
        }
        
        // Verificar que el autor existe
        Optional<usuarios> autorOpt = usuarioRepository.findById(autorId);
        if (!autorOpt.isPresent()) {
            throw new IllegalArgumentException("El autor no existe");
        }
        
        usuarios autor = autorOpt.get();
        Comment nuevoComment = new Comment(postId, autorId, autor.getUsername(), contenido);
        
        return commentRepository.save(nuevoComment);
    }
    
    /**
     * Obtiene todos los comentarios de una publicación ordenados por fecha ascendente
     * @param postId ID de la publicación
     * @return Lista de comentarios de la publicación
     */
    public List<Comment> obtenerCommentsPorPost(String postId) {
        return commentRepository.findByPostIdOrderByFechaCreacionAsc(postId);
    }
    
    /**
     * Obtiene un comentario por su ID
     * @param commentId ID del comentario
     * @return Optional con el comentario si existe
     */
    public Optional<Comment> obtenerCommentPorId(@NonNull String commentId) {
        return commentRepository.findById(commentId);
    }
    
    /**
     * Obtiene todos los comentarios de un autor específico
     * @param autorId ID del autor
     * @return Lista de comentarios del autor
     */
    public List<Comment> obtenerCommentsPorAutor(String autorId) {
        return commentRepository.findByAutorIdOrderByFechaCreacionDesc(autorId);
    }
    
    /**
     * Actualiza el contenido de un comentario
     * @param commentId ID del comentario a actualizar
     * @param nuevoContenido Nuevo contenido del comentario
     * @param solicitanteId ID del usuario que intenta actualizar
     * @return El comentario actualizado
     * @throws IllegalArgumentException si el comentario no existe o el solicitante no es el autor
     */
    public Comment actualizarComment(@NonNull String commentId, String nuevoContenido, @NonNull String solicitanteId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (!commentOpt.isPresent()) {
            throw new IllegalArgumentException("El comentario no existe");
        }
        
        Comment comment = commentOpt.get();
        
        // Verificar que el solicitante es el autor
        if (!comment.getAutorId().equals(solicitanteId)) {
            throw new IllegalArgumentException("Solo el autor puede editar el comentario");
        }
        
        // Actualizar contenido si se proporcionó
        if (nuevoContenido != null && !nuevoContenido.trim().isEmpty()) {
            comment.setContenido(nuevoContenido);
        }
        
        return commentRepository.save(comment);
    }
    
    /**
     * Elimina un comentario
     * @param commentId ID del comentario a eliminar
     * @param solicitanteId ID del usuario que intenta eliminar
     * @throws IllegalArgumentException si el comentario no existe o el solicitante no es el autor
     */
    public void eliminarComment(@NonNull String commentId, @NonNull String solicitanteId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (!commentOpt.isPresent()) {
            throw new IllegalArgumentException("El comentario no existe");
        }
        
        Comment comment = commentOpt.get();
        
        // Verificar que el solicitante es el autor del comentario
        if (!comment.getAutorId().equals(solicitanteId)) {
            throw new IllegalArgumentException("Solo el autor del comentario puede eliminarlo");
        }
        
        commentRepository.deleteById(commentId);
    }
    
    /**
     * Elimina un comentario (versión para administradores o autor del post)
     * @param commentId ID del comentario a eliminar
     * @param solicitanteId ID del usuario que intenta eliminar
     * @param esAutorDelPost Si el solicitante es el autor del post principal
     * @throws IllegalArgumentException si el comentario no existe o no tiene permisos
     */
    public void eliminarComment(@NonNull String commentId, @NonNull String solicitanteId, boolean esAutorDelPost) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (!commentOpt.isPresent()) {
            throw new IllegalArgumentException("El comentario no existe");
        }
        
        Comment comment = commentOpt.get();
        
        // Verificar que el solicitante es el autor del comentario o del post
        if (!comment.getAutorId().equals(solicitanteId) && !esAutorDelPost) {
            throw new IllegalArgumentException("Solo el autor del comentario o del post pueden eliminarlo");
        }
        
        commentRepository.deleteById(commentId);
    }
    
    /**
     * Cuenta cuántos comentarios tiene una publicación
     * @param postId ID de la publicación
     * @return Número de comentarios
     */
    public long contarCommentsPorPost(@NonNull String postId) {
        return commentRepository.countByPostId(postId);
    }
}
