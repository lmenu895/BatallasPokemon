package ar.edu.unlam.tallerweb1.servicios;

import java.util.Random;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Service
@Transactional
public class ServicioGachaponImpl implements ServicioGachapon {

	private RepositorioPokemon repositorioPokemon;
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;

	@Autowired
	public ServicioGachaponImpl(RepositorioPokemon repositorioPokemon, ServicioUsuario servicioUsuario,
			ServicioUsuarioPokemon servicioUsuarioPokemon) {
		this.repositorioPokemon = repositorioPokemon;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
	}

	@Override
	public Pokemon tiradaGachapon(Integer monedas, Usuario usuario) {
		Pokemon pokemon;
		if (usuario.getPrincipiante()) {
			if (usuario.getPokemons().size() == 1) {
				return pokemon = tiradaComun(usuario);
			}
			this.servicioUsuario.sacarPrincipiante(usuario);
			return pokemon = tiradaRaro();
		}

		switch (monedas) {
		case 100:
			return pokemon = tirada(80, 95, usuario);

		case 500:
			return pokemon = tirada(60, 90, usuario);

		case 1000:
			return pokemon = tirada(30, 80, usuario);

		case 10000:
			return pokemon = tirada(0, 30, usuario);
		}

		return null;
	}

	private Pokemon pokemonRepetido(Usuario usuario) {
		List<UsuarioPokemon> lista = usuario.getPokemons();
		List<Pokemon> pokemonUser = servicioUsuarioPokemon.buscarPokemon(lista);
		return pokemonUser.get(0);
	}

	private Pokemon tiradaRaro() {
		Pokemon pokemon;
		Random random = new Random();
		List<Pokemon> pokemonesFiltrados4 = this.repositorioPokemon.obtenerPokemonsPorRareza(1);
		pokemon = pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
		return pokemon;
	}

	private Pokemon tiradaComun(Usuario usuario) {
		Pokemon pokemon;
		Random random = new Random();
		List<Pokemon> pokemonesFiltrados = this.repositorioPokemon.obtenerPokemonsPorRareza(0);
		pokemonesFiltrados.remove(pokemonRepetido(usuario));
		pokemon = pokemonesFiltrados.get(random.nextInt(pokemonesFiltrados.size()));
		return pokemon;
	}

	private Pokemon tirada(Integer normal, Integer raroP, Usuario usuario) {
		Pokemon pokemon;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(101);

		int comun = 0;
		int raro = 1;
		int epico = 2;

		if (numeroAleatorio <= normal && usuario.getCantTiradasComunes() < 9 && usuario.getCantTiradasTotales() < 29) {
			List<Pokemon> pokemonesFiltrados4 = this.repositorioPokemon.obtenerPokemonsPorRareza(comun);
			pokemon = pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
			this.servicioUsuario.sumarTiradasComunes(usuario);
			this.servicioUsuario.sumarTiradasTotales(usuario);
			return pokemon;
		}
		if (numeroAleatorio > raroP || usuario.getCantTiradasTotales() == 29) {
			List<Pokemon> pokemonesFiltrados4 = this.repositorioPokemon.obtenerPokemonsPorRareza(epico);
			pokemon = pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
			this.servicioUsuario.reiniciarTiradasComunes(usuario);
			this.servicioUsuario.reiniciarTiradasTotales(usuario);
			return pokemon;
		}
		if (numeroAleatorio > normal && numeroAleatorio <= raroP && usuario.getCantTiradasTotales() < 29
				|| usuario.getCantTiradasComunes() == 9) {
			List<Pokemon> pokemonesFiltrados4 = this.repositorioPokemon.obtenerPokemonsPorRareza(raro);
			pokemon = pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
			this.servicioUsuario.reiniciarTiradasComunes(usuario);
			this.servicioUsuario.sumarTiradasTotales(usuario);
			return pokemon;

		}
		return null;

	}

}
