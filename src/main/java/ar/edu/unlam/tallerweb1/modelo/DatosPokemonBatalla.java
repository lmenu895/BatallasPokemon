package ar.edu.unlam.tallerweb1.modelo;

public class DatosPokemonBatalla {

	private Long id;
	private Boolean debilitado;
	private String entrenador;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getDebilitado() {
		return debilitado;
	}
	public void setDebilitado(Boolean debilitado) {
		this.debilitado = debilitado;
	}
	public String getEntrenador() {
		return entrenador;
	}
	public void setEntrenador(String entrenador) {
		this.entrenador = entrenador;
	}
	
}
