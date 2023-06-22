package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UsuarioAtaquePokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Usuario usuario;
	@ManyToOne(fetch = FetchType.LAZY)
	private Pokemon pokemon;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Ataque ataque;

	private Boolean bloqueado;
	private Boolean activo;

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

	public Ataque getAtaque() {
		return ataque;
	}

	public void setAtaque(Ataque ataque) {
		this.ataque = ataque;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioAtaquePokemon withUsuario(Usuario usuario) {
		this.setUsuario(usuario);
		return this;
	}

	public UsuarioAtaquePokemon withPokemon(Pokemon pokemon) {
		this.setPokemon(pokemon);
		return this;
	}

	public UsuarioAtaquePokemon withAtaque(Ataque ataque) {
		this.setAtaque(ataque);
		return this;
	}

	public UsuarioAtaquePokemon withBloqueado(Boolean bloqueado) {
		this.setBloqueado(bloqueado);
		return this;
	}

	public UsuarioAtaquePokemon withActivo(Boolean activo) {
		this.setActivo(activo);
		return this;
	}
}
