package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	void guardarPokemon(DatosPokemon datosPokemon) throws IOException, NombreExistenteException, SpriteNoIngresadoException;

	Pokemon buscarPokemon(Long id);
	
	Pokemon buscarPokemon(String nombre);

	List<Pokemon> obtenerTodosLosPokemons();

	void modificarPokemon(DatosPokemon datosPokemon, Long idPokemon) throws IOException, NombreExistenteException, SpriteNoIngresadoException;;

	void borrarPokemon(Long id);

	List<Pokemon> buscarPokemonPorGrupo(String[] pokemonsTraidos);

	List<Pokemon> crearEquipoCpu(HttpServletRequest request);

	List<Pokemon> obtenerTodosLosPokemonsComunes();

	Pokemon buscarPokemonString(String nombre);


}
