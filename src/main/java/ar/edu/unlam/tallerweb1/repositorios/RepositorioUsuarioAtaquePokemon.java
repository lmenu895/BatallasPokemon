package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;

public interface RepositorioUsuarioAtaquePokemon {

	List<UsuarioAtaquePokemon> obtenerAtaques(Long idUsuario, Long idPokemon);

	void guardar(UsuarioAtaquePokemon ataquePokemonUsuario);

	List<UsuarioAtaquePokemon> obtenerListaDeAtaquesActivos(Long idPokemon, Long idUsuario);

	UsuarioAtaquePokemon buscar(Long idUAP);

	List<UsuarioAtaquePokemon> obtenerAtaquesPorPokemon(Long idAtaque, Long idPokemon);

	void borrar(UsuarioAtaquePokemon uap);
}
