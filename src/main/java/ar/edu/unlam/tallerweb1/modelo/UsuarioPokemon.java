package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

@Entity
public class UsuarioPokemon {

	public UsuarioPokemon(Usuario usuario, Pokemon pokemon) {
		this.usuario = usuario;
		this.pokemon = pokemon;
	}

	public UsuarioPokemon() {}

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
}