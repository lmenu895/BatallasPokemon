package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;

@Repository("repositorioAtaquePokemon")
public class RepositorioAtaquePokemonImpl implements RepositorioAtaquePokemon {
	
	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioAtaquePokemonImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarAtaques(AtaquePokemon ataques) {
		this.sessionFactory.getCurrentSession().save(ataques);
	}

	@Override
	public List<AtaquePokemon> buscarAtaques(Long id) {
		return this.sessionFactory.getCurrentSession().createQuery("from AtaquePokemon where pokemon_id =:id").setParameter("id", id).list();
	}
	
}
