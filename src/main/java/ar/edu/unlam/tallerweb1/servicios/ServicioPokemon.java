package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	void guardar(DatosPokemon datosPokemon) throws IOException, NombreExistenteException, SpriteNoIngresadoException;

	Pokemon buscar(Long id);
	
	Pokemon buscar(String nombre);

	List<Pokemon> obtenerTodos();

	void modificar(DatosPokemon datosPokemon, Long idPokemon) throws IOException, NombreExistenteException, SpriteNoIngresadoException;;

	void borrar(Long id);

	List<Pokemon> buscarPorGrupo(String[] pokemonsTraidos);

	List<Pokemon> crearEquipoCpu(HttpServletRequest request);

	List<Pokemon> obtenerTodosLosComunes();
}
