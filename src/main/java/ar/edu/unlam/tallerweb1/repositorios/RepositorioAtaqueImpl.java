package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.persistence.criteria.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioAtaqueImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ataque> obtenerTodosLosAtaques() {
		Session session = this.sessionFactory.getCurrentSession();
		CriteriaQuery<Ataque> query = session.getCriteriaBuilder().createQuery(Ataque.class);
		query.select(query.from(Ataque.class));
		return session.createQuery(query).getResultList();
		/*
		 * return this.sessionFactory.getCurrentSession()
		 * .createCriteria(Ataque.class).list();
		 */
	}

	@Override
	public Ataque buscarAtaque(Long id) {
		return this.sessionFactory.getCurrentSession().get(Ataque.class, id); // aca se le pide para buscar QUE CLASE es																		// y por que PARAMETRO BUSCAR
	}

	@Override
	public void guardarAtaque(Ataque datosAtaque) {
		this.sessionFactory.getCurrentSession().save(datosAtaque);
	}

	@Override
	public void borrarAtaque(Long id) {
		Ataque ataque = this.sessionFactory.getCurrentSession().get(Ataque.class, id);
		this.sessionFactory.getCurrentSession().delete(ataque);
	}

	@Override
	public void modificarAtaque(Ataque ataque) {
		System.out.println(ataque.getId());
		this.sessionFactory.getCurrentSession().update(ataque);

	}

}
