package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa un proyecto en el sistema.
 * Esta clase está mapeada a la tabla 'Proyectos' en la base de datos.
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

    /**
     * Ahora reemplazamos el Boolean estadoProyecto
     * por una relación ManyToOne a EstadoProyecto.
     */
    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoProyecto idEstado;

    // Relación con la tabla `categorias`
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoriaProyecto; // Ahora es referencia a entidad Categoria

    public Proyectos() { }

    public Proyectos(
        Usuarios usuario,
        String nombreProyecto,
        String descripcionProyecto,
        byte[] imagen1Proyecto,
        byte[] imagen2Proyecto,
        byte[] imagen3Proyecto,
        LocalDateTime fechaInicioProyecto,
        LocalDate fechaFinalizacionProyecto,
        Double metaRecaudacionProyecto,
        EstadoProyecto estadoProyecto,
        Categoria categoriaProyecto
    ) {
        this.usuario = usuario;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.imagen1Proyecto = imagen1Proyecto;
        this.imagen2Proyecto = imagen2Proyecto;
        this.imagen3Proyecto = imagen3Proyecto;
        this.fechaInicioProyecto = fechaInicioProyecto;
        this.fechaFinalizacionProyecto = fechaFinalizacionProyecto;
        this.metaRecaudacionProyecto = metaRecaudacionProyecto;
        this.idEstado = estadoProyecto;
        this.categoriaProyecto = categoriaProyecto;
    }

    // Getters y setters de todos los campos:

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

    public EstadoProyecto getEstadoProyecto() {
        return idEstado;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.idEstado = estadoProyecto;
    }

    public Categoria getCategoriaProyecto() {
        return categoriaProyecto;
    }

    public void setCategoriaProyecto(Categoria categoriaProyecto) {
        this.categoriaProyecto = categoriaProyecto;
    }

    @Override
    public String toString() {
        return "Proyectos{" +
               "idProyecto=" + idProyecto +
               ", usuario=" + (usuario != null ? usuario.getId() : null) +
               ", nombreProyecto='" + nombreProyecto + '\'' +
               ", descripcionProyecto='" + descripcionProyecto + '\'' +
               ", fechaInicioProyecto=" + fechaInicioProyecto +
               ", fechaFinalizacionProyecto=" + fechaFinalizacionProyecto +
               ", metaRecaudacionProyecto=" + metaRecaudacionProyecto +
               ", estadoProyecto=" + (idEstado != null ? idEstado.getNombreEstado() : null) +
               ", categoriaProyecto=" + (categoriaProyecto != null ? categoriaProyecto.getNombreCategoria() : null) +
               '}';
    }
}
