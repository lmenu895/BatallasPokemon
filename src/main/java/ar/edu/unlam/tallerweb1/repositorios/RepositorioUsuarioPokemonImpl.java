package ar.edu.unlam.tallerweb1.repositorios;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Repository("repositorioUsuarioPokemon")
public class RepositorioUsuarioPokemonImpl implements RepositorioUsuarioPokemon{

	private SessionFactory sessionFactory;
	
	 @Autowired
	public RepositorioUsuarioPokemonImpl(SessionFactory sessionFactory){
			this.sessionFactory = sessionFactory;
	}
	
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

}