package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface ServicioAtaquePokemon {
	
	void guardarAtaque(AtaquePokemon ataquePokemon);

	void borrarAtaquePokemon(AtaquePokemon ataquePokemon);
	void borrarAtaquePokemon(Long idAtaque, Long idPokemon);

	List<Ataque> obtenerListaDeAtaques(Long idPokemon);

	List<Long> obtenetAtaquesDesbloqueados(Long idPokemon);

	List<Long> obtenetAtaquesBloqueados(Long idPokemon);
}
