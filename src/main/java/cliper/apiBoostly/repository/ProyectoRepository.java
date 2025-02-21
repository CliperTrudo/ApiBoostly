package cliper.apiBoostly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cliper.apiBoostly.daos.Proyectos;

public interface ProyectoRepository extends JpaRepository<Proyectos, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
