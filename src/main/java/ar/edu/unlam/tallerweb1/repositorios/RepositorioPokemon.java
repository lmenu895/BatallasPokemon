package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface RepositorioPokemon {

	void guardarPokemon(Pokemon pokemon);

	Pokemon buscarPokemonPorNombre(String nombre);

	Pokemon buscarPokemon(Long id);

}
