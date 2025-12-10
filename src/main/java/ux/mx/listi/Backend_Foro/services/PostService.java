/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.services;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ux.mx.listi.Backend_Foro.models.Post;
import ux.mx.listi.Backend_Foro.services.interfaces.PostRepository;
import ux.mx.listi.Backend_Foro.services.interfaces.UsuarioRepository;
import ux.mx.listi.Backend_Foro.models.usuarios;

import java.util.ArrayList; 
import java.lang.Math;


import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para las publicaciones del foro
 * Proporciona métodos para crear, leer, actualizar y eliminar posts
 */
@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Crea una nueva publicación
     * @param titulo Título de la publicación
     * @param descripcion Descripción/contenido de la publicación
     * @param autorId ID del usuario que crea la publicación
     * @return La publicación creada
     * @throws IllegalArgumentException si el autor no existe
     */
    public Post crearPost(@NonNull String titulo, @NonNull String descripcion, @NonNull String autorId) {
        // Verificar que el autor existe
        Optional<usuarios> autorOpt = usuarioRepository.findById(autorId);
        if (!autorOpt.isPresent()) {
            throw new IllegalArgumentException("El autor no existe");
        }
        
        usuarios autor = autorOpt.get();
        Post nuevoPost = new Post(titulo, descripcion, autorId, autor.getUsername());
        
        return postRepository.save(nuevoPost);
    }
    
    /**
     * Obtiene todas las publicaciones ordenadas por fecha descendente
     * @return Lista de todas las publicaciones
     */
    public List<Post> obtenerTodosLosPosts() {
        return postRepository.findAllByOrderByFechaCreacionDesc();
    }
    
    /**
     * Obtiene una publicación por su ID
     * @param postId ID de la publicación
     * @return Optional con la publicación si existe
     */
    public Optional<Post> obtenerPostPorId(@NonNull String postId) {
        return postRepository.findById(postId);
    }
    
    /**
     * Obtiene todas las publicaciones de un autor específico
     * @param autorId ID del autor
     * @return Lista de publicaciones del autor
     */
    public List<Post> obtenerPostsPorAutor(String autorId) {
        return postRepository.findByAutorIdOrderByFechaCreacionDesc(autorId);
    }
    
    /**
     * Actualiza una publicación existente
     * @param postId ID de la publicación a actualizar
     * @param nuevoTitulo Nuevo título (puede ser null para no cambiar)
     * @param nuevaDescripcion Nueva descripción (puede ser null para no cambiar)
     * @param solicitanteId ID del usuario que intenta actualizar
     * @return La publicación actualizada
     * @throws IllegalArgumentException si el post no existe o el solicitante no es el autor
     */
    public Post actualizarPost(@NonNull String postId, String nuevoTitulo, String nuevaDescripcion, @NonNull String solicitanteId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (!postOpt.isPresent()) {
            throw new IllegalArgumentException("La publicación no existe");
        }
        
        Post post = postOpt.get();
        
        // Verificar que el solicitante es el autor O es administrador
        Optional<usuarios> solicitanteOpt = usuarioRepository.findById(solicitanteId);
        if (!solicitanteOpt.isPresent()) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        
        usuarios solicitante = solicitanteOpt.get();
        boolean esAutor = post.getAutorId().equals(solicitanteId);
        boolean esAdmin = ux.mx.listi.Backend_Foro.enums.RoleEnum.Admin.equals(solicitante.getRoleEnum());

        
        if (!esAutor && !esAdmin) {
            throw new IllegalArgumentException("Solo el autor o un administrador pueden editar la publicación");
        }
        
        // Actualizar campos si se proporcionaron
        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {
            post.setTitulo(nuevoTitulo);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            post.setDescripcion(nuevaDescripcion);
        }
        
        return postRepository.save(post);
    }
    
    /**
     * Elimina una publicación
     * @param postId ID de la publicación a eliminar
     * @param solicitanteId ID del usuario que intenta eliminar
     * @throws IllegalArgumentException si el post no existe o el solicitante no es el autor
     */
    public void eliminarPost(@NonNull String postId, @NonNull String solicitanteId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (!postOpt.isPresent()) {
            throw new IllegalArgumentException("La publicación no existe");
        }
        
        Post post = postOpt.get();
        
        // Verificar que el solicitante es el autor O es administrador
        Optional<usuarios> solicitanteOpt = usuarioRepository.findById(solicitanteId);
        if (!solicitanteOpt.isPresent()) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        
        usuarios solicitante = solicitanteOpt.get();
        boolean esAutor = post.getAutorId().equals(solicitanteId);
        boolean esAdmin = ux.mx.listi.Backend_Foro.enums.RoleEnum.Admin.equals(solicitante.getRoleEnum());

        
        if (!esAutor && !esAdmin) {
            throw new IllegalArgumentException("Solo el autor o un administrador pueden eliminar la publicación");
        }
        
        postRepository.deleteById(postId);
    }
    
    /**
     * Da like a una publicación
     * @param postId ID de la publicación
     * @param usuarioId ID del usuario que da like
     * @return La publicación actualizada con el nuevo contador de likes
     * @throws IllegalArgumentException si el post no existe
     */
public Post darLike(@NonNull String postId, @NonNull String usuarioId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (!postOpt.isPresent()) {
            throw new IllegalArgumentException("La publicación no existe");
        }
        
        Post post = postOpt.get();
        
        //inicializar la lista por si es nula
        List<String> likesList = post.getUsuariosQueDieronLike();
        if (likesList == null) {
            likesList = new ArrayList<>();
        }
        // verificar si ya le dio like el usuario
        if (likesList.contains(usuarioId)) {
            likesList.remove(usuarioId);
            post.setLikes(Math.max(0, post.getLikes() - 1));
        } else {
            likesList.add(usuarioId);
            post.setLikes(post.getLikes() + 1);
        }
        
        post.setUsuariosQueDieronLike(likesList);
        
        return postRepository.save(post);
    }
    /**
     * Busca publicaciones por título
     * @param titulo Título a buscar
     * @return Lista de publicaciones que coinciden con el título
     */
    public List<Post> buscarPostsPorTitulo(@NonNull String titulo) {
        return postRepository.findByTituloContainingIgnoreCase(titulo);
    }
}

