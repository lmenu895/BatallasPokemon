package ar.edu.unlam.tallerweb1.repositorios;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface RepositorioPokemon {

	void guardar(Pokemon pokemon);

	Pokemon buscar(String nombre);

	Pokemon buscar(Long id);

	List<Pokemon> obtenerTodos();
	
	List<Pokemon> obtenerPorRareza(int rareza);

	void modificarPokemon(Pokemon pokemon);

	void borrar(Long id);

	ArrayList<Pokemon> buscarPorRareza(int rareza);
	
}
