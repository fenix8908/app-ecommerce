package co.com.b2chat.comercioe.service;

import co.com.b2chat.comercioe.dto.UsuarioDto;
import co.com.b2chat.comercioe.entity.Usuario;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.mappers.UsuarioMapper;
import co.com.b2chat.comercioe.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioMapper userMapper = UsuarioMapper.INSTANCIA;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario guardarUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.findByCorreo(usuarioDto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        Usuario usuario = userMapper.usuarioDtoToUsuarioEntity(usuarioDto);
        return usuarioRepository.save(usuario);
    }

    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
    }


}
