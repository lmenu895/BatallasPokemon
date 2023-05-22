package ar.edu.unlam.tallerweb1.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import javax.persistence.*;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface RepositorioPokemon {

	void guardarPokemon(Pokemon pokemon);

	Pokemon buscarPokemon(String nombre);

	Pokemon buscarPokemon(Long id);

	List<Pokemon> obtenerTodosLosPokemons();

	void modificarPokemon(Pokemon pokemon);

	void borrarPokemon(Long id);

	ArrayList<Pokemon> buscarPorRareza(int rareza);
	
}
