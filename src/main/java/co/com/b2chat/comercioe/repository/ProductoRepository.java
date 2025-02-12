package co.com.b2chat.comercioe.repository;

import co.com.b2chat.comercioe.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
