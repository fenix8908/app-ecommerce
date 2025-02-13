package co.com.b2chat.comercioe.mappers;
import co.com.b2chat.comercioe.dto.ProductoDto;
import co.com.b2chat.comercioe.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCIA = Mappers.getMapper(ProductoMapper.class);

    ProductoDto productoEntityToproductoDto(Producto producto);

    Producto productoDtoToProductoEntity(ProductoDto productoDto);

    List<ProductoDto> productoListEntityToListDto(List<Producto> productos);
}
