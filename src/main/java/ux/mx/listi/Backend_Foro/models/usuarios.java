    /* Paquete de la clase */
package ux.mx.listi.Backend_Foro.models;

    /* Importaciones */
// Sirve para identificar la llave primaria de un documento
import org.springframework.data.annotation.Id;
// Sirve para indicar que es una entidad que se guardará como documento en la base de mongo
//Se puede especificar donde se guardará usando @Document(collection = "colección")
import org.springframework.data.mongodb.core.mapping.Document;
import ux.mx.listi.Backend_Foro.enums.RoleEnum;

    /* Implementación del código */
// Se usa Document en lugar de Entity ya que se usa para especificar una colección de Mongo 
// (Si no se especifica se usará una clase como el nombre de la clase, en este caso usuarios por ejemplo)
@Document(collection = "usuarios")
// Creación clase usuarios
public class usuarios {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    // Se inicializará siempre como usuario gracias al parámetro role = xx.User
    private RoleEnum role = RoleEnum.User;

    // Lo de abajo no se ocupa ya que no es similar a estructura JS
    //private String role (enum ["User", "Admin"], default "User")

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public RoleEnum getRoleEnum() {
        return role;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        role = roleEnum;
    }
}


