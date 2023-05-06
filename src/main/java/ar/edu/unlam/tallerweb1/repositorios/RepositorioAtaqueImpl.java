package ar.edu.unlam.tallerweb1.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.cj.xdevapi.SessionFactory;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	
	private SessionFactory sessionFactory;

	    @Autowired
	    public RepositorioAtaqueImpl(SessionFactory sessionFactory){
	        this.sessionFactory = sessionFactory;
	    }
	
}
