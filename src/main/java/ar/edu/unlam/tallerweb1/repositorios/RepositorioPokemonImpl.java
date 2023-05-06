package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPokemon")
public class RepositorioPokemonImpl implements RepositorioPokemon {

	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioPokemonImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
}
