package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.RarezaPokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
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
	//repetidos
	@Override
<<<<<<< HEAD
	public Boolean guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon, Long idUsuario, Long idPokemon, Usuario usuario, Pokemon pokemon) {
		if(this.repositorioUsuarioPokemon.buscarUsuarioPokemon(idUsuario, idPokemon) == null) {
			this.repositorioUsuarioPokemon.guardarUsuarioPokemon(usuarioPokemon);
			return true;
		}
		
		return false;
=======
	public void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon) {
		this.repositorioUsuarioPokemon.guardarUsuarioPokemon(usuarioPokemon);
>>>>>>> 9a5119065880cdacee599089b135452dfa045187
		
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