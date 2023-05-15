package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

public interface RepositorioUsuarioPokemon {

	void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon);

	List<UsuarioPokemon> buscarPokemon(Long idUsuario);
	
	

}