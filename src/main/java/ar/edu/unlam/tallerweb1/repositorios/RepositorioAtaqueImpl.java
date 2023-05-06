package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioAtaqueImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

}
