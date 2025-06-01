package cliper.apiBoostly.daos;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_proyecto")
public class EstadoProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_proyecto")
    private Long id;

    @Column(name = "nombre_estado", length = 50, nullable = false)
    private String nombreEstado;

    public EstadoProyecto() { }

    public EstadoProyecto(Long id, String nombreEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
