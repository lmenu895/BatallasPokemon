package ar.edu.unlam.tallerweb1.controladores;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioBilletera;
import ar.edu.unlam.tallerweb1.servicios.ServicioPlan;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPlan;

@Controller
@EnableScheduling
public class ControladorPlan {

	private ServicioPlan servicioPlan;
	private ServicioUsuario servicioUsuario;
	private ServicioBilletera servicioBilletera;
	private ServicioUsuarioPlan servicioUsuarioPlan;

	@Autowired
	public ControladorPlan(ServicioPlan servicioPlan, ServicioUsuario servicioUsuario,
			ServicioBilletera servicioBilletera, ServicioUsuarioPlan servicioUsuarioPlan) {
		this.servicioPlan = servicioPlan;
		this.servicioUsuario = servicioUsuario;
		this.servicioBilletera = servicioBilletera;
		this.servicioUsuarioPlan = servicioUsuarioPlan;
	}

	@RequestMapping(path = "/planes", method = RequestMethod.GET)
	public ModelAndView planes(HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap modelo = new ModelMap();
		Long idUsuario = (Long) request.getSession().getAttribute("id");
		Usuario u1 = servicioUsuario.buscar(idUsuario);
		Billetera billetera = servicioBilletera.consultarBilleteraDeUsuario(u1.getId());
		if (u1 != null) {
			if (servicioUsuarioPlan.buscarPlanPorUsuario(idUsuario) == null) {
				if (billetera != null) {
					modelo.put("billetera", billetera);
					modelo.put("usuario", u1);
					modelo.put("planes", servicioPlan.obtenerPlanes());

					return new ModelAndView("planes", modelo);
				}else {
					modelo.put("usuario", u1);
					modelo.put("planes", servicioPlan.obtenerPlanes());
					modelo.put("error", "Usted no posee billetera para pagar el plan. Por favor, genere una");
					return new ModelAndView("registroBilletera", modelo);
				}

			}
		}
		return new ModelAndView("redirect:/login");
	}

	@RequestMapping(path = "asignarplan/{plan}", method = RequestMethod.GET)
	public ModelAndView elegirPlan(@PathVariable("plan") Long idP, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap modelo = new ModelMap();
		try {
			this.servicioUsuarioPlan.asignarPlan(idP, (Long) request.getSession().getAttribute("id"));
			return new ModelAndView("redirect:/planAsignadoCorrectamente");
		} catch (UsuarioSinBilleteraException ex) {
			modelo.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
			modelo.put("planes", servicioPlan.obtenerPlanes());
			modelo.put("error", ex.getMessage());
			return new ModelAndView("registroBilletera", modelo);
		} catch (SaldoInsuficienteException ex) {
			return new ModelAndView("redirect:/formularioSaldo");
		}
	}

	@RequestMapping(path = "/planAsignadoCorrectamente", method = RequestMethod.GET)
	public ModelAndView planAsignadoCorrectamente(HttpServletRequest request) {

		ModelMap modelo = new ModelMap();
		Long idUsuario = (Long) request.getSession().getAttribute("id");

		Usuario u1 = servicioUsuario.buscar(idUsuario);

		if (u1 != null) {
			modelo.put("usuario", u1);

			return new ModelAndView("planAsignado", modelo);
		} else {

			return new ModelAndView("redirect:/login");
		}
	}

	@Scheduled(cron = " 0 0 0 1 * *")
	private void checkPlan() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
	}

}
