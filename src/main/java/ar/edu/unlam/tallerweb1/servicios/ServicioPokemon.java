package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	void guardarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso) throws IOException, NombreExistenteException;

	Pokemon buscarPokemon(Long id);
	
	Pokemon buscarPokemonPorNombre(String nombre);

	List<Pokemon> obtenerTodosLosPokemons();

	void modificarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso);

	void borrarPokemon(Long id);

	List<Pokemon> buscarPokemonPorGrupo(String[] pokemonsTraidos);

}
