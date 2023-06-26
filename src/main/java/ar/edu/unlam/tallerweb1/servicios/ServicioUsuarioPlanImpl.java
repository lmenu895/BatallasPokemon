package ar.edu.unlam.tallerweb1.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPlan;

@Service("servicioUsuarioPlanImpl")
@Transactional
@EnableScheduling
public class ServicioUsuarioPlanImpl implements ServicioUsuarioPlan {

	private RepositorioUsuarioPlan repositorioUsuarioPlan;
	private RepositorioUsuario repositorioUsuario;
	private RepositorioPlan repositorioPlan;

	@Autowired
	public ServicioUsuarioPlanImpl(RepositorioUsuarioPlan repositorioUsuarioPlan, RepositorioUsuario repositorioUsuario,
			RepositorioPlan repositorioPlan) {
		this.repositorioUsuarioPlan = repositorioUsuarioPlan;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioPlan = repositorioPlan;
	}

	@Override
	public UsuarioPlan buscarPlanPorUsuario(Long idUsuario) {
		return this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
	}

	@Override
	public void asignarPlan(Long idPlan, Long idUsuario)
			throws UsuarioSinBilleteraException, SaldoInsuficienteException {
//		Billetera billetera = this.repositorioBilletera.consultarBilleteraDeUsuario(idUsuario);
//		Reviso si el usuario ya tiene el plan basico para borrarlo y que se le asigne solo el premium
		UsuarioPlan up = this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
		if (up != null) {
			this.repositorioUsuarioPlan.darDeBajaElPlan(up);
		}
		Plan plan = this.repositorioPlan.consultarPlan(idPlan);
		Usuario usuario = this.repositorioUsuario.buscar(idUsuario);
//		if (billetera == null) {
//			throw new UsuarioSinBilleteraException("El usuario no posee una billetera creada");
//		}
//		if (billetera.getSaldo() < plan.getPrecio()) {
//			throw new SaldoInsuficienteException("No posee el saldo suficiente para adquirir el plan");
//		}
		this.repositorioUsuarioPlan.guardar(new UsuarioPlan().withPlan(plan).withUsuario(usuario));
//		billetera.setSaldo(billetera.getSaldo() - plan.getPrecio());
		usuario.setPuntos(usuario.getPuntos() + plan.getPuntos());
		// Integer precio = (int) plan.getPrecio();
		Double precioArg = plan.getPrecio();
		agregarTiradas(usuario, precioArg);
		agregar();
	}

	@Override
	@Scheduled(cron = "0 0 * * * *")
	public void agregar() {
		List<UsuarioPlan> lista = repositorioUsuarioPlan.traerTodos();
		for (UsuarioPlan up : lista) {
			agregarTiradas(up.getUsuario(), up.getPlan().getPrecio());
		}

	}

	@Override
	public void agregarTiradas(Usuario usuario, Double precio) {
		UsuarioPlan up = repositorioUsuarioPlan.buscarPorUsuario(usuario.getId());
		LocalDate dia = up.getDia();

		// Si es el dia que lo compra y es el plan de 100 le da la master
		if (dia.isEqual(LocalDate.now()) && precio == 100) {
			usuario.setTiradaMasterball(usuario.getTiradaMasterball() + 1);
		}

		// Si el dia es el mismo que hoy o el dia en el que entro es despues del dia
		// guardado en el plan
		// Le da la ultraball semanal
		if (dia.isEqual(LocalDate.now()) || LocalDate.now().isAfter(dia)) {
			usuario.setTiradaUltraball(usuario.getTiradaUltraball() + 1);
			dia = LocalDate.now().plusWeeks(1);
			up.setDia(dia);
			repositorioUsuarioPlan.modificar(up);
		}

		// si el dia es mayor o igual al vencimiento, doy de baja el plan
		if (dia.isAfter(up.getVencimiento()) || dia.isEqual(up.getVencimiento())) {
			repositorioUsuarioPlan.darDeBajaElPlan(this.repositorioUsuarioPlan.buscarPorUsuario(usuario.getId()));
		}
	}

}
