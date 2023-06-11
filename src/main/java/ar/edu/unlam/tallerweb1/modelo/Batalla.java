package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Batalla {

	public Batalla(String duracion, Date fecha, String resultado, Usuario usuario) {
		this.duracion = duracion;
		this.fecha = fecha;
		this.resultado = resultado;
		this.usuario = usuario;
	}

	public Batalla() {
		// Default
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String duracion;
	private Date fecha;
	private String resultado;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Usuario usuario;
	@Transient
	private List<PokemonBatalla> pokemonsUsuario;
	@Transient
	private List<PokemonBatalla> pokemonsCpu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<PokemonBatalla> getPokemonsUsuario() {
		return pokemonsUsuario;
	}

	public void setPokemonsUsuario(List<PokemonBatalla> pokemonsUsuario) {
		this.pokemonsUsuario = pokemonsUsuario;
	}

	public List<PokemonBatalla> getPokemonsCpu() {
		return pokemonsCpu;
	}

	public void setPokemonsCpu(List<PokemonBatalla> pokemonsCpu) {
		this.pokemonsCpu = pokemonsCpu;
	}
}
