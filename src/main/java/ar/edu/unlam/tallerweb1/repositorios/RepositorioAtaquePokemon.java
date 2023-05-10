package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

public interface RepositorioAtaquePokemon {

	void guardarAtaques(AtaquePokemon ataques);

	List<AtaquePokemon> buscarAtaques(Long id);

}
