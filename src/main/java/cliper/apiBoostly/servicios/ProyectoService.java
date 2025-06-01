package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.daos.EstadoProyecto;
import cliper.apiBoostly.daos.Proyectos;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.dtos.ProyectoDto;
import cliper.apiBoostly.repository.EstadoProyectoRepository;
import cliper.apiBoostly.repository.ProyectoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private EstadoProyectoRepository estadoProyectoRepository;

    /**
     * Crea un nuevo proyecto a partir de un ProyectoDto.
     * El estado inicial será "En revisión" (id = 1).
     */
    @Transactional
    public Proyectos crearProyecto(ProyectoDto dto) {
        // 1) Construir objeto Usuarios con solo el ID
        Usuarios usuario = new Usuarios();
        usuario.setId(dto.getIdUsuario());

        // 2) Obtener el estado "En revisión" (id = 1) desde el repositorio
        EstadoProyecto estadoRevision = estadoProyectoRepository.findById(2)
            .orElseThrow(() -> new RuntimeException("No existe estado 'En revisión' con id=1"));

        // 3) Construir la entidad Proyectos usando el constructor
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
            estadoRevision,
            categoriaService.obtenerCategoriaPorId(dto.getIdCategoria())
        );

        // 4) Guardar y devolver
        return proyectoRepository.save(proyecto);
    }

    /**
     * Retorna la lista de todos los proyectos, ajustando su estado
     * de "En revisión" a "Activo"/"Finalizado" según la fecha.
     */
    @Transactional(readOnly = true)
    public List<Proyectos> listarProyectos() {
        List<Proyectos> lista = proyectoRepository.findAll();
        // Para cada proyecto, actualizamos el estado si procede
        return lista.stream()
                    .peek(this::ajustarEstadoSegunFecha)
                    .collect(Collectors.toList());
    }

    /**
     * Retorna un proyecto por su ID, o null si no existe.
     * Antes de devolverlo, ajusta su estado según la fecha.
     */
    @Transactional(readOnly = true)
    public Proyectos obtenerProyectoPorId(Long id) {
        Optional<Proyectos> optional = proyectoRepository.findById(id);
        if (optional.isPresent()) {
            Proyectos proyecto = optional.get();
            ajustarEstadoSegunFecha(proyecto);
            return proyecto;
        }
        return null;
    }

    /**
     * Actualiza un proyecto existente a partir del ID y los nuevos datos contenidos en el DTO.
     * Se actualizan solo los campos no nulos; para el estado, se toma idEstado desde el DTO.
     * Si dto.getIdEstado() no es nulo, se usa para cambiar el estado explícitamente.
     */
    @Transactional
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

        //  Para el estado: si viene dto.getIdEstado(), usamos ese id para buscar el EstadoProyecto
        if (dto.getIdEstado() != null) {
            EstadoProyecto estado = estadoProyectoRepository.findById(dto.getIdEstado().intValue())
                .orElseThrow(() -> new RuntimeException("No existe estado con id=" + dto.getIdEstado()));
            proyectoExistente.setEstadoProyecto(estado);
        }

        // Si cambia categoría
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
     * Ajusta el estado según la fecha antes de devolver la lista.
     */
    @Transactional(readOnly = true)
    public List<Proyectos> obtenerProyectosPorUsuario(Long idUsuario) {
        List<Proyectos> lista = proyectoRepository.findByUsuarioId(idUsuario);
        return lista.stream()
                    .peek(this::ajustarEstadoSegunFecha)
                    .collect(Collectors.toList());
    }

    /**
     * Obtiene los proyectos de una categoría específica.
     * Ajusta el estado según la fecha antes de devolver la lista.
     */
    @Transactional(readOnly = true)
    public List<Proyectos> obtenerProyectosPorCategoria(Long idCategoria) {
        List<Proyectos> lista = proyectoRepository.findByIdCategoria(idCategoria);
        return lista.stream()
                    .peek(this::ajustarEstadoSegunFecha)
                    .collect(Collectors.toList());
    }

    /**
     * Elimina un proyecto por su ID.
     * 
     * @param id El ID del proyecto a eliminar.
     * @return true si el proyecto fue eliminado, false en caso de error.
     */
    @Transactional
    public boolean eliminarProyecto(Long id) {
        try {
            proyectoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Si el proyecto está aún en "En revisión" (id=1), revisa su fecha de finalización:
     * - Si la fecha actual >= fechaFinalización → marca "Finalizado" (id=3).
     * - Si la fecha actual < fechaFinalización → marca "Activo" (id=2).
     * Luego persiste el cambio.
     */
    private void ajustarEstadoSegunFecha(Proyectos proyecto) {
        EstadoProyecto estadoActual = proyecto.getEstadoProyecto();
        if (estadoActual.getId() == 1) { // Solo si está "En revisión"
            LocalDate fechaFin = proyecto.getFechaFinalizacionProyecto();
            LocalDate hoy = LocalDate.now();

            if (fechaFin != null) {
                if (!hoy.isBefore(fechaFin)) {
                    // hoy >= fechaFin → Finalizado (id=3)
                    EstadoProyecto estadoFinalizado = estadoProyectoRepository.findById(3)
                            .orElseThrow(() -> new RuntimeException("No existe estado 'Finalizado' con id=3"));
                    proyecto.setEstadoProyecto(estadoFinalizado);
                } else {
                    // hoy < fechaFin → Activo (id=2)
                    EstadoProyecto estadoActivo = estadoProyectoRepository.findById(2)
                            .orElseThrow(() -> new RuntimeException("No existe estado 'Activo' con id=2"));
                    proyecto.setEstadoProyecto(estadoActivo);
                }
                // Persistir el cambio de estado
                proyectoRepository.save(proyecto);
            }
            // Si fechaFin es null, dejamos "En revisión" hasta que se le ponga fecha.
        }
    }
}
