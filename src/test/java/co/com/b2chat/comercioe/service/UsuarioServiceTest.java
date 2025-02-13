package co.com.b2chat.comercioe.service;


import co.com.b2chat.comercioe.dto.UsuarioDto;
import co.com.b2chat.comercioe.entity.Usuario;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDto usuarioDto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioDto = new UsuarioDto();
        usuarioDto.setCorreo("prueba@hot.com");
        usuarioDto.setNombre("Pedro");

        usuario = new Usuario();
        usuario.setCorreo("pepito@gmail.com");
        usuario.setNombre("Pepito");
    }

    @Test
    void deberia_guardar_usuario() {
        when(usuarioRepository.findByCorreo(usuarioDto.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.guardarUsuario(usuarioDto);

        assertNotNull(resultado);
        assertEquals(resultado.getCorreo(), "pepito@gmail.com");
    }

    @Test
    void deberia_lanzar_excepcion_alguardar() {
        when(usuarioRepository.findByCorreo(usuarioDto.getCorreo())).thenReturn(Optional.of(usuario));

        Exception excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.guardarUsuario(usuarioDto);
        });

        assertEquals("El correo electrónico ya está registrado", excepcion.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void getUserById_cuandoExiste_deberiaRetornarUsuario() {
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUserById(userId);

        assertNotNull(resultado);
        assertEquals(usuario.getCorreo(), resultado.getCorreo());
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void cuandoNoExiste_deberiaLanzarRecursoNoEncontradoException() {
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.getUserById(userId);
        });

        assertEquals("Usuario no encontrado", excepcion.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
    }
}