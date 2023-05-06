package cosas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;

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
	public Double getPrecision() {
		return precataque;
	}
	public void setPrecision(Double precision) {
		this.precataque = precision;
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
