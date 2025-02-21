package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Proyectos;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.dtos.ProyectoDto;
import cliper.apiBoostly.repository.ProyectoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;
    
    /**
     * Crea un nuevo proyecto a partir de un ProyectoDto.
     * Se asume que el campo idUsuario del DTO contiene el id del usuario logueado.
     */
    @Transactional
    public Proyectos crearProyecto(ProyectoDto dto) {
        // Se crea un objeto de tipo Usuarios únicamente con el id,
        // para establecer la relación ManyToOne con el proyecto.
        Usuarios usuario = new Usuarios();
        usuario.setId(dto.getIdUsuario());
        
        Proyectos proyecto = new Proyectos(
            usuario,
            dto.getNombreProyecto(),
            dto.getDescripcionProyecto(),
            dto.getImagen1Proyecto(),
            dto.getImagen2Proyecto(),
            dto.getImagen3Proyecto(),
            dto.getFechaInicioProyecto(),          // Se espera que el controlador asigne la fecha de inicio (o se asigne aquí)
            dto.getFechaFinalizacionProyecto(),     // Validar que sea posterior a la fecha de inicio en el controlador o servicio
            dto.getMetaRecaudacionProyecto(),       // Debe ser un número positivo
            dto.getEstadoProyecto(),                // true = activo, false = inactivo/finalizado
            dto.getCategoriaProyecto()
        );
        return proyectoRepository.save(proyecto);
    }
    
    /**
     * Retorna la lista de todos los proyectos.
     */
    @Transactional(readOnly = true)
    public List<Proyectos> listarProyectos() {
        return proyectoRepository.findAll();
    }
    
    /**
     * Retorna un proyecto por su ID.
     */
    @Transactional(readOnly = true)
    public Proyectos obtenerProyectoPorId(Long id) {
        Optional<Proyectos> optional = proyectoRepository.findById(id);
        return optional.orElse(null);
    }
    
    /**
     * Actualiza un proyecto existente a partir del ID y los nuevos datos contenidos en el DTO.
     * Se asume que algunos campos, como la fecha de inicio, no se actualizan.
     */
    @Transactional
    public Proyectos actualizarProyecto(Long id, ProyectoDto dto) {
        Proyectos proyectoExistente = obtenerProyectoPorId(id);
        if (proyectoExistente == null) {
            return null;
        }
        // Actualización de campos que pueden modificarse:
        proyectoExistente.setNombreProyecto(dto.getNombreProyecto());
        proyectoExistente.setDescripcionProyecto(dto.getDescripcionProyecto());
        proyectoExistente.setImagen1Proyecto(dto.getImagen1Proyecto());
        proyectoExistente.setImagen2Proyecto(dto.getImagen2Proyecto());
        proyectoExistente.setImagen3Proyecto(dto.getImagen3Proyecto());
        proyectoExistente.setFechaFinalizacionProyecto(dto.getFechaFinalizacionProyecto());
        proyectoExistente.setMetaRecaudacionProyecto(dto.getMetaRecaudacionProyecto());
        proyectoExistente.setEstadoProyecto(dto.getEstadoProyecto());
        proyectoExistente.setCategoriaProyecto(dto.getCategoriaProyecto());
        return proyectoRepository.save(proyectoExistente);
    }
    
    /**
     * Elimina un proyecto por su ID.
     */
    @Transactional
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
}
