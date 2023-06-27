package ar.edu.unlam.tallerweb1.controladores;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import ar.edu.unlam.tallerweb1.exceptions.PagoExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.PagoNoAprobadoException;
import ar.edu.unlam.tallerweb1.exceptions.PlanInexistenteException;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.servicios.ServicioPago;
import ar.edu.unlam.tallerweb1.servicios.ServicioPlan;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPlan;

@Controller
@EnableScheduling
public class ControladorPlan {

	private ServicioPlan servicioPlan;
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPlan servicioUsuarioPlan;
	private ServicioPago servicioPago;

	@Autowired
	public ControladorPlan(ServicioPlan servicioPlan, ServicioUsuario servicioUsuario,
			ServicioUsuarioPlan servicioUsuarioPlan, ServicioPago servicioPago) {
		this.servicioPlan = servicioPlan;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPlan = servicioUsuarioPlan;
		this.servicioPago = servicioPago;
	}

	@RequestMapping(path = "/planes", method = RequestMethod.GET)
	public ModelAndView planes(HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap model = new ModelMap();
		Long idUsuario = (Long) request.getSession().getAttribute("id");
		Usuario u1 = servicioUsuario.buscar(idUsuario);
		UsuarioPlan up = this.servicioUsuarioPlan.buscarPlanPorUsuario(idUsuario);
		if (up != null) {
			model.put("nombreUsuarioPlan", up.getPlan().getNombre());
		}
		model.put("usuario", u1);
		model.put("planes", servicioPlan.obtenerPlanes());
		return new ModelAndView("planes", model);
	}

	@RequestMapping(path = "/generarPago/{planId}", method = RequestMethod.GET)
	public ModelAndView planes(@PathVariable Long planId, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		Plan plan = servicioPlan.consultarPlan(planId);

		MercadoPagoConfig.setAccessToken("TEST-4273752139461683-062512-21434a011fdba96b368ddb48a0a2f168-164214839");
		PreferenceClient client = new PreferenceClient();
		// Crea un Ã­tem en la preferencia
		PreferenceItemRequest item = PreferenceItemRequest.builder().title(plan.getNombre()).quantity(1)
				.unitPrice(new BigDecimal(plan.getPrecio())).build();
		List<PreferenceItemRequest> items = new ArrayList<>();
		items.add(item);
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
				.success("http://localhost:8080/batallas-pokemons/asignar-plan?planId=" + planId)
				.failure("http://localhost:8080/batallas-pokemons/planes").build();
		PreferenceRequest prefRequest = PreferenceRequest.builder().autoReturn("approved").binaryMode(true).items(items)
				.backUrls(backUrls).build();
		Preference response = null;
		try {
			response = client.create(prefRequest);
		} catch (MPException | MPApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:" + response.getInitPoint());
	}

//	@RequestMapping(path = "asignarplan/{plan}", method = RequestMethod.GET)
//	public ModelAndView elegirPlan(@PathVariable("plan") Long idP, HttpServletRequest request) throws MPException, MPApiException {
//		if (request.getSession().getAttribute("usuario") == null) {
//			return new ModelAndView("redirect:/login");
//		}
//
//		ModelMap modelo = new ModelMap();
//		Plan plan = servicioPlan.consultarPlan(idP);
//	
//		
//		try {
//			this.servicioUsuarioPlan.asignarPlan(idP, (Long) request.getSession().getAttribute("id"));
//			return new ModelAndView("redirect:/planAsignadoCorrectamente");
//		} catch (UsuarioSinBilleteraException ex) {
//			modelo.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
//			modelo.put("planes", servicioPlan.obtenerPlanes());
//			modelo.put("error", ex.getMessage());
//			return new ModelAndView("registroBilletera", modelo);
//		} catch (SaldoInsuficienteException ex) {
//			return new ModelAndView("redirect:/formularioSaldo");
//		}
//	}

	@RequestMapping("/mejorar-plan/{planId}")
	public ModelAndView mejorarPlan(@PathVariable Long planId, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		Plan plan = servicioPlan.consultarPlan(planId);
		try {
			this.servicioUsuarioPlan.verificarPlanBasico((Long) request.getSession().getAttribute("id"));
		} catch (PlanInexistenteException e) {
			return new ModelAndView("redirect:/planes");
		}
		Double precio = plan.getPrecio() * 0.3;
		MercadoPagoConfig.setAccessToken("TEST-4273752139461683-062512-21434a011fdba96b368ddb48a0a2f168-164214839");
		PreferenceClient client = new PreferenceClient();
		// Crea un ítem en la preferencia
		PreferenceItemRequest item = PreferenceItemRequest.builder().title(plan.getNombre()).quantity(1)
				.unitPrice(new BigDecimal(precio)).build();
		List<PreferenceItemRequest> items = new ArrayList<>();
		items.add(item);
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
				.success("http://localhost:8080/batallas-pokemons/asignar-plan?planId=" + planId + "&mejorar=true")
				.failure("http://localhost:8080/batallas-pokemons/planes").build();
		PreferenceRequest prefRequest = PreferenceRequest.builder().autoReturn("approved").binaryMode(true).items(items)
				.backUrls(backUrls).build();
		Preference response = null;
		try {
			response = client.create(prefRequest);
		} catch (MPException | MPApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:" + response.getInitPoint());
	}

	@RequestMapping(path = "/asignar-plan", method = RequestMethod.GET)
	public ModelAndView asignarPlan(@RequestParam Long planId, @RequestParam(required = false) Boolean mejorar,
			@RequestParam Long payment_id, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		try {
			if (mejorar != null && mejorar) {
				this.servicioUsuarioPlan.verificarPlanBasico((Long) request.getSession().getAttribute("id"));
			}
			Payment payment = this.servicioPago.verificarPago(payment_id);
			this.servicioUsuarioPlan.asignarPlan(planId, (Long) request.getSession().getAttribute("id"));
			this.servicioPago.guardar(payment, (Long) request.getSession().getAttribute("id"));
		} catch (MPException | MPApiException | PagoExistenteException | PlanInexistenteException
				| PagoNoAprobadoException ex) {
			return new ModelAndView("redirect:/planes");
		}
		return new ModelAndView("redirect:/planes");
	}

	@Scheduled(cron = " 0 0 0 1 * *")
	private void checkPlan() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
	}

}
