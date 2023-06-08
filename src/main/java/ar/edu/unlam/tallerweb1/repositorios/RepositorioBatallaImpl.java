package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Batalla;

@Repository("repositorioBatalla")
public class RepositorioBatallaImpl implements RepositorioBatalla {
	
	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioBatallaImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarBatalla(Batalla batalla) {
		this.sessionFactory.getCurrentSession().save(batalla);
	}

}
