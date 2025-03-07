package cliper.apiBoostly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cliper.apiBoostly.daos.Roles;

/**
 * Repositorio para la entidad Roles.
 * Esta interfaz extiende JpaRepository y proporciona operaciones CRUD sobre la entidad Roles.
 * Se pueden agregar consultas personalizadas según sea necesario.
 * @author Sergio Alfonseca
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {
    // Puedes agregar consultas personalizadas si es necesario, como buscar un rol por nombre o descripción
    // Ejemplo:
    // Roles findByNombreRol(String nombreRol);
}
