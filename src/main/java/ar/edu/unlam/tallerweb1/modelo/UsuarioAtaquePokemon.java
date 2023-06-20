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
	
	public UsuarioAtaquePokemon(Usuario usuario, Pokemon pokemon, Ataque ataque, Boolean bloqueado, Boolean activo) {
		this.usuario = usuario;
		this.pokemon = pokemon;
		this.ataque = ataque;
		this.bloqueado = bloqueado;
		this.activo = activo;
	}
	
	public UsuarioAtaquePokemon() {
		//Default
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Usuario usuario;
	@ManyToOne(fetch = FetchType.LAZY)
	private Pokemon pokemon;
	@ManyToOne(cascade = CascadeType.ALL)
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
}
