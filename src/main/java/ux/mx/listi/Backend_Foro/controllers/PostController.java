/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.controllers;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.models.Post;
import ux.mx.listi.Backend_Foro.services.PostService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador REST para manejar las operaciones de las publicaciones del foro
 * Proporciona endpoints para crear, leer, actualizar y eliminar posts
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    /**
     * Clase interna para representar la solicitud de creación de post
     */
    public static class CreatePostRequest {
        public String titulo;
        public String descripcion;
        public String autorId;
        
        public CreatePostRequest() {}
        
        public CreatePostRequest(String titulo, String descripcion, String autorId) {
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.autorId = autorId;
        }
    }
    
    /**
     * Clase interna para representar la solicitud de actualización de post
     */
    public static class UpdatePostRequest {
        public String titulo;
        public String descripcion;
    }
    
    /**
     * Endpoint para crear una nueva publicación
     * @param request Objeto con los datos del nuevo post
     * @return La publicación creada
     */
    @PostMapping
    public ResponseEntity<?> crearPost(@RequestBody CreatePostRequest request) {
        try {
            if (request.titulo == null || request.titulo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El título es obligatorio");
            }
            if (request.descripcion == null || request.descripcion.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La descripción es obligatoria");
            }
            if (request.autorId == null || request.autorId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El ID del autor es obligatorio");
            }
            
            Post nuevoPost = postService.crearPost(
                Objects.requireNonNull(request.titulo, "El título no puede ser nulo"),
                Objects.requireNonNull(request.descripcion, "La descripción no puede ser nula"),
                Objects.requireNonNull(request.autorId, "El ID del autor no puede ser nulo")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPost);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la publicación");
        }
    }
    
    /**
     * Endpoint para obtener todas las publicaciones
     * @return Lista de todas las publicaciones ordenadas por fecha descendente
     */
    @GetMapping
    public ResponseEntity<List<Post>> obtenerTodosLosPosts() {
        try {
            List<Post> posts = postService.obtenerTodosLosPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para obtener una publicación por su ID
     * @param postId ID de la publicación
     * @return La publicación solicitada
     */
    @GetMapping("/{postId}")
    public ResponseEntity<?> obtenerPostPorId(@PathVariable @NonNull String postId) {
        try {
            Optional<Post> postOpt = postService.obtenerPostPorId(postId);
            if (postOpt.isPresent()) {
                return ResponseEntity.ok(postOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para obtener todas las publicaciones de un autor específico
     * @param autorId ID del autor
     * @return Lista de publicaciones del autor
     */
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<?> obtenerPostsPorAutor(@PathVariable @NonNull String autorId) {
        try {
            List<Post> posts = postService.obtenerPostsPorAutor(autorId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para actualizar una publicación existente
     * @param postId ID de la publicación a actualizar
     * @param request Objeto con los datos a actualizar
     * @param autorId ID del autor (debería venir del token de autenticación)
     * @return La publicación actualizada
     */
    @PutMapping("/{postId}")
    public ResponseEntity<?> actualizarPost(@PathVariable @NonNull String postId, 
                                           @RequestBody UpdatePostRequest request,
                                           @RequestHeader("X-User-Id") @NonNull String autorId) {
        try {
            Post postActualizado = postService.actualizarPost(postId, request.titulo, request.descripcion, autorId);
            return ResponseEntity.ok(postActualizado);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Solo el autor")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la publicación");
        }
    }
    
    /**
     * Endpoint para eliminar una publicación
     * @param postId ID de la publicación a eliminar
     * @param autorId ID del autor (debería venir del token de autenticación)
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> eliminarPost(@PathVariable @NonNull String postId,
                                          @RequestHeader("X-User-Id") @NonNull String autorId) {
        try {
            postService.eliminarPost(postId, autorId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Solo el autor")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la publicación");
        }
    }
    
    /**
     * Endpoint para dar like a una publicación
     * @param postId ID de la publicación
     * @param usuarioId ID del usuario que da like (debería venir del token de autenticación)
     * @return La publicación actualizada con el nuevo contador de likes
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> darLike(@PathVariable @NonNull String postId,
                                     @RequestHeader("X-User-Id") @NonNull String usuarioId) {
        try {
            Post postActualizado = postService.darLike(postId, usuarioId);
            return ResponseEntity.ok(postActualizado);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al dar like");
        }
    }
    
    /**
     * Endpoint para buscar publicaciones por título
     * @param titulo Título a buscar
     * @return Lista de publicaciones que coinciden con el título
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPostsPorTitulo(@RequestParam @NonNull String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El término de búsqueda es obligatorio");
            }
            
            List<Post> posts = postService.buscarPostsPorTitulo(titulo);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar publicaciones");
        }
    }
}
