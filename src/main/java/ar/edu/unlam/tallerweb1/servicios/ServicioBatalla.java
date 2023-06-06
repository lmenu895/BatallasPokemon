package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;

public interface ServicioBatalla {

	public void finalBatalla(String ganador, Long idUsuario);

	public void inicioBatalla(List<Long> pokemonsLista, String[] objetosLista)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException;

}
