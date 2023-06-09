package ar.edu.unlam.tallerweb1.repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

@Repository("repositorioPokemon")
public class RepositorioPokemonImpl implements RepositorioPokemon {

	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioPokemonImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarPokemon(Pokemon pokemon) {
		this.sessionFactory.getCurrentSession().save(pokemon);
	}

	@Override
	public Pokemon buscarPokemon(String nombre) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
		Root<Pokemon> root = cr.from(Pokemon.class);
		cr.select(root).where(cb.like(root.get("nombre"), nombre));
		try {
			return session.createQuery(cr).getSingleResult();
		} catch (Exception ex) {
			System.err.println(ex);
			return null;
		}
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		return this.sessionFactory.getCurrentSession().get(Pokemon.class, id);
	}

	@Override
	public List<Pokemon> obtenerTodosLosPokemons() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
		Root<Pokemon> root = cr.from(Pokemon.class);
		cr.select(root);
		try {
			return session.createQuery(cr).getResultList();
		} catch (Exception ex) {
			System.err.println(ex);
			return null;
		}
	}
	public List<Pokemon> obtenerPokemonsPorRareza(int rareza) {
	Session session = sessionFactory.getCurrentSession();
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
    Root<Pokemon> root = cr.from(Pokemon.class);
    cr.select(root);
    cr.select(root).where(cb.equal(root.get("rareza"), rareza));

        return session.createQuery(cr).getResultList();
	}

	@Override
	public void modificarPokemon(Pokemon pokemon) {
		this.sessionFactory.getCurrentSession().update(pokemon);
	}

	@Override
	public void borrarPokemon(Long id) {
		this.sessionFactory.getCurrentSession().delete(this.buscarPokemon(id));
	}
<<<<<<< HEAD
=======

	@Override
	public ArrayList<Pokemon> buscarPorRareza(int rareza) {
		Session session = sessionFactory.getCurrentSession();
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
	    Root<Pokemon> root = cr.from(Pokemon.class);
	    cr.select(root);
	    cr.select(root).where(cb.equal(root.get("rareza"), rareza));
	    try {
	        return (ArrayList<Pokemon>) session.createQuery(cr).getResultList();
	        
	    } catch (Exception ex) {
	        System.err.println(ex);
	        return null;
	    }
	}

	@Override
	public ArrayList<Pokemon> buscarPorRareza(int rareza) {
		Session session = sessionFactory.getCurrentSession();
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<Pokemon> cr = cb.createQuery(Pokemon.class);
	    Root<Pokemon> root = cr.from(Pokemon.class);
	    cr.select(root);
	    cr.select(root).where(cb.equal(root.get("rareza"), rareza));
	    try {
	        return (ArrayList<Pokemon>) session.createQuery(cr).getResultList();
	        
	    } catch (Exception ex) {
	        System.err.println(ex);
	        return null;
	    }
	}




>>>>>>> 0df91bd (gacha terminado sin front2)
}
