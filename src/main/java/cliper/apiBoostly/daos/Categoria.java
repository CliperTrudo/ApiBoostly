package cliper.apiBoostly.daos;

import jakarta.persistence.*;

/**
 * Clase que representa una categoría en el sistema.
 * Esta clase está mapeada a la tabla 'categorias' en la base de datos.
 * @author Sergio Alfonseca
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 255)
    private String nombreCategoria;

    @Column(name = "descripcion_categoria", nullable = false, length = 1000)
    private String descripcionCategoria;

    /**
     * Métodos Getters y Setters:
     * - getIdCategoria() y setIdCategoria(): Obtienen y establecen el ID de la categoría.
     * - getNombreCategoria() y setNombreCategoria(): Obtienen y establecen el nombre de la categoría.
     * - getDescripcionCategoria() y setDescripcionCategoria(): Obtienen y establecen la descripción de la categoría.
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String de la categoría.
     */
    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcionCategoria='" + descripcionCategoria + '\'' +
                '}';
    }
}
