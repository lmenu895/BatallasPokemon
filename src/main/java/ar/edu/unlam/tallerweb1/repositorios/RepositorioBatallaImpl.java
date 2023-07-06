package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Batalla;

@Repository("repositorioBatalla")
public class RepositorioBatallaImpl implements RepositorioBatalla {
	
	@Inject
	private SessionFactory sessionFactory;

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
