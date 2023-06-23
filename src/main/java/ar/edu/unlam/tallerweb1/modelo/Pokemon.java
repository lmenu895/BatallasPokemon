package ar.edu.unlam.tallerweb1.modelo;

import java.util.List;

import javax.persistence.*;

@Entity
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nombre;
	private TipoPokemon tipo;
	private RarezaPokemon rareza;
	private Double vida;
	private Double velocidad;
	private String imagenFrente;
	private String imagenDorso;
	@Transient
	private List<Ataque> ataques;

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

	public Double getVida() {
		return vida;
	}

	public void setVida(Double vida) {
		this.vida = vida;
	}

	public String getImagenFrente() {
		return imagenFrente;
	}

	public void setImagenFrente(String imagenFrente) {
		this.imagenFrente = imagenFrente;
	}

	public String getImagenDorso() {
		return imagenDorso;
	}

	public void setImagenDorso(String imagenDorso) {
		this.imagenDorso = imagenDorso;
	}

	public Double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Double velocidad) {
		this.velocidad = velocidad;
	}

	public List<Ataque> getAtaques() {
		return ataques;
	}

	public void setAtaques(List<Ataque> ataques) {
		this.ataques = ataques;
	}

	public RarezaPokemon getRareza() {
		return rareza;
	}

	public void setRareza(RarezaPokemon rareza) {
		this.rareza = rareza;
	}

	public Pokemon withNombre(String nombre) {
		this.setNombre(nombre);
		return this;
	}

	public Pokemon withTipo(TipoPokemon tipo) {
		this.setTipo(tipo);
		return this;
	}

	public Pokemon withRareza(RarezaPokemon rareza) {
		this.setRareza(rareza);
		return this;
	}

	public Pokemon withVida(Double vida) {
		this.setVida(vida);
		return this;
	}

	public Pokemon withVelocidad(Double velocidad) {
		this.setVelocidad(velocidad);
		return this;
	}

	public Pokemon withImagenFrente(String imagenFrente) {
		this.setImagenFrente(imagenFrente);
		return this;
	}

	public Pokemon withImagenDorso(String imagenDorso) {
		this.setImagenDorso(imagenDorso);
		return this;
	}

	public Pokemon withAtaques(List<Ataque> ataques) {
		this.setAtaques(ataques);
		return this;
	}
}
