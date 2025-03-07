package cliper.apiBoostly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cliper.apiBoostly.daos.Proyectos;

/**
 * Repositorio para la entidad Proyectos.
 * Esta interfaz extiende JpaRepository y proporciona operaciones CRUD sobre la entidad Proyectos.
 * Se pueden agregar consultas personalizadas según sea necesario.
 * @author Sergio Alfonseca
 */
public interface ProyectoRepository extends JpaRepository<Proyectos, Long> {
    /**
     * Consulta personalizada para obtener proyectos por categoría.
     * 
     * @param idCategoria El ID de la categoría.
     * @return Lista de proyectos que pertenecen a la categoría especificada.
     */
    @Query("SELECT p FROM Proyectos p WHERE p.categoriaProyecto.idCategoria = :idCategoria")
    List<Proyectos> findByIdCategoria(@Param("idCategoria") Long idCategoria);
    
    /**
     * Método para obtener proyectos por ID de usuario.
     * 
     * @param idUsuario El ID del usuario creador del proyecto.
     * @return Lista de proyectos asociados a ese usuario.
     */
    List<Proyectos> findByUsuarioId(Long idUsuario);
}
