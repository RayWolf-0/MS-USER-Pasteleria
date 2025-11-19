package cl.duoc.UsuarioMicroServicio.repository;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
