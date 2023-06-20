package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;

public interface ServicioUsuarioAtaquePokemon {

	List<UsuarioAtaquePokemon> obtenerAtaques(Long idUsuario, Long idPokemon);

	void guardarAtaquePokemonUsuario(AtaquePokemon ataquePokemon, Usuario usuario);

	List<Ataque> obtenerListaDeAtaquesActivos(Long idPokemon, Long idUsuario);
	
}
