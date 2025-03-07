package cliper.apiBoostly.repository;

import cliper.apiBoostly.daos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Categoria.
 * Esta interfaz extiende JpaRepository y proporciona operaciones CRUD sobre la entidad Categoria.
 * @author Sergio Alfonseca
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
