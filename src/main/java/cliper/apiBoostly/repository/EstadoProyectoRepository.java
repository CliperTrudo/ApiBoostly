package cliper.apiBoostly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cliper.apiBoostly.daos.EstadoProyecto;

@Repository
public interface EstadoProyectoRepository extends JpaRepository<EstadoProyecto, Integer> {
    // Si deseas buscar por nombre:
    Optional<EstadoProyecto> findByid(long id);
}
