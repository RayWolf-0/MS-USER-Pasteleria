package cl.duoc.UsuarioMicroServicio.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
import cl.duoc.UsuarioMicroServicio.service.UsuarioServiceImpl;

public class TestUsuarioService {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo() {

        List<Usuario> lista = new ArrayList<>();

        Usuario u1 = new Usuario();
        u1.setIdUsuario("U1");
        u1.setNombre("Irina");

        Usuario u2 = new Usuario();
        u2.setIdUsuario("U2");
        u2.setNombre("Isaac");

        lista.add(u1);
        lista.add(u2);

        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarUnUsuario() {

        Usuario u = new Usuario();
        u.setIdUsuario("U1");
        u.setNombre("Irina");

        when(usuarioRepository.findById("U1")).thenReturn(Optional.of(u));

        Usuario resultado = usuarioService.obtenerPorId("U1");

        assertNotNull(resultado);
        assertEquals("U1", resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).findById("U1");
    }

    @Test
    public void testGuardarUsuario() {

        Usuario u = new Usuario();
        u.setIdUsuario("U1");
        u.setNombre("Irina");

        when(usuarioRepository.save(u)).thenReturn(u);

        Usuario resultado = usuarioService.guardarUsuario(u);

        assertNotNull(resultado);
        assertEquals("U1", resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).save(u);
    }

    // ============================================================
    // TEST 4: Eliminar usuario
    // ============================================================
    @Test
    public void testEliminarUsuario() {

        String id = "U1";

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}

