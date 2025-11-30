package cl.duoc.UsuarioMicroServicio.service;

import java.util.List;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;

public interface UsuarioService {

    List<Usuario> obtenerUsuarios();

    Usuario obtenerPorId(String id);

    Usuario buscarPorEmail(String email);

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(String id);

    Usuario buscarUsuarioPorFirebase(String idFirebase);
}

