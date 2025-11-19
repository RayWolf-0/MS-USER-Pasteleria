package cl.duoc.UsuarioMicroServicio.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import cl.duoc.UsuarioMicroServicio.repository.UsuarioRepository;
import cl.duoc.UsuarioMicroServicio.service.UsuarioService;
import cl.duoc.UsuarioMicroServicio.service.UsuarioServiceImpl;

public class TestUsuarioService {

    @Mock
    private UsuarioRepository usuariorepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioservice;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo(){

        List<Usuario> lista = new ArrayList<>();

        Usuario user1 = new Usuario();
        Usuario user2 = new Usuario();

        user1.setIdUsuario(1);
        user1.setDireccion("las condes 123");
        user1.setEmail("topalterluz.00.b@gmail.com");
        user1.setTelefono("99892228");
        user1.setNombre("irina Marin");

        user2.setIdUsuario(2);
        user2.setDireccion("san joaquin 456");
        user2.setEmail("inexsistent@gmail.com");
        user2.setTelefono("91333345");
        user2.setNombre("Isaac valenzuela");

        lista.add(user1);
        lista.add(user2);

        when(usuariorepository.findAll()).thenReturn(lista);

        List<Usuario> resultadoBusqueda = usuarioservice.obtenerUsuarios();

        assertEquals(2, resultadoBusqueda.size());
        verify(usuariorepository, times(1)).findAll();
    }

    @Test
    public void testBuscarUnUsuario(){
        Usuario user1 = new Usuario();
        user1.setIdUsuario(1);
        user1.setDireccion("las condes 123");
        user1.setEmail("topalterluz.00.b@gmail.com");
        user1.setTelefono("99892228");
        user1.setNombre("irina Marin");

        when(usuariorepository.findById(1)).thenReturn(Optional.of(user1));

        Usuario usuarioBuscado = usuarioservice.obtenerPorId(1);
        assertEquals(1, usuarioBuscado.getIdUsuario());
        verify(usuariorepository, times(1)).findById(1);
    }

    @Test
    public void testGuardarUsuario(){
        Usuario user1 = new Usuario();
        user1.setIdUsuario(1);
        user1.setDireccion("las condes 123");
        user1.setEmail("topalterluz.00.b@gmail.com");
        user1.setTelefono("99892228");
        user1.setNombre("irina Marin");

        when(usuariorepository.save(user1)).thenReturn(user1);

        Usuario usuarioGuardado = usuarioservice.guardarUsuario(user1);

        assertEquals(1, usuarioGuardado.getIdUsuario());
        verify(usuariorepository, times(1)).save(user1);

    }

    @Test
    public void testeliminarUsuario(){
        int IdUsuario = 1;
        doNothing().when(usuariorepository).deleteById(IdUsuario);

        usuarioservice.eliminarUsuario(IdUsuario);

        verify(usuariorepository,times(1)).deleteById(IdUsuario);
    }

}
