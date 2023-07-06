package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.exceptions.PokemonNoObtenidoException;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

public interface ServicioUsuarioPokemon {

	Boolean guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon, Long idUsuario, Long idPokemon, Usuario usuario, Pokemon pokemon);

	List<UsuarioPokemon> obtenerListaDeUsuarioPokemon(Long idUsuario);

	void borrarUsuarioDeUnPokemon(Long idUsuario);
	
	void guardarEquipo(UsuarioPokemon usuarioPokemon);

	List<Pokemon> buscarPokemons(List<UsuarioPokemon> lista);

	void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon);

	UsuarioPokemon buscarPokemonUsuario(Long idPokemon, Long idUsuario) throws PokemonNoObtenidoException;

	Pokemon buscarPokemon(Long idPokemon, Long idUsuario) throws PokemonNoObtenidoException;

	List<Pokemon> obtenerListaDePokemonSinPokemonUsuario(Long idUsuario);
}