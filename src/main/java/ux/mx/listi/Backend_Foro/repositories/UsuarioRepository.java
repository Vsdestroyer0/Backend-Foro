/* paquete */
package ux.mx.listi.Backend_Foro.repositories;

/* Importaciones */
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import ux.mx.listi.Backend_Foro.models.usuarios;

// Esto es una tipo Capa de acceso a datos, para la colección de usuarios
// El extends nos da los métodos CRUD sin definirlosm teniendo save, findById, findAll y deleteById
public interface UsuarioRepository extends MongoRepository<usuarios, String> {
    // Aquí se generan querys dinámicas, analizando el nombre, por ejemplo
    // findByUsername nos da una query tipo así { "username": <valor> }
    Optional<usuarios> findByUsername(String username); // No creo volver a ocuparlo maybe si se implementa ver un Usuario pero no se
    Optional<usuarios> findByEmail(String email); // Este se usará para el login
}
