package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Ataque;

@Repository("repositorioAtaque")
public class RepositorioAtaqueImpl implements RepositorioAtaque {

	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioAtaqueImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ataque> obtenerTodosLosAtaques() {
		return this.sessionFactory.getCurrentSession().createQuery("from Ataque").list();
	}

	@Override
	public Ataque buscarAtaque(Long id) {
		return this.sessionFactory.getCurrentSession().get(Ataque.class, id); // aca se le pide para buscar QUE CLASE es y por que PARAMETRO BUSCAR
	}

	@Override
	public void guardarAtaque(Ataque datosAtaque) {
		this.sessionFactory.getCurrentSession().save(datosAtaque);
	}

}
