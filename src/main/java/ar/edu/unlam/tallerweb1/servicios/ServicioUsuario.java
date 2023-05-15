package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {

	void guardarEquipo(String[] pokemons,Long id);
	
}