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
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ataque> ataques;

	@Transient
	private String[] cv;

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

	public List<Ataque> getAtaques() {
		return ataques;
	}

	public void setAtaques(List<Ataque> ataques) {
		this.ataques = ataques;
	}

	public String[] getCv() {
		return cv;
	}

	public void setCv(String[] cv) {
		this.cv = cv;
	}
	
}
