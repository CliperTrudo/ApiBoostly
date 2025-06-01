package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Donaciones;
import cliper.apiBoostly.dtos.DonacionDto;
import cliper.apiBoostly.repository.DonacionRepository;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.daos.Proyectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DonacionService
 *
 * Servicio que encapsula la lógica de negocio relacionada con las donaciones.
 * Se encarga de mapear entre Donaciones (entidad JPA) y DonacionDto, y de
 * llamar al DonacionRepository para persistir/consultar datos.
 */
@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    @Autowired
    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    /**
     * Crea/Guarda una nueva donación pendiente de autorización.
     *
     * @param donacionDto DTO con los datos de la donación a crear.
     * @return DonacionDto con los datos guardados (incluyendo ID y fechaCreacion).
     */
    public DonacionDto crearDonacion(DonacionDto donacionDto) {
        // Mapea de DTO a entidad
        Donaciones entidad = new Donaciones();

        // Asignamos usuario y proyecto (solo establecemos el ID porque JPA hará el resto)
        Usuarios usuario = new Usuarios();
        usuario.setId(donacionDto.getUsuarioId());
        entidad.setUsuario(usuario);

        Proyectos proyecto = new Proyectos();
        proyecto.setIdProyecto(donacionDto.getProyectoId());
        entidad.setProyecto(proyecto);

        entidad.setAuthorizationId(donacionDto.getAuthorizationId());
        entidad.setOrderId(donacionDto.getOrderId());
        entidad.setAmount(donacionDto.getAmount());
        entidad.setEstado(donacionDto.getEstado());

        // Guardamos la fecha de creación ahora (server time)
        entidad.setFechaCreacion(LocalDateTime.now());

        // Persistimos en BD
        Donaciones guardada = donacionRepository.save(entidad);

        // Mapeamos de entidad a DTO y devolvemos
        return mapEntidadADto(guardada);
    }

    /**
     * Busca todas las donaciones de un proyecto con un estado dado.
     *
     * @param proyectoId ID del proyecto.
     * @param estado     Estado de la donación (por ejemplo, "PENDIENTE_AUTORIZACION").
     * @return Lista de DonacionDto correspondientes.
     */
    public List<DonacionDto> buscarPorProyectoYEstado(Long proyectoId, String estado) {
        List<Donaciones> listaEntidades = donacionRepository
                .findByProyecto_IdProyectoAndEstado(proyectoId, estado);

        return listaEntidades.stream()
                .map(this::mapEntidadADto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una donación existente (por ejemplo, para cambiar el estado a CAPTURADA).
     *
     * @param donacionDto DTO con la información actualizada (se necesita al menos el ID).
     * @return DonacionDto actualizado.
     */
    public DonacionDto actualizarDonacion(DonacionDto donacionDto) {
        // Primero buscamos la entidad en BD
        Donaciones entidadExistente = donacionRepository
                .findById(donacionDto.getIdDonacion())
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + donacionDto.getIdDonacion()));

        // Actualizamos solo los campos que cambian (por ejemplo, estado y/o captureId si quieres agregarlo)
        entidadExistente.setEstado(donacionDto.getEstado());
        // Si en algún momento guardas captureId, hobby de implementación
        // entidadExistente.setCaptureId(donacionDto.getCaptureId());

        Donaciones actualizada = donacionRepository.save(entidadExistente);
        return mapEntidadADto(actualizada);
    }

    /**
     * Mapea una entidad Donaciones a su DTO DonacionDto.
     */
    private DonacionDto mapEntidadADto(Donaciones entidad) {
        DonacionDto dto = new DonacionDto();
        dto.setIdDonacion(entidad.getIdDonacion());
        dto.setUsuarioId(entidad.getUsuario().getId());
        dto.setProyectoId(entidad.getProyecto().getIdProyecto());
        dto.setAuthorizationId(entidad.getAuthorizationId());
        dto.setOrderId(entidad.getOrderId());
        dto.setAmount(entidad.getAmount());
        dto.setEstado(entidad.getEstado());
        dto.setFechaCreacion(entidad.getFechaCreacion());
        return dto;
    }
}
