package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioAtaquePokemon {
	
	void guardar(AtaquePokemon ataquePokemon);

	void borrar(AtaquePokemon ataquePokemon);
	void borrar(Long idAtaque, Long idPokemon);

	List<Ataque> obtenerListaDeAtaques(Long idPokemon);

	List<Long> obtenetDesbloqueados(Long idPokemon);

	List<Long> obtenetBloqueados(Long idPokemon);

	List<AtaquePokemon> obtenerLista(Long idPokemon);

	List<AtaquePokemon> obtenerTodos();
}
