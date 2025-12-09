/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.middleware;

import ux.mx.listi.Backend_Foro.models.Post;
import ux.mx.listi.Backend_Foro.models.Comment;
import ux.mx.listi.Backend_Foro.enums.RoleEnum;

/**
 * Middleware para manejar la autorización en el sistema de foros
 * Proporciona métodos para verificar permisos de edición y eliminación
 */
public class AuthorizationMiddleware {
    
    /**
     * Verifica si un usuario puede editar un post
     * @param post Post a verificar
     * @param usuarioId ID del usuario que intenta editar
     * @param rolUsuario Rol del usuario que intenta editar
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeEditarPost(Post post, String usuarioId, RoleEnum rolUsuario) {
        if (post == null) {
            return "El post no existe";
        }
        
        // Solo el autor o un administrador pueden editar
        if (!post.getAutorId().equals(usuarioId) && rolUsuario != RoleEnum.Admin) {
            return "Solo el autor del post o un administrador pueden editar esta publicación";
        }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede eliminar un post
     * @param post Post a verificar
     * @param usuarioId ID del usuario que intenta eliminar
     * @param rolUsuario Rol del usuario que intenta eliminar
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeEliminarPost(Post post, String usuarioId, RoleEnum rolUsuario) {
        if (post == null) {
            return "El post no existe";
        }
        
        // Solo el autor o un administrador pueden eliminar
        if (!post.getAutorId().equals(usuarioId) && rolUsuario != RoleEnum.Admin) {
            return "Solo el autor del post o un administrador pueden eliminar esta publicación";
        }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede editar un comentario
     * @param comment Comentario a verificar
     * @param usuarioId ID del usuario que intenta editar
     * @param rolUsuario Rol del usuario que intenta editar
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeEditarComment(Comment comment, String usuarioId, RoleEnum rolUsuario) {
        if (comment == null) {
            return "El comentario no existe";
        }
        
        // Solo el autor o un administrador pueden editar
        if (!comment.getAutorId().equals(usuarioId) && rolUsuario != RoleEnum.Admin) {
            return "Solo el autor del comentario o un administrador pueden editar este comentario";
        }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede eliminar un comentario
     * @param comment Comentario a verificar
     * @param usuarioId ID del usuario que intenta eliminar
     * @param rolUsuario Rol del usuario que intenta eliminar
     * @param esAutorDelPost Si el usuario es el autor del post principal
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeEliminarComment(Comment comment, String usuarioId, RoleEnum rolUsuario, boolean esAutorDelPost) {
        if (comment == null) {
            return "El comentario no existe";
        }
        
        // El autor del comentario, el autor del post, o un administrador pueden eliminar
        boolean esAutorDelComment = comment.getAutorId().equals(usuarioId);
        boolean esAdmin = rolUsuario == RoleEnum.Admin;
        
        if (!esAutorDelComment && !esAutorDelPost && !esAdmin) {
            return "Solo el autor del comentario, el autor del post o un administrador pueden eliminar este comentario";
        }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede dar like a un post
     * @param post Post a verificar
     * @param usuarioId ID del usuario que intenta dar like
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeDarLike(Post post, String usuarioId) {
        if (post == null) {
            return "El post no existe";
        }
        
        // Un usuario no puede dar like a su propio post
        if (post.getAutorId().equals(usuarioId)) {
            return "No puedes dar like a tu propia publicación";
        }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede comentar en un post
     * @param post Post a verificar
     * @param usuarioId ID del usuario que intenta comentar
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeComentar(Post post, String usuarioId) {
        if (post == null) {
            return "El post no existe";
        }
        
        // Un usuario no puede comentar en su propio post (opcional, puede cambiarse)
        // if (post.getAutorId().equals(usuarioId)) {
        //     return "No puedes comentar en tu propia publicación";
        // }
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede ver un post (para posts privados en el futuro)
     * @param post Post a verificar
     * @param usuarioId ID del usuario que intenta ver
     * @param rolUsuario Rol del usuario que intenta ver
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeVerPost(Post post, String usuarioId, RoleEnum rolUsuario) {
        if (post == null) {
            return "El post no existe";
        }
        
        // Por ahora, todos los posts son públicos
        // En el futuro se puede implementar lógica para posts privados
        
        return null; // Tiene permisos
    }
    
    /**
     * Verifica si un usuario puede ver un comentario
     * @param comment Comentario a verificar
     * @param usuarioId ID del usuario que intenta ver
     * @param rolUsuario Rol del usuario que intenta ver
     * @return Mensaje de error si no tiene permisos, null si tiene permisos
     */
    public static String puedeVerComment(Comment comment, String usuarioId, RoleEnum rolUsuario) {
        if (comment == null) {
            return "El comentario no existe";
        }
        
        // Por ahora, todos los comentarios son públicos
        // En el futuro se puede implementar lógica para comentarios privados
        
        return null; // Tiene permisos
    }
}
