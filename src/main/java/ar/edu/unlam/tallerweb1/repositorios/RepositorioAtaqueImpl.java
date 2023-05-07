package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioAtaqueImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarAtaque(Ataque datosAtaque) {
		this.sessionFactory.getCurrentSession().save(datosAtaque);
		
	}

}
