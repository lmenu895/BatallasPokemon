package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface RepositorioAtaquePokemon {

	void guardar(AtaquePokemon ataques);

	List<AtaquePokemon> buscar(Long id);

	void borrar(AtaquePokemon ataquePokemon);

	void borrar(Long idAtaque, Long idPokemon);

	List<AtaquePokemon> buscarDesbloqueados(Long idPokemon);

	List<AtaquePokemon> buscarBloqueados(Long idPokemon);

	List<AtaquePokemon> obtenerTodos();
	
}
