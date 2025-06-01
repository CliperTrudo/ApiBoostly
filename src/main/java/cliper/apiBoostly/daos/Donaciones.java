package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una donación en el sistema.
 * Esta clase está mapeada a la tabla 'donaciones' en la base de datos.
 * 
 * Cada donación está asociada a un usuario (quien dona) y a un proyecto (al que se dona).
 * Utilizamos el campo authorizationId para almacenar la autorización de PayPal,
 * y el estado inicial es "PENDIENTE_AUTORIZACION". Más adelante podremos capturar
 * (charge) o anular (void) esa autorización según la lógica all‐or‐nothing.
 * 
 * Campos principales:
 * - idDonacion: clave primaria auto-generada
 * - usuario: referencia a la entidad Usuarios
 * - proyecto: referencia a la entidad Proyectos
 * - authorizationId: identificador de autorización en PayPal
 * - orderId: identificador de la orden en PayPal
 * - amount: importe de la donación
 * - estado: estado del flujo ("PENDIENTE_AUTORIZACION", "CAPTURADA", "ANULADA", "FALLIDA", etc.)
 * - fechaCreacion: timestamp de cuándo se autorizó la donación
 */
@Entity
@Table(name = "donaciones")
public class Donaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donacion")
    private Long idDonacion;

    /**
     * Relación ManyToOne a la tabla 'usuarios'.
     * Se asume que la entidad de usuario se llama 'Usuarios' y está en el mismo paquete.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    /**
     * Relación ManyToOne a la tabla 'proyectos'.
     * Se asume que la entidad de proyecto se llama 'Proyectos' y está en el mismo paquete.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyectos proyecto;

    /**
     * Almacena la authorizationId que devuelve PayPal cuando se autoriza la orden.
     * Con este ID podremos capturar la donación más adelante.
     */
    @Column(name = "authorization_id", length = 255, nullable = false)
    private String authorizationId;

    /**
     * Almacena el orderId (token) que devuelve PayPal para esta orden.
     */
    @Column(name = "order_id", length = 255, nullable = false)
    private String orderId;

    /**
     * Importe de la donación (por ejemplo, 25.00, 10.50, etc.)
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * Estado de la donación dentro del flujo all‐or‐nothing.
     * Por ejemplo: "PENDIENTE_AUTORIZACION", "CAPTURADA", "ANULADA", "FALLIDA", etc.
     */
    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    /**
     * Fecha y hora en que se creó/la autorización fue generada.
     * Útil para auditoría o para saber cuándo expira la autorización.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    /* ---------------------------------- */
    /* Getters y Setters */
    /* ---------------------------------- */

    public Long getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Long idDonacion) {
        this.idDonacion = idDonacion;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * toString para depuración: muestra información básica de la donación.
     */
    @Override
    public String toString() {
        return "Donacion{" +
                "idDonacion=" + idDonacion +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", proyecto=" + (proyecto != null ? proyecto.getIdProyecto() : null) +
                ", authorizationId='" + authorizationId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
