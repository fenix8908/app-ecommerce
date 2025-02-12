package co.com.b2chat.comercioe.repository;

import co.com.b2chat.comercioe.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByCorreo(String email);
}
