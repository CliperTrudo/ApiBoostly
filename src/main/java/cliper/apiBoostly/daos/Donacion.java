package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "donaciones")
public class Donacion {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal importe;

    @Column(nullable = false, length = 3)
    private String moneda;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column
    private String payerId;

    @Column
    private String payerEmail;

    @Column(nullable = false, updatable = false)
    private Instant creadoEn;

    @Column
    private Instant capturadoEn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public Instant getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Instant creadoEn) {
		this.creadoEn = creadoEn;
	}

	public Instant getCapturadoEn() {
		return capturadoEn;
	}

	public void setCapturadoEn(Instant capturadoEn) {
		this.capturadoEn = capturadoEn;
	}

	public Donacion(Long id, String orderId, BigDecimal importe, String moneda, String estado, String payerId,
			String payerEmail, Instant creadoEn, Instant capturadoEn) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.importe = importe;
		this.moneda = moneda;
		this.estado = estado;
		this.payerId = payerId;
		this.payerEmail = payerEmail;
		this.creadoEn = creadoEn;
		this.capturadoEn = capturadoEn;
	}

	public Donacion() {
		super();
	}

	@Override
	public String toString() {
		return "Donacion [id=" + id + ", orderId=" + orderId + ", importe=" + importe + ", moneda=" + moneda
				+ ", estado=" + estado + ", payerId=" + payerId + ", payerEmail=" + payerEmail + ", creadoEn="
				+ creadoEn + ", capturadoEn=" + capturadoEn + "]";
	}

    

}
