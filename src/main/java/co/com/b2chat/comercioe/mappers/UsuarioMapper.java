package co.com.b2chat.comercioe.mappers;

import co.com.b2chat.comercioe.dto.UsuarioDto;
import co.com.b2chat.comercioe.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCIA = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDto usuarioEntityToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuarioEntity(UsuarioDto usuario);
}
