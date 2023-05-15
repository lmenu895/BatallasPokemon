package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPokemon;

@Service("servicioUsuarioPokemon")
@Transactional
public class ServicioUsuarioPokemonImpl implements ServicioUsuarioPokemon {

	private RepositorioUsuarioPokemon repositorioUsuarioPokemon;
	@Autowired
	public ServicioUsuarioPokemonImpl(RepositorioUsuarioPokemon repositorioUsuarioPokemon) {
		this.repositorioUsuarioPokemon = repositorioUsuarioPokemon;
	}

	@Override
	public void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon) {
		this.repositorioUsuarioPokemon.guardarUsuarioPokemon(usuarioPokemon);

	}

	@Override
	public List<UsuarioPokemon> obtenerListaDeUsuarioPokemon(Long idUsuario) {
		return this.repositorioUsuarioPokemon.buscarPokemon(idUsuario);
	}

	@Override
	public void borrarUsuarioDeUnPokemon(Long idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarEquipo(UsuarioPokemon usuarioPokemon) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Pokemon> buscarPokemon(List<UsuarioPokemon> lista) {
		ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
		for (UsuarioPokemon pokemon : lista) {
			pokemons.add(pokemon.getPokemon());
		}
		return pokemons;
	}
}