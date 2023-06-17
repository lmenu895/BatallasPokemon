package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioAtaquePokemon;

@Service("servicioUsuarioAtaquePokemon")
@Transactional
public class ServicioUsuarioAtaquePokemonImpl implements ServicioUsuarioAtaquePokemon {

	private RepositorioUsuarioAtaquePokemon repositorioUsuarioAtaquePokemon;

	@Autowired
	public ServicioUsuarioAtaquePokemonImpl(RepositorioUsuarioAtaquePokemon repositorioUsuarioAtaquePokemon) {
		this.repositorioUsuarioAtaquePokemon = repositorioUsuarioAtaquePokemon;
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerAtaques(Long idUsuario, Long idPokemon) {
		return this.repositorioUsuarioAtaquePokemon.obtenerAtaques(idUsuario, idPokemon);
	}

	@Override
	public void guardarAtaquePokemonUsuario(AtaquePokemon ataquePokemon, Usuario usuario) {
		this.repositorioUsuarioAtaquePokemon.guardarAtaquePokemonUsuario(
				new UsuarioAtaquePokemon(usuario, ataquePokemon.getPokemon(), ataquePokemon.getAtaque(),
						ataquePokemon.getBloqueado(), ataquePokemon.getBloqueado() ? false : true));
	}

	@Override
	public List<Ataque> obtenerListaDeAtaquesActivos(Long idPokemon, Long idUsuario) {
		List<Ataque> ataques = new ArrayList<>();
		this.repositorioUsuarioAtaquePokemon.obtenerListaDeAtaquesActivos(idPokemon, idUsuario)
				.forEach(x -> ataques.add(x.getAtaque()));
		return ataques;
	}
}
