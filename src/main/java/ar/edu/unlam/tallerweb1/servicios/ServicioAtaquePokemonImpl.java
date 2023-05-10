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
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ServicioAtaquePokemonImpl(RepositorioAtaquePokemon repositorioAtaquePokemon, ServicioAtaque servicioAtaque) {
		this.repositorioAtaquePokemon = repositorioAtaquePokemon;
		this.servicioAtaque = servicioAtaque;
	}

	@Override
	public void guardarAtaques(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.guardarAtaques(ataquePokemon);
	}

	@Override
	public List<Ataque> buscarAtaques(Long id) {
		List<Ataque> ataques = new ArrayList<>();
		List<AtaquePokemon> busqueda = this.repositorioAtaquePokemon.buscarAtaques(id);
		for (AtaquePokemon ataquePokemon : busqueda) {
			ataques.add(this.servicioAtaque.buscarAtaque(ataquePokemon.getAtaque().getId()));
		}
		return ataques;
	}
}
