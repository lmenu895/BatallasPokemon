package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface RepositorioPokemon {

	void guardarPokemon(Pokemon pokemon);

	Pokemon buscarPokemonPorNombre(String nombre);

	Pokemon buscarPokemon(Long id);

	List<Pokemon> obtenerTodosLosPokemons();

	void modificarPokemon(Pokemon pokemon);

	void borrarPokemon(Long id);

}
