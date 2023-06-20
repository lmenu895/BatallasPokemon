package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;

@Repository("repositorioUsuarioAtaquePokemon")
public class RepositorioUsuarioAtaquePokemonImpl implements RepositorioUsuarioAtaquePokemon {

	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioUsuarioAtaquePokemonImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerAtaques(Long idUsuario, Long idPokemon) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioAtaquePokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario))
				.add(Restrictions.eq("pokemon.id", idPokemon))
				.list();
	}

	@Override
	public void guardarAtaquePokemonUsuario(UsuarioAtaquePokemon ataquePokemonUsuario) {
		this.sessionFactory.getCurrentSession().save(ataquePokemonUsuario);
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerListaDeAtaquesActivos(Long idPokemon, Long idUsuario) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioAtaquePokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario))
				.add(Restrictions.eq("pokemon.id", idPokemon))
				.add(Restrictions.eq("activo", true))
				.list();
	}
}
