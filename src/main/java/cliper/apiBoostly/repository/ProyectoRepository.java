package cliper.apiBoostly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cliper.apiBoostly.daos.Proyectos;

public interface ProyectoRepository extends JpaRepository<Proyectos, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
	@Query("SELECT p FROM Proyectos p WHERE p.categoriaProyecto.idCategoria = :idCategoria")
    List<Proyectos> findByIdCategoria(@Param("idCategoria") Long idCategoria);
	
	List<Proyectos> findByUsuarioId(Long idUsuario);
}
