package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

@Entity
public class UsuarioPokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Pokemon pokemon;

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

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public UsuarioPokemon withUsuario(Usuario usuario) {
		this.setUsuario(usuario);
		return this;
	}

	public UsuarioPokemon withPokemon(Pokemon pokemon) {
		this.setPokemon(pokemon);
		return this;
	}
}