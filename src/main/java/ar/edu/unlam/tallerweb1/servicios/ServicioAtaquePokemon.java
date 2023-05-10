package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface ServicioAtaquePokemon {

	List<Ataque> buscarAtaques(Long id);
	
	void guardarAtaques(AtaquePokemon ataquePokemon);

}
