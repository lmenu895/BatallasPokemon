package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;

@Repository("repositorioUsuarioAtaquePokemon")
public class RepositorioUsuarioAtaquePokemonImpl implements RepositorioUsuarioAtaquePokemon {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public List<UsuarioAtaquePokemon> obtenerAtaques(Long idUsuario, Long idPokemon) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UsuarioAtaquePokemon> cr = cb.createQuery(UsuarioAtaquePokemon.class);
		Root<UsuarioAtaquePokemon> root = cr.from(UsuarioAtaquePokemon.class);
		Predicate[] predicates = { cb.equal(root.get("usuario"), idUsuario), cb.equal(root.get("pokemon"), idPokemon) };
		cr.select(root).where(predicates);

		return session.createQuery(cr).getResultList();
		/*
		 * return
		 * this.sessionFactory.getCurrentSession().createCriteria(UsuarioAtaquePokemon.
		 * class) .add(Restrictions.eq("usuario.id",
		 * idUsuario)).add(Restrictions.eq("pokemon.id", idPokemon)).list();
		 */
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
