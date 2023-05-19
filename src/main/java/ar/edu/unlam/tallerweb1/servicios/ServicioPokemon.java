package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	void guardarPokemon(Pokemon pokemon, List<Long> ataques, MultipartFile frente, MultipartFile dorso) throws IOException, NombreExistenteException, SpriteNoIngresadoException;

	Pokemon buscarPokemon(Long id);
	
	Pokemon buscarPokemon(String nombre);

	List<Pokemon> obtenerTodosLosPokemons();

	void modificarPokemon(Pokemon pokemon, List<Long> ataques, MultipartFile frente, MultipartFile dorso, String nombreAnterior, List<Long> ataquesAprendidos) throws IOException, NombreExistenteException, SpriteNoIngresadoException;;

	void borrarPokemon(Long id);

	List<Pokemon> buscarPokemonPorGrupo(String[] pokemonsTraidos);

}
