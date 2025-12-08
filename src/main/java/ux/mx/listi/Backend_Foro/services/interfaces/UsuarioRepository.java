package ux.mx.listi.Backend_Foro.services.interfaces;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ux.mx.listi.Backend_Foro.models.usuarios;

public interface UsuarioRepository extends MongoRepository<usuarios, String> {
    Optional<usuarios> findByUsername(String username);
}
