package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
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
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
		Root<Pokemon> root = cr.from(Pokemon.class);
		cr.select(root).where(cb.like(root.get("nombre"), nombre));
		try {
			return session.createQuery(cr).getSingleResult();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
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
