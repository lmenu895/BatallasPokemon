package ar.edu.unlam.tallerweb1.servicios;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioBilletera;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPlan;

@Service("servicioUsuarioPlanImpl")
@Transactional
public class ServicioUsuarioPlanImpl implements ServicioUsuarioPlan {

	private RepositorioUsuarioPlan repositorioUsuarioPlan;
	private RepositorioUsuario repositorioUsuario;
	private RepositorioBilletera repositorioBilletera;
	private RepositorioPlan repositorioPlan;

	@Autowired
	public ServicioUsuarioPlanImpl(RepositorioUsuarioPlan repositorioUsuarioPlan, RepositorioUsuario repositorioUsuario,
			RepositorioBilletera repositorioBilletera, RepositorioPlan repositorioPlan) {
		this.repositorioUsuarioPlan = repositorioUsuarioPlan;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioBilletera = repositorioBilletera;
		this.repositorioPlan = repositorioPlan;
	}

	@Override
	public UsuarioPlan buscarPlanPorUsuario(Long idUsuario) {
		return this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
	}

	@Override
	public void asignarPlanAUsuario(Usuario usuario, Plan plan) {
		repositorioUsuarioPlan.asignarPlanAUsuario(usuario, plan);
		usuario.setPuntos(usuario.getPuntos() + plan.getPuntos());
		repositorioUsuario.modificar(usuario);
	}

	@Override
	public void asignarPlan(Long idPlan, Long idUsuario) throws UsuarioSinBilleteraException, SaldoInsuficienteException {
		Billetera billetera = this.repositorioBilletera.consultarBilleteraDeUsuario(idUsuario);
		Plan plan = this.repositorioPlan.consultarPlan(idPlan);
		Usuario usuario = this.repositorioUsuario.buscar(idUsuario);
		if (billetera == null) {
			throw new UsuarioSinBilleteraException("El usuario no posee una billetera creada");
		}
		if (billetera.getSaldo() < plan.getPrecio()) {
			throw new SaldoInsuficienteException("No posee el saldo suficiente para adquirir el plan");
		}
		billetera.setSaldo(billetera.getSaldo() - plan.getPrecio());
		usuario.setPuntos(usuario.getPuntos() + plan.getPuntos());
		this.repositorioUsuarioPlan.guardar(new UsuarioPlan().withPlan(plan).withUsuario(usuario));
	}
}
