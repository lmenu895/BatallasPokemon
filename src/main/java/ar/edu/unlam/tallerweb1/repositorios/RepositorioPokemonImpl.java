package ar.edu.unlam.tallerweb1.repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

@Repository("repositorioPokemon")
public class RepositorioPokemonImpl implements RepositorioPokemon {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public void guardar(Pokemon pokemon) {
		this.sessionFactory.getCurrentSession().save(pokemon);
	}

	@Override
	public Pokemon buscar(String nombre) {
		Session session = this.sessionFactory.getCurrentSession();
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
	public Pokemon buscar(Long id) {
		return this.sessionFactory.getCurrentSession().get(Pokemon.class, id);
	}

	@Override
	public List<Pokemon> obtenerTodos() {
		Session session = this.sessionFactory.getCurrentSession();
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
	public List<Pokemon> obtenerPorRareza(int rareza) {
	Session session = this.sessionFactory.getCurrentSession();
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
	public void borrar(Long id) {
		this.sessionFactory.getCurrentSession().delete(this.buscar(id));
	}

	@Override
	public ArrayList<Pokemon> buscarPorRareza(int rareza) {
		Session session = this.sessionFactory.getCurrentSession();
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
}
