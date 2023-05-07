package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

@Service("servicioPokemon")
@Transactional
public class ServicioPokemonImpl implements ServicioPokemon {

	private RepositorioPokemon repositorioPokemon;
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ServicioPokemonImpl(RepositorioPokemon repositorioPokemon, ServicioAtaque servicioAtaque){
		this.repositorioPokemon = repositorioPokemon;
		this.servicioAtaque = servicioAtaque;
	}

	@Override
	public Boolean guardarPokemon(Pokemon pokemon) {
		if(validarPokemon(pokemon)) {
			List<Ataque> lista = new ArrayList<>();
			for (String ataque : pokemon.getCv()) {
				lista.add(this.servicioAtaque.buscarAtaque(Long.parseLong(ataque)));
			}
			pokemon.setAtaques(lista);
			this.repositorioPokemon.guardarPokemon(pokemon);
			return true;
		}
		else
			return false;
	}

	
	private Boolean validarPokemon(Pokemon pokemon) {
		if(this.repositorioPokemon.buscarPokemonPorNombre(pokemon.getNombre()) == null)
			return true;
		else
			return false;
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		return this.repositorioPokemon.buscarPokemon(id);
	}
	
}
