package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa un proyecto en el sistema.
 * Esta clase está mapeada a la tabla 'Proyectos' en la base de datos.
 * @author Sergio Alfonseca
 */
@Entity
@Table(name = "Proyectos")
public class Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    // Relación ManyToOne: cada proyecto está asociado al usuario creador.
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    @Column(name = "nombre_proyecto", nullable = false, length = 255)
    private String nombreProyecto;

    @Column(name = "descripcion_proyecto", nullable = false, length = 1000)
    private String descripcionProyecto;

    // Imágenes almacenadas en la base de datos (como BLOB o byte[])
    @Lob
    @Column(name = "imagen1_proyecto", columnDefinition = "LONGBLOB")
    private byte[] imagen1Proyecto;

    @Lob
    @Column(name = "imagen2_proyecto", columnDefinition = "LONGBLOB")
    private byte[] imagen2Proyecto;

    @Lob
    @Column(name = "imagen3_proyecto", columnDefinition = "LONGBLOB")
    private byte[] imagen3Proyecto;

    // Fecha de inicio como LocalDateTime (momento de creación del proyecto)
    @Column(name = "fecha_inicio_proyecto", nullable = false)
    private LocalDateTime fechaInicioProyecto;

    // Fecha de finalización como LocalDate; debe ser posterior a la fecha de inicio
    @Column(name = "fecha_finalizacion_proyecto")
    private LocalDate fechaFinalizacionProyecto;

    // Meta de recaudación: número positivo
    @Column(name = "meta_recaudacion_proyecto", nullable = false)
    private Double metaRecaudacionProyecto;

    // Estado del proyecto: true = activo, false = inactivo/finalizado
    @Column(name = "estado_proyecto", nullable = false)
    private Boolean estadoProyecto;

    // Relación con la tabla `categorias`
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoriaProyecto; // Ahora es una referencia a la entidad Categoria

    // Constructor vacío requerido por JPA
    public Proyectos() {}

    // Constructor completo (sin id, que se genera automáticamente)
    public Proyectos(Usuarios usuario, String nombreProyecto, String descripcionProyecto,
                    byte[] imagen1Proyecto, byte[] imagen2Proyecto, byte[] imagen3Proyecto,
                    LocalDateTime fechaInicioProyecto, LocalDate fechaFinalizacionProyecto,
                    Double metaRecaudacionProyecto, Boolean estadoProyecto, Categoria categoriaProyecto) {
        this.usuario = usuario;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.imagen1Proyecto = imagen1Proyecto;
        this.imagen2Proyecto = imagen2Proyecto;
        this.imagen3Proyecto = imagen3Proyecto;
        this.fechaInicioProyecto = fechaInicioProyecto;
        this.fechaFinalizacionProyecto = fechaFinalizacionProyecto;
        this.metaRecaudacionProyecto = metaRecaudacionProyecto;
        this.estadoProyecto = estadoProyecto;
        this.categoriaProyecto = categoriaProyecto; // Referencia a la entidad Categoria
    }

    /**
     * Métodos Getters y Setters:
     * - getIdProyecto() y setIdProyecto(): Obtienen y establecen el ID del proyecto.
     * - getUsuario() y setUsuario(): Obtienen y establecen el usuario creador del proyecto.
     * - getNombreProyecto() y setNombreProyecto(): Obtienen y establecen el nombre del proyecto.
     * - getDescripcionProyecto() y setDescripcionProyecto(): Obtienen y establecen la descripción del proyecto.
     * - getImagen1Proyecto(), getImagen2Proyecto(), getImagen3Proyecto() y sus respectivos setters: 
     *   Obtienen y establecen las imágenes del proyecto.
     * - getFechaInicioProyecto() y setFechaInicioProyecto(): Obtienen y establecen la fecha de inicio del proyecto.
     * - getFechaFinalizacionProyecto() y setFechaFinalizacionProyecto(): Obtienen y establecen la fecha de finalización del proyecto.
     * - getMetaRecaudacionProyecto() y setMetaRecaudacionProyecto(): Obtienen y establecen la meta de recaudación del proyecto.
     * - getEstadoProyecto() y setEstadoProyecto(): Obtienen y establecen el estado del proyecto (activo o inactivo).
     * - getCategoriaProyecto() y setCategoriaProyecto(): Obtienen y establecen la categoría asociada al proyecto.
     */
    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public byte[] getImagen1Proyecto() {
        return imagen1Proyecto;
    }

    public void setImagen1Proyecto(byte[] imagen1Proyecto) {
        this.imagen1Proyecto = imagen1Proyecto;
    }

    public byte[] getImagen2Proyecto() {
        return imagen2Proyecto;
    }

    public void setImagen2Proyecto(byte[] imagen2Proyecto) {
        this.imagen2Proyecto = imagen2Proyecto;
    }

    public byte[] getImagen3Proyecto() {
        return imagen3Proyecto;
    }

    public void setImagen3Proyecto(byte[] imagen3Proyecto) {
        this.imagen3Proyecto = imagen3Proyecto;
    }

    public LocalDateTime getFechaInicioProyecto() {
        return fechaInicioProyecto;
    }

    public void setFechaInicioProyecto(LocalDateTime fechaInicioProyecto) {
        this.fechaInicioProyecto = fechaInicioProyecto;
    }

    public LocalDate getFechaFinalizacionProyecto() {
        return fechaFinalizacionProyecto;
    }

    public void setFechaFinalizacionProyecto(LocalDate fechaFinalizacionProyecto) {
        this.fechaFinalizacionProyecto = fechaFinalizacionProyecto;
    }

    public Double getMetaRecaudacionProyecto() {
        return metaRecaudacionProyecto;
    }

    public void setMetaRecaudacionProyecto(Double metaRecaudacionProyecto) {
        this.metaRecaudacionProyecto = metaRecaudacionProyecto;
    }

    public Boolean getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(Boolean estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Categoria getCategoriaProyecto() {
        return categoriaProyecto;
    }

    public void setCategoriaProyecto(Categoria categoriaProyecto) {
        this.categoriaProyecto = categoriaProyecto;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String del proyecto, incluyendo el ID del usuario y el nombre de la categoría.
     */
    @Override
    public String toString() {
        return "Proyecto{" + "idProyecto=" + idProyecto + ", usuario=" + (usuario != null ? usuario.getId() : null)
                + ", nombreProyecto='" + nombreProyecto + '\'' + ", descripcionProyecto='" + descripcionProyecto + '\'' 
                + ", fechaInicioProyecto=" + fechaInicioProyecto + ", fechaFinalizacionProyecto=" + fechaFinalizacionProyecto
                + ", metaRecaudacionProyecto=" + metaRecaudacionProyecto + ", estadoProyecto=" + estadoProyecto
                + ", categoriaProyecto=" + (categoriaProyecto != null ? categoriaProyecto.getNombreCategoria() : null) + '}';
    }
}
