package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.persistence.criteria.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

@Repository("repositorioAtaquePokemon")
public class RepositorioAtaquePokemonImpl implements RepositorioAtaquePokemon {

	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioAtaquePokemonImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarAtaque(AtaquePokemon ataques) {
		this.sessionFactory.getCurrentSession().save(ataques);
	}

	@Override
	public List<AtaquePokemon> buscarAtaques(Long id) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AtaquePokemon> cr = cb.createQuery(AtaquePokemon.class);
		Root<AtaquePokemon> root = cr.from(AtaquePokemon.class);
		cr.select(root).where(cb.equal(root.get("pokemon"), id));

		return session.createQuery(cr).getResultList();
		/*
		 * return
		 * this.sessionFactory.getCurrentSession().createCriteria(AtaquePokemon.class)
		 * .add(Restrictions.eq("pokemon.id", id)).list();
		 */
	}

	@Override
	public void borrarAtaquePokemon(AtaquePokemon ataquePokemon) {
		this.sessionFactory.getCurrentSession().delete(ataquePokemon);
	}

	@Override
	public void borrarAtaquePokemon(Long idAtaque, Long idPokemon) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaDelete<AtaquePokemon> cd = cb.createCriteriaDelete(AtaquePokemon.class);
		Root<AtaquePokemon> root = cd.from(AtaquePokemon.class);
		Predicate[] predicates = new Predicate[] { cb.equal(root.get("ataque"), idAtaque),
				cb.equal(root.get("pokemon"), idPokemon) };
		cd.where(predicates);

		session.createQuery(cd).executeUpdate();
	}
}
