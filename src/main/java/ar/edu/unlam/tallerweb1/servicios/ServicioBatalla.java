package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.ObjetosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Batalla;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;

public interface ServicioBatalla {

	public void finalBatalla(Long duracion, DatosPokemonBatalla[] datosPokemons, Long idUsuario,
			String[] objetosUtilizados);

	public void inicioBatalla(List<Long> pokemonsLista, List<Long> objetosLista, Long idUsuario)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException;

	public List<Batalla> obtenerBatallasUsuario(Long idUsuario);

	public void obtenerPokemonsBatalla(Batalla batalla);

}
