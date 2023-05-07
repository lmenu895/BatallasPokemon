package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
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
		Query<Pokemon> query = this.sessionFactory.getCurrentSession().createQuery("from Pokemon where nombre =:nombre").setParameter("nombre", nombre);
		return query.uniqueResult();
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		return this.sessionFactory.getCurrentSession().get(Pokemon.class, id);
	}
	
	
}
