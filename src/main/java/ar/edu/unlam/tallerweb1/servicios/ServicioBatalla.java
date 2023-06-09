package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import java.util.Map;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;

public interface ServicioBatalla {

	public void finalBatalla(Long duracion, DatosPokemonBatalla[] listaDatosPokemons, Long idUsuario);

	public void inicioBatalla(List<Long> pokemonsLista, String[] objetosLista)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException;

}
