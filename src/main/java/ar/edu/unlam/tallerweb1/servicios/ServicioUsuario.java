package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {

	void guardarEquipo(String[] pokemons,Long id);

	Usuario buscarUsuario(Long idUsuario);
	
	public List<Pokemon> obtenerListaDePokemons(Long idUsuario);

	public List<Objeto> obtenerListaDeObjetos(Long attribute);
	
}