package cl.duoc.UsuarioMicroServicio.controller;

import cl.duoc.UsuarioMicroServicio.Assembler.usuarioassembler;
import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import cl.duoc.UsuarioMicroServicio.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private usuarioassembler assembler;

    //endpoint para obtener todos los usuarios
    @GetMapping
    @Operation(summary = "Usuarios", description = "Operación que lista los usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los usuarios",
            content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro el usuario",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "no se encuentran Datos")))
    })
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerUsuarios();
    }

    //endpoint para buscar un usuario por id
    @Operation(summary = "Endpoint que busca un usuario por id", description = "Operación que busca y lista un usuario")
    @Parameters(value = {
        @Parameter(name = "idItem", description = "Id del usuario que se va a buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se lista correctamente El usuario",
            content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentra el usuario",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "No se encuentran usuarios")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> obtenerPorId(@PathVariable int id) {
        Usuario usuario = usuarioService.obtenerPorId(null);
        return usuario != null ? ResponseEntity.ok(assembler.toModel(usuario)) : ResponseEntity.notFound().build();
    }

    //endpoint para guardar un usuario
    @PostMapping
    @Operation(summary = "Endpoint que registra un usuario", description = "Endpoint que registra un usuario", 
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto usuario que se va a registrar", required = true,
    content = @Content(schema = @Schema(implementation = Usuario.class))
    ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Indica que se registro correctamente el usuario", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "500", description = "Indica que no se logro registrar el usuario",
        content = @Content(schema = @Schema(type = "String", example = "No se puede registar El usuario")))
    })
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    //endpoint que edita un usuario
    @PutMapping("/{id}")
    @Operation(summary = "Endpoint que edita un usuario", description = "Endpoint que edita un usuario", 
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto usuario que se va a editar", required = true,
    content = @Content(schema = @Schema(implementation = Usuario.class))
    ))
    @Parameters(value = {
        @Parameter(name = "iditem", description = "Id del usuario que se va a editar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Indica que se registro correctamente el usuario", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "500", description = "usuaio no esta registrada",
        content = @Content(schema = @Schema(type = "String", example = "usuario no esta registrado")))
    })
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioService.obtenerPorId(null);
        if (usuarioExistente != null) {
            usuarioActualizado.setIdUsuario(null);
            return ResponseEntity.ok(assembler.toModel(usuarioExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //endpoint para eliminar un usuario
    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint que busca y elimina un usuario", description = "Operación que busca y elimina un usuario")
    @Parameters(value = {
        @Parameter(name = "idusuario", description = "Id del usuario que se va a eliminar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se elimina usuario",
        content = @Content(mediaType = "application/json",
        schema = @Schema(type = "string", example = "Se elimina usuario"))),
        @ApiResponse(responseCode = "404", description = "usuario no esta registrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "usuario no esta registrado")))
    })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(null);
        return ResponseEntity.noContent().build();
    }
    
}
