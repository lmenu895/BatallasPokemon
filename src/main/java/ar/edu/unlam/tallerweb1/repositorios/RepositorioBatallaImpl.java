package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
	public void guardar(Batalla batalla) {
		this.sessionFactory.getCurrentSession().save(batalla);
	}

	@Override
	public List<Batalla> obtenerBatallasUsuario(Long idUsuario) {
		return (List<Batalla>) this.sessionFactory.getCurrentSession().createCriteria(Batalla.class)
				.add(Restrictions.eq("usuario.id", idUsuario)).list();
	}
}
