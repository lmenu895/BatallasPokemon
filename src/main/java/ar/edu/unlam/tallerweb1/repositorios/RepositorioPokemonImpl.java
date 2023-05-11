package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

@Repository("repositorioPokemon")
public class RepositorioPokemonImpl implements RepositorioPokemon {

	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioPokemonImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarPokemon(Pokemon pokemon) {
		this.sessionFactory.getCurrentSession().save(pokemon);
	}

	@Override
	public Pokemon buscarPokemonPorNombre(String nombre) {
		return (Pokemon) this.sessionFactory.getCurrentSession()
				.createCriteria(Pokemon.class)
				.add(Restrictions.eq("nombre", nombre))
				.uniqueResult();
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		return this.sessionFactory.getCurrentSession().get(Pokemon.class, id);
	}

	@Override
	public List<Pokemon> obtenerTodosLosPokemons() {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Pokemon.class).list();
	}

	@Override
	public void modificarPokemon(Pokemon pokemon) {
		this.sessionFactory.getCurrentSession().update(pokemon);
	}

	@Override
	public void borrarPokemon(Long id) {
		this.sessionFactory.getCurrentSession().delete(this.buscarPokemon(id));
	}
	
	
}
