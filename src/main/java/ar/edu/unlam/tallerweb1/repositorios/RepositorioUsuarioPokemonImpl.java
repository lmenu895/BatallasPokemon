package ar.edu.unlam.tallerweb1.repositorios;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

}
