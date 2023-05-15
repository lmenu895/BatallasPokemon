package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface RepositorioAtaquePokemon {

	void guardarAtaque(AtaquePokemon ataques);

	List<AtaquePokemon> buscarAtaques(Long id);

	void borrarAtaquePokemon(AtaquePokemon ataquePokemon);

	void borrarAtaquePokemon(Long idAtaque, Long idPokemon);
	
}
