/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.models;

/* Importaciones */
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una publicación en el foro
 * Almacena el título, descripción, autor, likes y comentarios
 */
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String autorId;          // ID del usuario que creó el post
    private String nombreAutor;      // Nombre del autor para mostrar fácilmente
    private int likes;               // Contador de likes
    private LocalDateTime fechaCreacion; // Fecha de creación del post
    private List<String> usuariosQueDieronLike; // Lista de IDs de usuarios que dieron like

    // Constructor por defecto
    public Post() {
        this.likes = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor con parámetros principales
    public Post(String titulo, String descripcion, String autorId, String nombreAutor) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autorId = autorId;
        this.nombreAutor = nombreAutor;
        this.likes = 0;
        this.fechaCreacion = LocalDateTime.now(); // Aun por ver su implementación
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<String> getUsuariosQueDieronLike() {
        return usuariosQueDieronLike;
    }

    public void setUsuariosQueDieronLike(List<String> usuariosQueDieronLike) {
        this.usuariosQueDieronLike = usuariosQueDieronLike;
    }
}
