package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idPago;
	private LocalDateTime fechaAprobado;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	public LocalDateTime getFechaAprobado() {
		return fechaAprobado;
	}

	public void setFechaAprobado(LocalDateTime fechaAprobado) {
		this.fechaAprobado = fechaAprobado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pago withIdPago(Long idPago) {
		this.setIdPago(idPago);
		return this;
	}

	public Pago withFechaAprobado(LocalDateTime fechaAprobado) {
		this.setFechaAprobado(fechaAprobado);
		return this;
	}

	public Pago withUsuario(Usuario usuario) {
		this.setUsuario(usuario);
		return this;
	}
}
