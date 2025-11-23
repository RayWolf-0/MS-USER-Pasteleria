package cl.duoc.UsuarioMicroServicio.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.duoc.UsuarioMicroServicio.controller.UsuarioController;
import cl.duoc.UsuarioMicroServicio.entity.Usuario;

@Component
public class usuarioassembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(
                usuario,
                linkTo(methodOn(UsuarioController.class).buscarUsuario(usuario.getIdUsuario()))
                        .withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar())
                        .withRel("usuarios"));
    }
}
