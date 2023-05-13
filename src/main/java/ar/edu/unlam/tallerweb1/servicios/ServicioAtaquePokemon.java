package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface ServicioAtaquePokemon {
	
	void guardarAtaque(AtaquePokemon ataquePokemon);

	List<Ataque> obtenerListaDeAtaquePokemon(Long idPokemon);

	void borrarAtaquePokemon(AtaquePokemon ataquePokemon);
	void borrarAtaquePokemon(Long idAtaque, Long idPokemon);
}
