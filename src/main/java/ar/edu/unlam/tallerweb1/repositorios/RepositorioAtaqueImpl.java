package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.persistence.criteria.*;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	private SessionFactory sessionFactory;

	@Autowired // inyeccion de dependencias
	public RepositorioAtaqueImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ataque> obtenerTodos() {
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
	public Ataque buscar(Long id) {
		return this.sessionFactory.getCurrentSession().get(Ataque.class, id); // aca se le pide para buscar QUE CLASE es
																				// // y por que PARAMETRO BUSCAR
	}

	@Override
	public void guardar(Ataque datosAtaque) {
		this.sessionFactory.getCurrentSession().save(datosAtaque);
	}

	@Override
	public void borrar(Long id) {
		Ataque ataque = this.sessionFactory.getCurrentSession().get(Ataque.class, id);
		this.sessionFactory.getCurrentSession().delete(ataque);
	}

	@Override
	public void modificar(Ataque ataque) {
		System.out.println(ataque.getId());
		this.sessionFactory.getCurrentSession().update(ataque);

	}

	@Override
	public Ataque buscar(String nombre) {
		return (Ataque) this.sessionFactory.getCurrentSession().createCriteria(Ataque.class)
				.add(Restrictions.eq("nombre", nombre)).uniqueResult();
	}
}
