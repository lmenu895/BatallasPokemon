package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UsuarioPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Plan plan;
	
	private Integer duracion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	
	public UsuarioPlan withPlan(Plan plan) {
		this.setPlan(plan);
		return this;
	}
	public UsuarioPlan withUsuario(Usuario usuario) {
		this.setUsuario(usuario);
		return this;
	}
	public UsuarioPlan withDuracion(Integer duracion) {
		this.setDuracion(duracion);
		return this;
	}

}
