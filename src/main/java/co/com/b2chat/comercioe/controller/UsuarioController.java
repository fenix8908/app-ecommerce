package co.com.b2chat.comercioe.controller;

import co.com.b2chat.comercioe.dto.RespuestaGenerica;
import co.com.b2chat.comercioe.dto.UsuarioDto;
import co.com.b2chat.comercioe.entity.Usuario;

import co.com.b2chat.comercioe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<RespuestaGenerica<?>> registrarUsuario(@RequestBody UsuarioDto usuario) {
        try {
            Usuario userCreado = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RespuestaGenerica.builder()
                            .exito(true).datos(userCreado).mensaje("El usuario ha sido creado").build());
        }catch (Exception ex){
            return ResponseEntity.status(500)
                    .body(RespuestaGenerica.builder()
                            .exito(false).mensaje(ex.getMessage()).build());
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Usuario user = usuarioService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
