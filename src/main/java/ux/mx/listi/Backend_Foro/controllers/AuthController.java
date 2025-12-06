/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.controllers;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ux.mx.listi.Backend_Foro.models.usuarios;
import ux.mx.listi.Backend_Foro.repositories.UsuarioRepository;

@Service
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // ResponseEntity es una clase de Spring para controlar respuestas tipo HTTP
    // ? Indica que el tipo de respuesta puede ser cualquier cosa
    public ResponseEntity<?> login(LoginRequest request) {
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
    
    /* Clases internas para las respuestas */
    // LoginRequest es una clase interna para la solicitud de login
    public static class LoginRequest {
        public String username;
        public String password;
    }
    
    // AuthResponse es una clase interna para la respuesta de autenticación
    public static class AuthResponse {
        public String userId;
        public String role;
        
        public AuthResponse(String userId, String role) {
            this.userId = userId;
            this.role = role;
        }
    }
}
