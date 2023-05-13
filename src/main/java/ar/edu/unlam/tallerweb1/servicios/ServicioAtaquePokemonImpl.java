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
	public void guardarAtaque(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.guardarAtaque(ataquePokemon);
	}

	@Override
	public List<Ataque> obtenerListaDeAtaquePokemon(Long idPokemon) {
		List<Ataque> ataques = new ArrayList<>();
		this.repositorioAtaquePokemon.buscarAtaques(idPokemon)
				.forEach(x -> ataques.add(this.servicioAtaque.buscarAtaque(x.getAtaque().getId())));
		return ataques;
	}

	@Override
	public void borrarAtaquePokemon(AtaquePokemon ataquePokemon) {
		this.repositorioAtaquePokemon.borrarAtaquePokemon(ataquePokemon);
	}

	@Override
	public void borrarAtaquePokemon(Long idAtaque, Long idPokemon) {
		this.repositorioAtaquePokemon.borrarAtaquePokemon(idAtaque, idPokemon);
	}
}
