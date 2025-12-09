/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.controllers;

/* Importaciones */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ux.mx.listi.Backend_Foro.models.usuarios;
import ux.mx.listi.Backend_Foro.services.interfaces.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // ResponseEntity es una clase de Spring para controlar respuestas tipo HTTP
    // ? Indica que el tipo de respuesta puede ser cualquier cosa
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request.email == null || request.password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes proporcionar email y contraseña");
        }

        usuarios usuario = usuarioRepository.findByEmail(request.email)
                .orElse(null);

        if (usuario == null || !usuario.getPassword().equals(request.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email o contraseña incorrectos");
        }

        return ResponseEntity.ok(new AuthResponse(usuario.getId(), usuario.getRoleEnum().toString()));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.username == null || request.password == null || request.email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes proporcionar usuario, contraseña y email");
        }

        // Verificar si el usuario ya existe
        if (usuarioRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya existe");
        }

        // Crear nuevo usuario
        usuarios nuevoUsuario = new usuarios();
        nuevoUsuario.setUsername(request.username);
        nuevoUsuario.setPassword(request.password); 
        nuevoUsuario.setEmail(request.email);
        nuevoUsuario.setRoleEnum(ux.mx.listi.Backend_Foro.enums.RoleEnum.User);

        usuarios guardado = usuarioRepository.save(nuevoUsuario);
        
        return ResponseEntity.ok(new AuthResponse(guardado.getId(), guardado.getRoleEnum().toString()));
    }
    
    /* Clases internas para las respuestas */
    // LoginRequest es una clase interna para la solicitud de login
    public static class LoginRequest {
        public String email;
        public String password;
    }
    
    // RegisterRequest es una clase interna para la solicitud de registro
    public static class RegisterRequest {
        public String username;
        public String password;
        public String email;
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
