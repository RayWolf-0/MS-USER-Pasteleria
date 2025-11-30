package cl.duoc.UsuarioMicroServicio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByIdFirebase(String idFirebase);
}
