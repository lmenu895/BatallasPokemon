package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

@Service("servicioPokemon")
@Transactional
public class ServicioPokemonImpl implements ServicioAtaque {

	private RepositorioPokemon repositorioPokemon;

	@Autowired
	public ServicioPokemonImpl(RepositorioPokemon repositorioPokemon){
		this.repositorioPokemon = repositorioPokemon;
	}
	
}
