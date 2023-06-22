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
	public void guardar(AtaquePokemon ataques) {
		this.sessionFactory.getCurrentSession().save(ataques);
	}

	@Override
	public List<AtaquePokemon> buscar(Long id) {
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
	public void borrar(AtaquePokemon ataquePokemon) {
		this.sessionFactory.getCurrentSession().delete(ataquePokemon);
	}

	@Override
	public void borrar(Long idAtaque, Long idPokemon) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaDelete<AtaquePokemon> cd = cb.createCriteriaDelete(AtaquePokemon.class);
		Root<AtaquePokemon> root = cd.from(AtaquePokemon.class);
		Predicate[] predicates = new Predicate[] { cb.equal(root.get("ataque"), idAtaque),
				cb.equal(root.get("pokemon"), idPokemon) };
		cd.where(predicates);

		session.createQuery(cd).executeUpdate();
	}

	@Override
	public List<AtaquePokemon> buscarDesbloqueados(Long idPokemon) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AtaquePokemon> cr = cb.createQuery(AtaquePokemon.class);
		Root<AtaquePokemon> root = cr.from(AtaquePokemon.class);
		Predicate[] predicates = new Predicate[2];
		predicates[0] = cb.equal(root.get("pokemon"), idPokemon);
		predicates[1] = cb.equal(root.get("bloqueado"), false);
		cr.select(root).where(predicates);

		return session.createQuery(cr).getResultList();
	}

	@Override
	public List<AtaquePokemon> buscarBloqueados(Long idPokemon) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AtaquePokemon> cr = cb.createQuery(AtaquePokemon.class);
		Root<AtaquePokemon> root = cr.from(AtaquePokemon.class);
		Predicate[] predicates = new Predicate[2];
		predicates[0] = cb.equal(root.get("pokemon"), idPokemon);
		predicates[1] = cb.equal(root.get("bloqueado"), true);
		cr.select(root).where(predicates);

		return session.createQuery(cr).getResultList();
	}

	@Override
	public List<AtaquePokemon> obtenerTodos() {
		return this.sessionFactory.getCurrentSession().createCriteria(AtaquePokemon.class).list();
	}
}
