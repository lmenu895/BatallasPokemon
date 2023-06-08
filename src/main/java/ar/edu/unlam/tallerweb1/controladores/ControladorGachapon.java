package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioGachapon;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;

@Controller
public class ControladorGachapon {
	
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	private ServicioGachapon servicioGachapon;
	private ServicioPokemon servicioPokemon;
	
	@Autowired
	public ControladorGachapon(ServicioGachapon servicioGachapon, ServicioUsuario servicioUsuario, ServicioPokemon servicioPokemon, ServicioUsuarioPokemon servicioUsuarioPokemon) {
		this.servicioPokemon=servicioPokemon;	
		this.servicioUsuario=servicioUsuario;
		this.servicioUsuarioPokemon=servicioUsuarioPokemon;
		this.servicioGachapon=servicioGachapon;
	}
	
	@RequestMapping("/gachapon")
	public ModelAndView gachapon( HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		Long id = (Long)request.getSession().getAttribute("id");
		Usuario usuario= servicioUsuario.buscarUsuario(id);
		ModelMap model = new ModelMap();
		model.put("puntos", usuario.getPuntos());
		return new ModelAndView("gachapon", model);
	}
	
	@RequestMapping(path= "gachapon-resultado", method = RequestMethod.POST)
	public ModelAndView gachaponResultado( HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		Integer monedas=Integer.parseInt( request.getParameter("monedas") );
		Long id = (Long)request.getSession().getAttribute("id");
		Usuario usuario= servicioUsuario.buscarUsuario(id);
		
		if(!this.servicioUsuario.restarPuntos(monedas, usuario)) {
			model.put("error", "Monedas Insuficientes");
			model.put("puntos", usuario.getPuntos());
			return new ModelAndView("gachapon", model);
		}
		Pokemon pokemon= this.servicioGachapon.tiradaGachapon(monedas);
		this.servicioUsuarioPokemon.guardarUsuarioPokemon(new UsuarioPokemon(usuario, pokemon));

		model.put("puntos", usuario.getPuntos());
		model.put("pokemon", pokemon);
		return new ModelAndView("gachapon-resultado", model);
	}
	
	
	

}
