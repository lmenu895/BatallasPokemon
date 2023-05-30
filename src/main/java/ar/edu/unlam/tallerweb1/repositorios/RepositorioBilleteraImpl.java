package ar.edu.unlam.tallerweb1.repositorios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository
public class RepositorioBilleteraImpl implements RepositorioBilletera{
	
	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioBilleteraImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void registrarBilletera(Billetera billetera) {
		final Session session = sessionFactory.getCurrentSession();
		session.save(billetera);
		
	}

	@Override
	public Billetera buscarBilleteraPorId(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		return session.get(Billetera.class, id);
	}

	
	@Override
	public Billetera consultarBilleteraDeUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		return (Billetera) session.createCriteria(Billetera.class)
				.createAlias("usuario", "usuarioBuscado")
				.add(Restrictions.eq("usuarioBuscado.id", usuario.getId()))
				.uniqueResult();
	}

	@Override
	public Double consultarSaldo(Billetera saldo) {
		return saldo.getSaldo();
	}

	@Override
	public void ingresarSaldo(Billetera billetera, Double monto) {
		final Session session = sessionFactory.getCurrentSession();
		 Double saldoActual = billetera.getSaldo() + monto;
		 billetera.setSaldo(saldoActual);
		 session.update(billetera);
		
	}

}
