package ar.edu.unlam.tallerweb1.servicios;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.RarezaPokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

	private RepositorioUsuario repositorioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	private ServicioPokemon servicioPokemon;
	
	@Autowired
	public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario, ServicioUsuarioPokemon servicioUsuarioPokemon, ServicioPokemon servicioPokemon){
		this.repositorioUsuario = repositorioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
		this.servicioPokemon = servicioPokemon;
	}

	@Override
	public void guardarEquipo(String[] pokemons, Long idUsuario) {
		Usuario usuario = this.repositorioUsuario.buscarUsuario(idUsuario);
		Long idUs=(long) 0;
		Long idPokemon=(long) 0;
		Usuario us= null;
		Pokemon poke= null;
		
		for (String pokemon : pokemons) {
			this.servicioUsuarioPokemon.guardarUsuarioPokemon(
					new UsuarioPokemon(usuario, this.servicioPokemon.buscarPokemon(Long.parseLong(pokemon))), idUs, idPokemon, us, poke
			);
		}	
	}

	@Override
	public Usuario buscarUsuario(Long idUsuario) {
		return this.repositorioUsuario.buscarUsuario(idUsuario);
	}
	
	@Override
	public List<Pokemon> obtenerListaDePokemons(Long idUsuario) {
		List<Pokemon> pokemons = new ArrayList<>();
		this.buscarUsuario(idUsuario).getPokemons().forEach(x -> pokemons.add(x.getPokemon()));
		return pokemons;
	}

	@Override
	public Boolean restarPuntos(Integer monedas, Usuario usuario) {
		if(monedas > usuario.getPuntos()) {
			return false;
		}
		usuario.setPuntos(usuario.getPuntos()-monedas);
		repositorioUsuario.modificar(usuario);
		return true;
	}
	
	public List<Objeto> obtenerListaDeObjetos(Long idUsuario) {
		List<Objeto> objetos= new ArrayList<>();
		this.buscarUsuario(idUsuario).getObjetos().forEach(x -> objetos.add(x.getObjeto()));
		return objetos;
	}

	@Override
	public void sumarPuntos(Long idUsuario, Integer puntos) {
		Usuario user = this.repositorioUsuario.buscarUsuario(idUsuario);
		user.setPuntos(user.getPuntos() + puntos);
		this.repositorioUsuario.modificar(user);
	}
	//repetidos
	@Override
	public Integer sumarpokeMonedas(RarezaPokemon rareza, Usuario usuario) {
		switch (rareza) {
		case NORMAL:
			usuario.setPokemonedas(usuario.getPokemonedas()+1);
			this.repositorioUsuario.modificar(usuario);
			return 1;
		case RARO:
			usuario.setPokemonedas(usuario.getPokemonedas()+4);
			this.repositorioUsuario.modificar(usuario);
			return 4;
		case EPICO:
			usuario.setPokemonedas(usuario.getPokemonedas()+10);
			this.repositorioUsuario.modificar(usuario);
			return 10;
			
		}
		return null;
	}

	@Override
	public void sumarTiradasComunes(Usuario usuario) {
		if(usuario.getCantTiradasComunes()<10) {
			usuario.setCantTiradasComunes(usuario.getCantTiradasComunes()+1);
			}else {
			usuario.setCantTiradasComunes(0);
			}
			this.repositorioUsuario.modificar(usuario);
	}

	@Override
	public void sumarTiradasTotales(Usuario usuario) {
		if(usuario.getCantTiradasTotales()<50) {
		usuario.setCantTiradasTotales(usuario.getCantTiradasTotales()+1);
		}else {
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
}