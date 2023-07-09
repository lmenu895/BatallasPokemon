package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.ObjetosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Batalla;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioBatalla;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemonBatalla;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioObjeto;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

	private RepositorioBatalla repositorioBatalla;
	private RepositorioUsuarioObjeto repositorioUsuarioObjeto;
	
	@Inject
	private RepositorioPokemonBatalla repositorioPokemonBatalla;
	@Inject
	private ServicioUsuario servicioUsuario;
	@Inject
	private ServicioPokemon servicioPokemon;

	@Autowired
	public ServicioBatallaImpl(RepositorioBatalla repositorioBatalla,
			RepositorioUsuarioObjeto repositorioUsuarioObjeto) {
		this.repositorioBatalla = repositorioBatalla;
		this.repositorioUsuarioObjeto = repositorioUsuarioObjeto;
	}

	@Override
	public void inicioBatalla(List<Long> pokemonsLista, List<Long> objetosLista, Long idUsuario)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {
		if (pokemonsLista == null || pokemonsLista.size() != 3) {
			throw new PokemonsInsuficientesException("Debe seleccionar 3 pokémons");
		}
		if (objetosLista != null && objetosLista.size() > 3) {
			throw new ExcesoDeObjetosException("Solo puede seleccionar un máximo de 3 objetos");
		}
		if (objetosLista != null) {
			for (Long idObjeto : objetosLista) {
				if (this.repositorioUsuarioObjeto.buscar(idObjeto, idUsuario).getCantidad() <= 0) {
					throw new ObjetosInsuficientesException(
							"No posees el/los objetos que intentas llevar a la batalla");
				}
			}
		}
	}

	@Override
	public void finalBatalla(Long duracion, DatosPokemonBatalla[] listaDatosPokemons, Long idUsuario,
			String[] objetosUtilizados) {

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
			guardar("Victoria", listaDatosPokemons, duracion, idUsuario);
		} else if (pokemonsDebilitadosUsuario == 3) {
			this.servicioUsuario.sumarPuntos(idUsuario, 6);
			guardar("Derrota", listaDatosPokemons, duracion, idUsuario);
		}
		if (objetosUtilizados != null) {
			List<UsuarioObjeto> listaUsuarioObjeto = this.repositorioUsuarioObjeto.buscarObjetos(idUsuario);
			for (String efecto : objetosUtilizados) {
				UsuarioObjeto uo = listaUsuarioObjeto.stream()
						.filter(x -> x.getObjeto().getEfecto().name().equals(efecto)).findFirst().get();
				uo.setCantidad(uo.getCantidad() - 1);
			}
		}
	}

	@Override
	public List<Batalla> obtenerBatallasUsuario(Long idUsuario) {
		List<Batalla> batallas = this.repositorioBatalla.obtenerBatallasUsuario(idUsuario);
		Collections.reverse(batallas);
		return batallas;
	}

	private void guardar(String resultado, DatosPokemonBatalla[] listaDatosPokemons, Long duracion, Long idUsuario) {
		Long minutos = (long) Math.floor(duracion / 60000);
		Long segundos = (long) Math.floor((duracion % 60000) / 1000);
		String duracionString = String.format("%02d:%02d", minutos, segundos);
		// String fecha = new
		// SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
		Batalla batalla = new Batalla().withDuracion(duracionString).withFecha(Calendar.getInstance().getTime())
				.withResultado(resultado).withUsuario(this.servicioUsuario.buscar(idUsuario));
		this.repositorioBatalla.guardar(batalla);
		for (DatosPokemonBatalla dato : listaDatosPokemons) {
			this.repositorioPokemonBatalla.guardarPokemonBatalla(
					new PokemonBatalla().withPokemon(this.servicioPokemon.buscar(dato.getId())).withBatalla(batalla)
							.withDebilitado(dato.getDebilitado()).withEntrenador(dato.getEntrenador()));
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
		batalla.withPokemonsUsuario(pokemonsUsuario).withPokemonsCpu(pokemonsCpu);
	}
}
