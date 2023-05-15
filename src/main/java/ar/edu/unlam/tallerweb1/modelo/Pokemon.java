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
	private Double vida;
	private Double velocidad;
	private String imagenFrente;
	private String imagenDorso;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pokemon" ,cascade = CascadeType.ALL)
	private List<AtaquePokemon> ataques;

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

	public List<AtaquePokemon> getAtaques() {
		return ataques;
	}

	public void setAtaques(List<AtaquePokemon> ataques) {
		this.ataques = ataques;
	}

	public Double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Double velocidad) {
		this.velocidad = velocidad;
	}
}
