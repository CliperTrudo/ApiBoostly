package cliper.apiBoostly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cliper.apiBoostly.daos.Donaciones;

import java.util.List;

/**
 * DonacionRepository
 *
 * Interfaz de repositorio para la entidad Donacion.
 * Proporciona métodos estándar de Spring Data JPA.
 */
@Repository
public interface DonacionRepository extends JpaRepository<Donaciones, Long> {

    /**
     * Encuentra todas las donaciones con un estado concreto para un proyecto dado.
     * Útil para cuando vayas a capturar o anular tras cierre de proyecto.
     *
     * @param proyectoId ID del proyecto
     * @param estado     Estado de la donación (p.ej. "PENDIENTE_AUTORIZACION")
     * @return Lista de donaciones que cumplen esos criterios
     */
    List<Donaciones> findByProyecto_IdProyectoAndEstado(Long proyectoId, String estado);
    
    /**
     * Encuentra todas las donaciones para un proyecto dado (sin filtrar por estado).
     *
     * @param proyectoId ID del proyecto
     * @return Lista de donaciones para ese proyecto
     */
    List<Donaciones> findByProyecto_IdProyecto(Long proyectoId);
}
