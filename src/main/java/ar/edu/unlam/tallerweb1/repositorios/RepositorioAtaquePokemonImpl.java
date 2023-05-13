package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
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
		/*return this.sessionFactory.getCurrentSession().createCriteria(AtaquePokemon.class)
				.add(Restrictions.eq("pokemon.id", id)).list();*/
	}

	@Override
	public void borrarAtaquePokemon(AtaquePokemon ataquePokemon) {
		this.sessionFactory.getCurrentSession().delete(ataquePokemon);
	}

	@Override
	public void borrarAtaquePokemon(Long idAtaque, Long idPokemon) {
		AtaquePokemon borrar = (AtaquePokemon) this.sessionFactory.getCurrentSession()
				.createCriteria(AtaquePokemon.class).add(Restrictions.eq("ataque.id", idAtaque))
				.add(Restrictions.eq("pokemon.id", idPokemon)).uniqueResult();
		this.borrarAtaquePokemon(borrar);
	}
}
