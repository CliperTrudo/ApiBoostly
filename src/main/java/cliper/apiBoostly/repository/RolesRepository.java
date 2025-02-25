package cliper.apiBoostly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cliper.apiBoostly.daos.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    // Puedes agregar consultas personalizadas si es necesario, como buscar un rol por nombre o descripción
    // Ejemplo:
    // Roles findByNombreRol(String nombreRol);
}
