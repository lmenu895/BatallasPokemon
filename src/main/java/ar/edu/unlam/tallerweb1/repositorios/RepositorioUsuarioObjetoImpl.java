package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;

@Repository("repositorioUsuarioObjeto")
public class RepositorioUsuarioObjetoImpl implements RepositorioUsuarioObjeto{

	private SessionFactory sessionFactory;
	
	@Autowired
	public RepositorioUsuarioObjetoImpl(SessionFactory sessionFactory){
			this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<UsuarioObjeto> buscarObjeto(Long idUsuario) {
		return (List<UsuarioObjeto>) this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioObjeto.class)
				.add(Restrictions.eq("usuario.id", idUsuario)).list();
	}

}
