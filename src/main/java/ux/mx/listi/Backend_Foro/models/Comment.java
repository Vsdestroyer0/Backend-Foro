/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.models;

/* Importaciones */
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Entidad que representa un comentario en una publicación del foro
 * Almacena el contenido, autor y la publicación a la que pertenece
 */
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String postId;           // ID del post al que pertenece el comentario
    private String autorId;          // ID del usuario que hizo el comentario
    private String nombreAutor;      // Nombre del autor para mostrar fácilmente
    private String contenido;        // Texto del comentario
    private LocalDateTime fechaCreacion; // Fecha de creación del comentario

    // Constructor por defecto
    public Comment() {
        this.fechaCreacion = LocalDateTime.now();
        this.postId = "";
    }

    // Constructor con parámetros principales
    public Comment(String postId, String autorId, String nombreAutor, String contenido) {
        this.postId = postId != null ? postId : "";
        this.autorId = autorId;
        this.nombreAutor = nombreAutor;
        this.contenido = contenido;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
