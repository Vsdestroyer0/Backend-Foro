/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.routes;

/* Importacniones */
import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.controllers.AuthController;

// RestController sirve para avisarle a spring que AuthRoutes sirve para métodos REST
@RestController
// Define la ruta base del controlador 
@RequestMapping("/api/auth")
public class AuthRoutes {
    // 
    private final AuthController authController;

    /**
     * Constructor de la clase AuthRoutes
     * Inyecta el controlador de autenticación mediante inyección de dependencias
     * (La inyección por constructor hace es por ejemplo, es pasar una dependencia como Authcontroller dentro de authcontrollers, dentro
     * y esas dependencias se declaran y se pasan al constructor)
     * @param authController Controlador que maneja la lógica de autenticación
     */
    public AuthRoutes(AuthController authController) {
        this.authController = authController;
    }

    /**
     * Endpoint para iniciar sesión de usuarios
     * Recibe las credenciales del usuario y las envía al controlador para su validación
     */
    @PostMapping("/login")
    public Object login(@RequestBody AuthController.LoginRequest request) {
        return authController.login(request);
    }
    
    /**
     * Endpoint para registrar nuevos usuarios en el sistema
     * Recibe los datos del nuevo usuario y los envía al controlador para su creación
     */
    @PostMapping("/register")
    public Object register(@RequestBody AuthController.RegisterRequest request) {
        return authController.register(request);
    }
}
