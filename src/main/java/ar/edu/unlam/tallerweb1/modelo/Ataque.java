package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

@Entity
public class Ataque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private TipoPokemon tipo;
	private Double potencia;
	private Double precataque;
	private Double pp;
	private Boolean efecto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoPokemon getTipo() {
		return tipo;
	}

	public void setTipo(TipoPokemon tipo) {
		this.tipo = tipo;
	}

	public Double getPotencia() {
		return potencia;
	}

	public void setPotencia(Double potencia) {
		this.potencia = potencia;
	}

	public Double getPrecataque() {
		return precataque;
	}

	public void setPrecataque(Double precataque) {
		this.precataque = precataque;
	}

	public Double getPp() {
		return pp;
	}

	public void setPp(Double pp) {
		this.pp = pp;
	}

	public Boolean getEfecto() {
		return efecto;
	}

	public void setEfecto(Boolean efecto) {
		this.efecto = efecto;
	}
}
