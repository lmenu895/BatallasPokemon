package ar.edu.unlam.tallerweb1.controladores;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Plan;
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
	
	@RequestMapping(path = "/generarPago/{planId}", method = RequestMethod.GET)
	public ModelAndView planes(@PathVariable Long planId, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		Plan plan = servicioPlan.consultarPlan(planId);
		
        MercadoPagoConfig.setAccessToken("TEST-4273752139461683-062512-21434a011fdba96b368ddb48a0a2f168-164214839");
		PreferenceClient client = new PreferenceClient();
		// Crea un ítem en la preferencia
		PreferenceItemRequest item =
		   PreferenceItemRequest.builder()
		       .title(plan.getNombre())
		       .quantity(1)
		       .unitPrice(new BigDecimal(plan.getPrecio()))
		       .build();
		List<PreferenceItemRequest> items = new ArrayList<>();
		items.add(item);
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                .builder()
                .success("http://localhost:8080/batallas-pokemons/planAsignadoCorrectamente/")
                .failure("http://localhost:8080/batallas-pokemons/planAsignadoCorrectamente/")
                .pending("http://localhost:8080/batallas-pokemons/planAsignadoCorrectamente/")
                .build();
		PreferenceRequest prefRequest =
		   PreferenceRequest.builder().items(items).backUrls(backUrls).build();
        Preference response = null;
		try {
			response = client.create(prefRequest);
		} catch (MPException | MPApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.servicioUsuarioPlan.asignarPlan(planId, (Long) request.getSession().getAttribute("id"));
		} catch (UsuarioSinBilleteraException | SaldoInsuficienteException e) {
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
