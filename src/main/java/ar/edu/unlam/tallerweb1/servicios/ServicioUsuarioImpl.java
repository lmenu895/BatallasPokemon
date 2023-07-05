package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.PuntosInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.RarezaPokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPlan;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

	@Inject
	private RepositorioUsuario repositorioUsuario;
	@Inject
	private RepositorioUsuarioPlan repositorioUsuarioPlan;
	@Inject
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	@Inject
	private ServicioPokemon servicioPokemon;
	@Inject
	private ServicioObjeto servicioObjeto;
	@Inject
	private ServicioUsuarioObjeto servicioUsuarioObjeto;

	@Override
	public void guardarEquipo(String[] pokemons, Long idUsuario) {
		Usuario usuario = this.repositorioUsuario.buscar(idUsuario);
		Long idUs = (long) 0;
		Long idPokemon = (long) 0;
		Usuario us = null;
		Pokemon poke = null;

		for (String pokemon : pokemons) {
			this.servicioUsuarioPokemon.guardarUsuarioPokemon(new UsuarioPokemon().withUsuario(usuario)
					.withPokemon(this.servicioPokemon.buscar(Long.parseLong(pokemon))), idUs, idPokemon, us, poke);
		}
	}

	@Override
	public Usuario buscar(Long idUsuario) {
		return this.repositorioUsuario.buscar(idUsuario);
	}

	@Override
	public void modificar(Usuario usuario) {
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public List<Pokemon> obtenerListaDePokemons(Long idUsuario) {
		List<Pokemon> pokemons = new ArrayList<>();
		this.buscar(idUsuario).getPokemons().forEach(x -> pokemons.add(x.getPokemon()));
		return pokemons;
	}

	@Override
	public void comprarObjetos(Long idUsuario, List<Integer> cantidad) throws PuntosInsuficientesException {
		Usuario usuario = this.buscar(idUsuario);
		List<UsuarioObjeto> usuarioObjetoList = this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(usuario.getId());
		Integer puntosUsuario = usuario.getPuntos();
		Integer cant;
		for (UsuarioObjeto usuarioObjeto : usuarioObjetoList) {
			cant = cantidad.get(usuarioObjetoList.indexOf(usuarioObjeto));
			if (cant != null && cant != 0) {
				puntosUsuario -= usuarioObjeto.getObjeto().getPrecio() * cant;
			}
		}
		if (puntosUsuario < 0) {
			throw new PuntosInsuficientesException("Puntos Insuficientes :(");
		}
		for (UsuarioObjeto usuarioObjeto : usuarioObjetoList) {
			try {
				usuarioObjeto.setCantidad(
						usuarioObjeto.getCantidad() + cantidad.get(usuarioObjetoList.indexOf(usuarioObjeto)));
			} catch (NullPointerException e) {
				System.err.println(e);
			}
		}
		usuario.setPuntos(puntosUsuario);
	}

	@Override
	public Boolean restarPuntos(Integer monedas, Usuario usuario) {
		if (checkearTokens(usuario, monedas)) {
			repositorioUsuario.modificar(usuario);
			return true;
		}
		if (monedas > usuario.getPuntos()) {
			return false;
		}
		usuario.setPuntos(usuario.getPuntos() - monedas);
		repositorioUsuario.modificar(usuario);
		return true;
	}

	@Override
	public void sumarPuntos(Long idUsuario, Integer puntos) {
		UsuarioPlan up = this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
		if (up != null) {
			up.getUsuario().setPuntos(up.getUsuario().getPuntos() + (int) (puntos * up.getPlan().getMultiplicador()));
		} else {
			Usuario usuario = this.repositorioUsuario.buscar(idUsuario);
			usuario.setPuntos(usuario.getPuntos() + puntos);
		}
	}

	// repetidos
	@Override
	public Integer sumarpokeMonedas(RarezaPokemon rareza, Usuario usuario) {
		switch (rareza) {
		case NORMAL:
			usuario.setPokemonedas(usuario.getPokemonedas() + 1);
			this.repositorioUsuario.modificar(usuario);
			return 1;
		case RARO:
			usuario.setPokemonedas(usuario.getPokemonedas() + 4);
			this.repositorioUsuario.modificar(usuario);
			return 4;
		case EPICO:
			usuario.setPokemonedas(usuario.getPokemonedas() + 10);
			this.repositorioUsuario.modificar(usuario);
			return 10;

		}
		return null;
	}

	@Override
	public void sumarTiradasComunes(Usuario usuario) {
		if (usuario.getCantTiradasComunes() < 10) {
			usuario.setCantTiradasComunes(usuario.getCantTiradasComunes() + 1);
		} else {
			usuario.setCantTiradasComunes(0);
		}
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void sumarTiradasTotales(Usuario usuario) {
		if (usuario.getCantTiradasTotales() < 50) {
			usuario.setCantTiradasTotales(usuario.getCantTiradasTotales() + 1);
		} else {
			usuario.setCantTiradasTotales(0);
		}
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void reiniciarTiradasComunes(Usuario usuario) {
		usuario.setCantTiradasComunes(0);
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void reiniciarTiradasTotales(Usuario usuario) {
		usuario.setCantTiradasTotales(0);
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void sacarPrincipiante(Usuario usuario) {
		usuario.setPrincipiante(false);
		this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void asignarObjetos(Usuario usuarioNuevo) {
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		for (Objeto objeto : objetos) {
			UsuarioObjeto usuarioObjeto = new UsuarioObjeto().withUsuario(usuarioNuevo).withObjeto(objeto);
			this.servicioUsuarioObjeto.guardarUsuarioObjeto(usuarioObjeto);
		}
	}

	private boolean checkearTokens(Usuario usuario, Integer monedas) {
		if (usuario.getTiradaUltraball() > 0 && monedas == 1000) {
			usuario.setTiradaUltraball(usuario.getTiradaUltraball() - 1);
			return true;
		} else if (usuario.getTiradaMasterball() > 0 && monedas == 10000) {
			usuario.setTiradaUltraball(usuario.getTiradaMasterball() - 1);
			return true;
		}
		return false;
	}

}