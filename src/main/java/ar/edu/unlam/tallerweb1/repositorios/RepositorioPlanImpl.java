package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;


@Repository
public class RepositorioPlanImpl implements RepositorioPlan {
	
	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioPlanImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void darDeBajaPlan(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		Plan plan1 = (Plan) session.createCriteria(Plan.class).add(Restrictions.eq("id", id)).uniqueResult();
		sessionFactory.getCurrentSession().delete(plan1);
		
	}

	@Override
	public List<Plan> listaDePlanes() {
		final Session session = sessionFactory.getCurrentSession();

		return session.createCriteria(Plan.class).list();
	}

	@Override
	public Plan consultarPlan(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		return (Plan) session.createCriteria(Plan.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public void asignarPlanAUsuario(Usuario usuario, Plan plan) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario u1= (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id",usuario.getId()))
				.uniqueResult();
		         u1.setPlan(plan);
		         u1.setPuntos(u1.getPuntos() + plan.getPuntos());
		         
		         
	}
		
	}


