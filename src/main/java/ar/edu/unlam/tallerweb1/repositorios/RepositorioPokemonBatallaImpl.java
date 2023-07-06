package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;

@Repository("repositorioPokemonBatalla")
public class RepositorioPokemonBatallaImpl implements RepositorioPokemonBatalla {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public void guardarPokemonBatalla(PokemonBatalla pokemonBatalla) {
		this.sessionFactory.getCurrentSession().save(pokemonBatalla);
	}

	@Override
	public List<PokemonBatalla> obtenerPokemonsBatalla(Long idBatalla) {
		return this.sessionFactory.getCurrentSession().createCriteria(PokemonBatalla.class)
				.add(Restrictions.eq("batalla.id", idBatalla)).list();
	}

}
