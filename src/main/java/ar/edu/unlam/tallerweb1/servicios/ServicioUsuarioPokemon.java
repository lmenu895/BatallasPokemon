package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

public interface ServicioUsuarioPokemon {

	Boolean guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon, Long idUsuario, Long idPokemon, Usuario usuario, Pokemon pokemon);

	List<UsuarioPokemon> obtenerListaDeUsuarioPokemon(Long idUsuario);

	void borrarUsuarioDeUnPokemon(Long idUsuario);
	
	void guardarEquipo(UsuarioPokemon usuarioPokemon);

	List<Pokemon> buscarPokemon(List<UsuarioPokemon> lista);
}