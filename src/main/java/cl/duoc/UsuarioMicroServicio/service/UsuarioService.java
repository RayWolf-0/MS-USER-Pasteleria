package cl.duoc.UsuarioMicroServicio.service;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> obtenerUsuarios();
    Usuario obtenerPorId(String id);
    Usuario buscarPorEmail(String email);

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(String id);
}


