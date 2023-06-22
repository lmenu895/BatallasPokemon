package ar.edu.unlam.tallerweb1.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
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
		return this.sessionFactory.getCurrentSession().createCriteria(UsuarioAtaquePokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario)).add(Restrictions.eq("pokemon.id", idPokemon)).list();
	}

	@Override
	public void guardar(UsuarioAtaquePokemon ataquePokemonUsuario) {
		this.sessionFactory.getCurrentSession().save(ataquePokemonUsuario);
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerListaDeAtaquesActivos(Long idPokemon, Long idUsuario) {
		return this.sessionFactory.getCurrentSession().createCriteria(UsuarioAtaquePokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario)).add(Restrictions.eq("pokemon.id", idPokemon))
				.add(Restrictions.eq("activo", true)).list();
	}

	@Override
	public UsuarioAtaquePokemon buscar(Long idUAP) {
		return this.sessionFactory.getCurrentSession().get(UsuarioAtaquePokemon.class, idUAP);
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerAtaquesPorPokemon(Long idAtaque, Long idPokemon) {
		return this.sessionFactory.getCurrentSession().createCriteria(UsuarioAtaquePokemon.class)
				.add(Restrictions.eq("ataque.id", idAtaque)).add(Restrictions.eq("pokemon.id", idPokemon)).list();
	}

	@Override
	public void borrar(UsuarioAtaquePokemon uap) {
		this.sessionFactory.getCurrentSession().delete(uap);
	}
}
