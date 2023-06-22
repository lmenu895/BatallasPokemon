package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AtaquePokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Ataque ataque;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pokemon pokemon;
	private Boolean bloqueado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ataque getAtaque() {
		return ataque;
	}

	public void setAtaque(Ataque ataque) {
		this.ataque = ataque;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	public AtaquePokemon withAtaque(Ataque ataque) {
		this.setAtaque(ataque);
		return this;
	}
	
	public AtaquePokemon withPokemon(Pokemon pokemon) {
		this.setPokemon(pokemon);
		return this;
	}
	
	public AtaquePokemon withBloqueado(Boolean bloqueado) {
		this.setBloqueado(bloqueado);
		return this;
	}
}
