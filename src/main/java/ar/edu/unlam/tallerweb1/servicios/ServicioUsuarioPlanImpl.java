package ar.edu.unlam.tallerweb1.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.PlanInexistenteException;
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
	public void asignarPlan(Long idPlan, Long idUsuario) {
		Plan plan = this.repositorioPlan.consultarPlan(idPlan);
		Usuario usuario = this.repositorioUsuario.buscar(idUsuario);
		UsuarioPlan oldUp = this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
		UsuarioPlan newUp = new UsuarioPlan().withPlan(plan).withUsuario(usuario);
		this.repositorioUsuarioPlan.guardar(newUp);
		usuario.setPuntos(usuario.getPuntos() + plan.getPuntos());
		agregarTiradas(newUp);
		if (oldUp != null && oldUp.getPlan().getNombre().equals("Basico")) {
			usuario.setPuntos(usuario.getPuntos() - oldUp.getPlan().getPuntos());
			this.repositorioUsuarioPlan.darDeBajaElPlan(oldUp);
		}
	}

	@Override
	@Scheduled(cron = "0 0 * * * *")
	public void agregar() {
		List<UsuarioPlan> lista = repositorioUsuarioPlan.traerTodos();
		for (UsuarioPlan up : lista) {
			agregarTiradas(up);
		}
	}

	@Override
	public void agregarTiradas(UsuarioPlan up) {
		LocalDate dia = up.getDia();

		// Si es el dia que lo compra y es el plan de 100 le da la master
		if (dia.isEqual(LocalDate.now()) && up.getPlan().getNombre() == "Premium") {
			up.getUsuario().setTiradaMasterball(up.getUsuario().getTiradaMasterball() + 1);
		}

		// Si el dia es el mismo que hoy o el dia en el que entro es despues del dia
		// guardado en el plan
		// Le da la ultraball semanal
		if (dia.isEqual(LocalDate.now()) || LocalDate.now().isAfter(dia)) {
			up.getUsuario().setTiradaUltraball(up.getUsuario().getTiradaUltraball() + 1);
			dia = LocalDate.now().plusWeeks(1);
			up.setDia(dia);
		}

		// Si estamos en el d�a del vencimiento o m�s adelante, doy de baja el plan
		if (LocalDate.now().isEqual(up.getVencimiento()) || LocalDate.now().isAfter(up.getVencimiento())) {
			repositorioUsuarioPlan.darDeBajaElPlan(up);
		}
	}

	@Override
	public void verificarPlanBasico(Long idUsuario) throws PlanInexistenteException {
		UsuarioPlan up = this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
		if (up == null || !up.getPlan().getNombre().equals("Basico")) {
			throw new PlanInexistenteException("El usuario no tiene contratado el plan b�sico");
		}
	}

}
