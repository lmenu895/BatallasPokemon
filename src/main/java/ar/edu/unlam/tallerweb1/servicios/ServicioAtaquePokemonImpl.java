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
	public void guardarAtaque(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.guardarAtaque(ataquePokemon);
	}

	@Override
	public void borrarAtaquePokemon(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.borrarAtaquePokemon(ataquePokemon);
	}

	@Override
	public void borrarAtaquePokemon(Long idAtaque, Long idPokemon) {
		this.repositorioAtaquePokemon.borrarAtaquePokemon(idAtaque, idPokemon);
	}

	@Override
	public List<Long> obtenetAtaquesDesbloqueados(Long idPokemon) {
		List<Long> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscarAtaquesDesbloqueados(idPokemon)) {
			ataques.add(ataque.getAtaque().getId());
		}
		return ataques;
	}

	@Override
	public List<Long> obtenetAtaquesBloqueados(Long idPokemon) {
		List<Long> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscarAtaquesBloqueados(idPokemon)) {
			ataques.add(ataque.getAtaque().getId());
		}
		return ataques;
	}

	@Override
	public List<Ataque> obtenerListaDeAtaques(Long idPokemon) {
		List<Ataque> ataques = new ArrayList<>();
		for (AtaquePokemon ataque : this.repositorioAtaquePokemon.buscarAtaques(idPokemon)) {
			ataques.add(ataque.getAtaque());
		}
		return ataques;
	}

	@Override
	public List<AtaquePokemon> obtenerListaDeAtaquesPokemon(Long idPokemon) {
		return this.repositorioAtaquePokemon.buscarAtaques(idPokemon);
	}
}
