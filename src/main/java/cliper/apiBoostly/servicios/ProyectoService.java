package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Categoria;
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
    
    @Autowired
    private CategoriaService categoriaService;
    
    /**
     * Crea un nuevo proyecto a partir de un ProyectoDto.
     * Se asume que el campo idUsuario del DTO contiene el id del usuario logueado.
     * 
     * @param dto El DTO con los datos del nuevo proyecto.
     * @return El proyecto creado.
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
            dto.getFechaInicioProyecto(),
            dto.getFechaFinalizacionProyecto(),
            dto.getMetaRecaudacionProyecto(),
            dto.getEstadoProyecto(),
            categoriaService.obtenerCategoriaPorId(dto.getIdCategoria())
        );
        return proyectoRepository.save(proyecto);
    }
    
    /**
     * Retorna la lista de todos los proyectos.
     * 
     * @return Una lista de proyectos.
     */
    @Transactional(readOnly = true)
    public List<Proyectos> listarProyectos() {
        return proyectoRepository.findAll();
    }
    
    /**
     * Retorna un proyecto por su ID.
     * 
     * @param id El ID del proyecto a buscar.
     * @return El proyecto encontrado o null si no existe.
     */
    @Transactional(readOnly = true)
    public Proyectos obtenerProyectoPorId(Long id) {
        Optional<Proyectos> optional = proyectoRepository.findById(id);
        return optional.orElse(null);
    }
    
    /**
     * Actualiza un proyecto existente a partir del ID y los nuevos datos contenidos en el DTO.
     * Se asume que algunos campos, como la fecha de inicio, no se actualizan.
     * 
     * @param id El ID del proyecto a actualizar.
     * @param dto El DTO con los nuevos datos.
     * @return El proyecto actualizado o null si no se encontró el proyecto.
     */
    public Proyectos actualizarProyecto(Long id, ProyectoDto dto) {
        Proyectos proyectoExistente = obtenerProyectoPorId(id);
        if (proyectoExistente == null) {
            return null;
        }

        // Actualizar solo si los valores no son nulos
        if (dto.getNombreProyecto() != null) {
            proyectoExistente.setNombreProyecto(dto.getNombreProyecto());
        }
        if (dto.getDescripcionProyecto() != null) {
            proyectoExistente.setDescripcionProyecto(dto.getDescripcionProyecto());
        }
        if (dto.getImagen1Proyecto() != null) {
            proyectoExistente.setImagen1Proyecto(dto.getImagen1Proyecto());
        }
        if (dto.getImagen2Proyecto() != null) {
            proyectoExistente.setImagen2Proyecto(dto.getImagen2Proyecto());
        }
        if (dto.getImagen3Proyecto() != null) {
            proyectoExistente.setImagen3Proyecto(dto.getImagen3Proyecto());
        }
        if (dto.getFechaFinalizacionProyecto() != null) {
            proyectoExistente.setFechaFinalizacionProyecto(dto.getFechaFinalizacionProyecto());
        }
        if (dto.getMetaRecaudacionProyecto() != null) {
            proyectoExistente.setMetaRecaudacionProyecto(dto.getMetaRecaudacionProyecto());
        }
        if (dto.getEstadoProyecto() != null) {
            proyectoExistente.setEstadoProyecto(dto.getEstadoProyecto());
        }
        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaService.obtenerCategoriaPorId(dto.getIdCategoria());
            if (categoria != null) {
                proyectoExistente.setCategoriaProyecto(categoria);
            }
        }

        return proyectoRepository.save(proyectoExistente);
    }
    
    /**
     * Obtiene los proyectos de un usuario específico.
     * 
     * @param idUsuario El ID del usuario cuyos proyectos se desean obtener.
     * @return Una lista de proyectos del usuario.
     */
    public List<Proyectos> obtenerProyectosPorUsuario(Long idUsuario) {
        return proyectoRepository.findByUsuarioId(idUsuario);
    }
    
    /**
     * Obtiene los proyectos de una categoría específica.
     * 
     * @param idCategoria El ID de la categoría cuyos proyectos se desean obtener.
     * @return Una lista de proyectos de la categoría.
     */
    public List<Proyectos> obtenerProyectosPorCategoria(Long idCategoria) {
        return proyectoRepository.findByIdCategoria(idCategoria);
    }

    /**
     * Elimina un proyecto por su ID.
     * 
     * @param id El ID del proyecto a eliminar.
     * @return true si el proyecto fue eliminado, false si hubo un error.
     */
    @Transactional
    public boolean eliminarProyecto(Long id) {
    	try {
			proyectoRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    }
}
