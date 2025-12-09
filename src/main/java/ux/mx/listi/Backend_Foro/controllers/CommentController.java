/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.controllers;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.models.Comment;
import ux.mx.listi.Backend_Foro.models.Post;
import ux.mx.listi.Backend_Foro.services.CommentService;
import ux.mx.listi.Backend_Foro.services.PostService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para manejar las operaciones de los comentarios del foro
 * Proporciona endpoints para crear, leer, actualizar y eliminar comentarios
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private PostService postService;
    
    /**
     * Clase interna para representar la solicitud de creación de comentario
     */
    public static class CreateCommentRequest {
        public String postId;
        public String autorId;
        public String contenido;
    }
    
    /**
     * Clase interna para representar la solicitud de actualización de comentario
     */
    public static class UpdateCommentRequest {
        public String contenido;
    }
    
    /**
     * Endpoint para crear un nuevo comentario
     * @param request Objeto con los datos del nuevo comentario
     * @return El comentario creado
     */
    @PostMapping
    public ResponseEntity<?> crearComment(@RequestBody CreateCommentRequest request) {
        try {
            if (request.postId == null || request.postId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El ID del post es obligatorio");
            }
            if (request.autorId == null || request.autorId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El ID del autor es obligatorio");
            }
            if (request.contenido == null || request.contenido.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El contenido del comentario es obligatorio");
            }
            
            Comment nuevoComment = commentService.crearComment(
                request.postId != null ? request.postId : "",
                request.autorId != null ? request.autorId : "", 
                request.contenido != null ? request.contenido : ""
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComment);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el comentario");
        }
    }
    
    /**
     * Endpoint para obtener todos los comentarios de una publicación
     * @param postId ID de la publicación
     * @return Lista de comentarios de la publicación ordenados por fecha ascendente
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> obtenerCommentsPorPost(@PathVariable @NonNull String postId) {
        try {
            List<Comment> comments = commentService.obtenerCommentsPorPost(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para obtener un comentario por su ID
     * @param commentId ID del comentario
     * @return El comentario solicitado
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<?> obtenerCommentPorId(@PathVariable @NonNull String commentId) {
        try {
            Optional<Comment> commentOpt = commentService.obtenerCommentPorId(commentId);
            if (commentOpt.isPresent()) {
                return ResponseEntity.ok(commentOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para obtener todos los comentarios de un autor específico
     * @param autorId ID del autor
     * @return Lista de comentarios del autor
     */
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<?> obtenerCommentsPorAutor(@PathVariable @NonNull String autorId) {
        try {
            List<Comment> comments = commentService.obtenerCommentsPorAutor(autorId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para actualizar un comentario existente
     * @param commentId ID del comentario a actualizar
     * @param request Objeto con los datos a actualizar
     * @param autorId ID del autor (debería venir del token de autenticación)
     * @return El comentario actualizado
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> actualizarComment(@PathVariable @NonNull String commentId, 
                                              @RequestBody UpdateCommentRequest request,
                                              @RequestHeader("X-User-Id") @NonNull String autorId) {
        try {
            Comment commentActualizado = commentService.actualizarComment(commentId, request.contenido, autorId);
            return ResponseEntity.ok(commentActualizado);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Solo el autor")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el comentario");
        }
    }
    
    /**
     * Endpoint para eliminar un comentario (solo el autor del comentario)
     * @param commentId ID del comentario a eliminar
     * @param autorId ID del autor (debería venir del token de autenticación)
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> eliminarComment(@PathVariable @NonNull String commentId,
                                           @RequestHeader("X-User-Id") @NonNull String autorId) {
        try {
            commentService.eliminarComment(commentId, autorId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Solo el autor")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el comentario");
        }
    }
    
    /**
     * Endpoint para eliminar un comentario (autor del post o autor del comentario)
     * @param commentId ID del comentario a eliminar
     * @param autorId ID del usuario que intenta eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{commentId}/con-permisos")
    public ResponseEntity<?> eliminarCommentConPermisos(@PathVariable @NonNull String commentId,
                                                       @RequestHeader("X-User-Id") @NonNull String autorId) {
        try {
            // Obtener el comentario para verificar el postId
            Optional<Comment> commentOpt = commentService.obtenerCommentPorId(commentId);
            if (!commentOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Comment comment = commentOpt.get();
            
            // Verificar si el solicitante es el autor del post
            boolean esAutorDelPost = false;
            try {
                Optional<Post> postOpt = postService.obtenerPostPorId(comment.getPostId());
                if (postOpt.isPresent() && postOpt.get().getAutorId().equals(autorId)) {
                    esAutorDelPost = true;
                }
            } catch (Exception e) {
                // Si hay error al obtener el post, continuamos sin permisos de autor del post
            }
            
            commentService.eliminarComment(commentId, autorId, esAutorDelPost);
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Solo el autor")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el comentario");
        }
    }
    
    /**
     * Endpoint para contar los comentarios de una publicación
     * @param postId ID de la publicación
     * @return Número de comentarios de la publicación
     */
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> contarCommentsPorPost(@PathVariable @NonNull String postId) {
        try {
            long count = commentService.contarCommentsPorPost(postId);
            Map<String, Long> response = Map.of("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
