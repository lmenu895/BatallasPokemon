package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("repositorioAtaquePokemon")
public class RepositorioAtaquePokemonImpl implements RepositorioAtaquePokemon {
	
	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioAtaquePokemonImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void guardarAtaque(AtaquePokemon ataques) {
		this.sessionFactory.getCurrentSession().save(ataques);
	}

	@Override
	public List<AtaquePokemon> buscarAtaques(Long id) {
		return (List<AtaquePokemon>) this.sessionFactory.getCurrentSession()
				.createCriteria(AtaquePokemon.class)
				.add(Restrictions.eq("pokemon.id", id)).list();
	}

	@Override
	public void borrarAtaquePokemon(AtaquePokemon ataquePokemon) {
		this.sessionFactory.getCurrentSession().delete(ataquePokemon);
	}

	@Override
	public void borrarAtaquesDeUnPokemon(Long idPokemon) {
		this.buscarAtaques(idPokemon).forEach(x -> this.borrarAtaquePokemon(x));
	}
	
}
