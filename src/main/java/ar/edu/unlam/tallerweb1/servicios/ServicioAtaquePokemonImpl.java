package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAtaquePokemon;

@Service("servicioAtaquePokemon")
@Transactional
public class ServicioAtaquePokemonImpl implements ServicioAtaquePokemon {

	private RepositorioAtaquePokemon repositorioAtaquePokemon;
	@Autowired
	public ServicioAtaquePokemonImpl(RepositorioAtaquePokemon repositorioAtaquePokemon) {
		this.repositorioAtaquePokemon = repositorioAtaquePokemon;
	}

	@Override
	public void guardar(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.guardar(ataquePokemon);
	}

	@Override
	public void borrar(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.borrar(ataquePokemon);
	}

	@Override
	public void borrar(Long idAtaque, Long idPokemon) {
		this.repositorioAtaquePokemon.borrar(idAtaque, idPokemon);
	}

	@Override
	public List<Long> obtenetDesbloqueados(Long idPokemon) {
		List<Long> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscarDesbloqueados(idPokemon)) {
			ataques.add(ataque.getAtaque().getId());
		}
		return ataques;
	}

	@Override
	public List<Long> obtenetBloqueados(Long idPokemon) {
		List<Long> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscarBloqueados(idPokemon)) {
			ataques.add(ataque.getAtaque().getId());
		}
		return ataques;
	}

	@Override
	public List<Ataque> obtenerListaDeAtaques(Long idPokemon) {
		List<Ataque> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscar(idPokemon)) {
			ataques.add(ataque.getAtaque());
		}
		return ataques;
	}

	@Override
	public List<AtaquePokemon> obtenerLista(Long idPokemon) {
		return this.repositorioAtaquePokemon.buscar(idPokemon);
	}

	@Override
	public List<AtaquePokemon> obtenerTodos() {
		return this.repositorioAtaquePokemon.obtenerTodos();
	}
}
