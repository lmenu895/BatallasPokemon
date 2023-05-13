package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

public interface ServicioUsuarioPokemon {

	void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon);

	List<UsuarioPokemon> obtenerListaDeUsuarioPokemon(Long idUsuario);

	void borrarUsuarioDeUnPokemon(Long idUsuario);
	
	void guardarEquipo(UsuarioPokemon usuarioPokemon);

	List<Pokemon> buscarPokemon(List<UsuarioPokemon> lista);
}