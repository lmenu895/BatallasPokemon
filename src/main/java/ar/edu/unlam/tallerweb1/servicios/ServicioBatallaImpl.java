package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Batalla;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioBatalla;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemonBatalla;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

	private ServicioUsuario servicioUsuario;
	private ServicioPokemon servicioPokemon;
	private RepositorioBatalla repositorioBatalla;
	private RepositorioPokemonBatalla repositorioPokemonBatalla;

	@Autowired
	public ServicioBatallaImpl(ServicioUsuario servicioUsuario, ServicioPokemon servicioPokemon,
			RepositorioBatalla repositorioBatalla, RepositorioPokemonBatalla repositorioPokemonBatalla) {
		this.servicioUsuario = servicioUsuario;
		this.servicioPokemon = servicioPokemon;
		this.repositorioBatalla = repositorioBatalla;
		this.repositorioPokemonBatalla = repositorioPokemonBatalla;
	}

	@Override
	public void inicioBatalla(List<Long> pokemonsLista, String[] objetosLista)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException {
		if (pokemonsLista == null || pokemonsLista.size() != 3) {
			throw new PokemonsInsuficientesException("Debe seleccionar 3 pokémons");
		} else if (objetosLista != null && objetosLista.length > 3) {
			throw new ExcesoDeObjetosException("Solo puede seleccionar un máximo de 3 objetos");
		}
	}

	@Override
	public void finalBatalla(Long duracion, DatosPokemonBatalla[] listaDatosPokemons, Long idUsuario) {

		Integer pokemonsDebilitadosUsuario = 0;
		Integer pokemonsDebilitadosCpu = 0;
		for (DatosPokemonBatalla dato : listaDatosPokemons) {
			if (dato.getDebilitado()) {
				if (dato.getEntrenador().equals("usuario")) {
					pokemonsDebilitadosUsuario++;
				} else if (dato.getEntrenador().equals("cpu")) {
					pokemonsDebilitadosCpu++;
				}
			}
		}
		if (pokemonsDebilitadosCpu == 3) {
			this.servicioUsuario.sumarPuntos(idUsuario, 20);
			guardarBatalla("Victoria", listaDatosPokemons, duracion, idUsuario);
		} else if (pokemonsDebilitadosUsuario == 3) {
			this.servicioUsuario.sumarPuntos(idUsuario, 6);
			guardarBatalla("Derrota", listaDatosPokemons, duracion, idUsuario);
		}
	}
	
	@Override
	public List<Batalla> obtenerBatallasUsuario(Long idUsuario) {
		List<Batalla> batallas = this.repositorioBatalla.obtenerBatallasUsuario(idUsuario);
		Collections.reverse(batallas);
		return batallas;
	}

	private void guardarBatalla(String resultado, DatosPokemonBatalla[] listaDatosPokemons, Long duracion,
			Long idUsuario) {
		Long minutos = (long) Math.floor(duracion / 60000);
		Long segundos = (long) Math.floor((duracion % 60000) / 1000);
		String duracionString = String.format("%02d:%02d", minutos, segundos);
		//String fecha = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
		Batalla batalla = new Batalla(duracionString, Calendar.getInstance().getTime(), resultado,
				this.servicioUsuario.buscarUsuario(idUsuario));
		this.repositorioBatalla.guardarBatalla(batalla);
		for (DatosPokemonBatalla dato : listaDatosPokemons) {
			this.repositorioPokemonBatalla
					.guardarPokemonBatalla(new PokemonBatalla(this.servicioPokemon.buscarPokemon(dato.getId()), batalla,
							dato.getDebilitado(), dato.getEntrenador()));
		}
	}
	
	@Override
	public void obtenerPokemonsBatalla(Batalla batalla) {
		List<PokemonBatalla> pokemonsUsuario = new ArrayList<>();
		List<PokemonBatalla> pokemonsCpu = new ArrayList<>();
		for (PokemonBatalla pokemonBatalla : this.repositorioPokemonBatalla.obtenerPokemonsBatalla(batalla.getId())) {
			if (pokemonBatalla.getEntrenador().equals("usuario")) {
				pokemonsUsuario.add(pokemonBatalla);
			} else if (pokemonBatalla.getEntrenador().equals("cpu")) {
				pokemonsCpu.add(pokemonBatalla);
			}
		}
		batalla.setPokemonsUsuario(pokemonsUsuario);
		batalla.setPokemonsCpu(pokemonsCpu);
	}
}
