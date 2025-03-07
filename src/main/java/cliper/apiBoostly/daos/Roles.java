package cliper.apiBoostly.daos;

import jakarta.persistence.*;

/**
 * Clase que representa un rol en el sistema.
 * Esta clase está mapeada a la tabla 'roles' en la base de datos.
 * @author Sergio Alfonseca
 */
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre_rol", length = 255, nullable = false)
    private String nombreRol;

    @Column(name = "descripcion_rol", length = 255)
    private String descripcionRol;

    // Constructor vacío
    public Roles() {}

    // Constructor con parámetros
    public Roles(String nombreRol, String descripcionRol) {
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    /**
     * Métodos Getters y Setters:
     * - getIdRol() y setIdRol(): Obtienen y establecen el ID del rol.
     * - getNombreRol() y setNombreRol(): Obtienen y establecen el nombre del rol.
     * - getDescripcionRol() y setDescripcionRol(): Obtienen y establecen la descripción del rol.
     */
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String del rol, incluyendo el ID, nombre y descripción del rol.
     */
    @Override
    public String toString() {
        return "Roles{" +
                "idRol=" + idRol +
                ", nombreRol='" + nombreRol + '\'' +
                ", descripcionRol='" + descripcionRol + '\'' +
                '}';
    }
}
