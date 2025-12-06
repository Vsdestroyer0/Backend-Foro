/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.models.usuarios;
import ux.mx.listi.Backend_Foro.repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request.username == null || request.password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes proporcionar usuario y contraseña");
        }

        usuarios usuario = usuarioRepository.findByUsername(request.username)
                .orElse(null);

        if (usuario == null || !usuario.getPassword().equals(request.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        return ResponseEntity.ok(new AuthResponse(usuario.getId(), usuario.getRoleEnum().toString()));
    }
    
    // Clases internas para las respuestas
    public static class LoginRequest {
        public String username;
        public String password;
    }
    
    public static class AuthResponse {
        public String userId;
        public String role;
        
        public AuthResponse(String userId, String role) {
            this.userId = userId;
            this.role = role;
        }
    }
}
