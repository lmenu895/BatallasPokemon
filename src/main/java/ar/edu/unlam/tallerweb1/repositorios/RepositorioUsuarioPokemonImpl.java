package ar.edu.unlam.tallerweb1.repositorios;
import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Repository("repositorioUsuarioPokemon")
public class RepositorioUsuarioPokemonImpl implements RepositorioUsuarioPokemon{

	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	public void guardarUsuarioPokemon(UsuarioPokemon usuarioPokemon) {
		this.sessionFactory.getCurrentSession().save(usuarioPokemon);
	}

	@Override
	public List<UsuarioPokemon> buscarPokemon(Long idUsuario) {

		return (List<UsuarioPokemon>) this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioPokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario)).list();
	}
	//repetidos
	@Override
	public Pokemon buscarPokemonPorId(Long idPokemon) {

		return (Pokemon) this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioPokemon.class)
				.add(Restrictions.eq("pokemon.id", idPokemon)).uniqueResult();
	}

	//repetidos
	@Override
	public UsuarioPokemon buscarUsuarioPokemon(Long idUsuario, Long idPokemon) {
		// TODO Auto-generated method stub
		return (UsuarioPokemon) this.sessionFactory.getCurrentSession()
				.createCriteria(UsuarioPokemon.class)
				.add(Restrictions.eq("usuario.id", idUsuario))
				.add(Restrictions.eq("pokemon.id", idPokemon)).uniqueResult();
	}
	
	@Override
	public List<Pokemon> getPokemonSinUsuarioPokemon(long idUsuario) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Pokemon> criteriaQuery = builder.createQuery(Pokemon.class);
        Root<Pokemon> root = criteriaQuery.from(Pokemon.class);

        // Subconsulta para verificar que el id del Pokémon no exista en usuarioPokemon
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<UsuarioPokemon> subRoot = subquery.from(UsuarioPokemon.class);
        subquery.select(subRoot.get("pokemon").get("id"))
                .where(builder.equal(subRoot.get("usuario").get("id"), idUsuario));

        criteriaQuery.select(root)
                .where(builder.not(root.get("id").in(subquery)));

        return session.createQuery(criteriaQuery).getResultList();
    }

}