package ar.edu.unlam.tallerweb1.modelo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DatosPokemon {
	
	private String nombre;
	private TipoPokemon tipo;
	private RarezaPokemon rareza;
	private Double vida;
	private Double velocidad;
	private MultipartFile imagenFrente;
	private MultipartFile imagenDorso;
	private List<Long> ataquesDesbloqueados;
	private List<Long> ataquesBloqueados;
	
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
	public RarezaPokemon getRareza() {
		return rareza;
	}
	public void setRareza(RarezaPokemon rareza) {
		this.rareza = rareza;
	}
	public Double getVida() {
		return vida;
	}
	public void setVida(Double vida) {
		this.vida = vida;
	}
	public Double getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(Double velocidad) {
		this.velocidad = velocidad;
	}
	public MultipartFile getImagenFrente() {
		return imagenFrente;
	}
	public void setImagenFrente(MultipartFile imagenFrente) {
		this.imagenFrente = imagenFrente;
	}
	public MultipartFile getImagenDorso() {
		return imagenDorso;
	}
	public void setImagenDorso(MultipartFile imagenDorso) {
		this.imagenDorso = imagenDorso;
	}
	public List<Long> getAtaquesDesbloqueados() {
		return ataquesDesbloqueados;
	}
	public void setAtaquesDesbloqueados(List<Long> ataquesDesbloqueados) {
		this.ataquesDesbloqueados = ataquesDesbloqueados;
	}
	public List<Long> getAtaquesBloqueados() {
		return ataquesBloqueados;
	}
	public void setAtaquesBloqueados(List<Long> ataquesBloqueados) {
		this.ataquesBloqueados = ataquesBloqueados;
	}
}
