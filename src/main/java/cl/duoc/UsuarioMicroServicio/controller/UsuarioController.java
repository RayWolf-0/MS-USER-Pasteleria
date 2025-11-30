package cl.duoc.UsuarioMicroServicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import cl.duoc.UsuarioMicroServicio.entity.LoginRequest;
import cl.duoc.UsuarioMicroServicio.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type" }, exposedHeaders = { "Content-Type" }, methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
})
@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET – Listar usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // GET – Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        return ResponseEntity.ok(usuario);
    }

    // POST – Crear usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {

        try {
            // Verificar si el email ya existe
            Usuario existente = usuarioService.buscarPorEmail(usuario.getEmail());
            if (existente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El correo ya está registrado");
            }

            usuarioService.guardarUsuario(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario");
        }
    }

    // DELETE – Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario no existe");
        }

        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    // PUT – Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuarioActualizado) {

        Usuario original = usuarioService.obtenerPorId(id);

        if (original == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Actualización de campos
        original.setNombre(usuarioActualizado.getNombre());
        original.setApellido(usuarioActualizado.getApellido());
        original.setEmail(usuarioActualizado.getEmail());
        original.setContrasena(usuarioActualizado.getContrasena());
        original.setTelefono(usuarioActualizado.getTelefono());
        original.setDireccion(usuarioActualizado.getDireccion());
        original.setTipo_usuario(usuarioActualizado.getTipo_usuario());

        usuarioService.guardarUsuario(original);

        return ResponseEntity.ok("Usuario actualizado");
    }

    // POST – LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            if (request.getMail() == null || request.getMail().isBlank()
                    || request.getPassword() == null || request.getPassword().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Email y contraseña son obligatorios");
            }

            // Buscar usuario por email REAL
            Usuario usuario = usuarioService.buscarPorEmail(request.getMail());

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El correo no está registrado");
            }

            // Validación de contraseña
            if (!usuario.getContrasena().equals(request.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Contraseña incorrecta");
            }

            // Login exitoso → retorna usuario
            return ResponseEntity.ok(usuario);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el login");
        }
    }

    @GetMapping("/firebase/{uid}")
    public ResponseEntity<?> buscarPorFirebase(@PathVariable String uid) {
        try {
            Usuario u = usuarioService.buscarUsuarioPorFirebase(uid);
            return ResponseEntity.ok(u);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

}
