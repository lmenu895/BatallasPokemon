package ar.edu.unlam.tallerweb1.repositorios;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Pago;

@Repository
public class RepositorioPagoImpl implements RepositorioPago {
	
	@Inject
	private SessionFactory sessionFactory;

	@Override
	public Pago buscar(Long idPago, LocalDateTime localDateTime) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Pago> cr = cb.createQuery(Pago.class);
		Root<Pago> root = cr.from(Pago.class);
		Predicate[] predicates = { cb.equal(root.get("idPago"), idPago),
				cb.equal(root.get("fechaAprobado"), localDateTime) };
		cr.select(root).where(predicates);

		try {
			return session.createQuery(cr).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public void guardar(Pago pago) {
		this.sessionFactory.getCurrentSession().save(pago);
	}

}
