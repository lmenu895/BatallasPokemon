package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;

@Repository("repositorioPokemonBatalla")
public class RepositorioPokemonBatallaImpl implements RepositorioPokemonBatalla {

	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioPokemonBatallaImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void guardarPokemonBatalla(PokemonBatalla pokemonBatalla) {
		this.sessionFactory.getCurrentSession().save(pokemonBatalla);
	}

}
