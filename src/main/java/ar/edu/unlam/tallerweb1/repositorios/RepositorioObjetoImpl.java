package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Objeto;

@Repository
public class RepositorioObjetoImpl implements RepositorioObjeto {
	
	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioObjetoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	
	public List<Objeto> listarObjetos() {
		 final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Objeto.class)
				.list();
	}



	@Override
	public Objeto buscar(Long id) {
		return this.sessionFactory.getCurrentSession().get(Objeto.class, id);
	}

}


