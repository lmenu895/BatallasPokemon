package ar.edu.unlam.tallerweb1.servicios;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
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
		for (String pokemon : pokemons) {
			this.servicioUsuarioPokemon.guardarUsuarioPokemon(
					new UsuarioPokemon(usuario, this.servicioPokemon.buscarPokemon(Long.parseLong(pokemon)))
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
}