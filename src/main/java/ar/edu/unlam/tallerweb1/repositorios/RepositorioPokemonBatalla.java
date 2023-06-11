package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;

public interface RepositorioPokemonBatalla {

	void guardarPokemonBatalla(PokemonBatalla pokemonBatalla);

	List<PokemonBatalla> obtenerPokemonsBatalla(Long idBatalla);

}
