package ar.edu.unlam.tallerweb1.modelo;

import java.util.List;

import javax.persistence.*;

@Entity
public class AtaquePokemon {
	
	public AtaquePokemon(Pokemon pokemon, Ataque ataque) {
		super();
		this.pokemon = pokemon;
		this.ataque = ataque;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Pokemon pokemon;

	@ManyToOne
	private Ataque ataque;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Ataque getAtaque() {
		return ataque;
	}

	public void setAtaque(Ataque ataque) {
		this.ataque = ataque;
	}
}
