/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.routes;

import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.controllers.AuthController;

@RestController
@RequestMapping("/api/auth")
public class AuthRoutes {
    
    private final AuthController authController;

    public AuthRoutes(AuthController authController) {
        this.authController = authController;
    }

    @PostMapping("/login")
    public Object login(@RequestBody AuthController.LoginRequest request) {
        return authController.login(request);
    }
}
