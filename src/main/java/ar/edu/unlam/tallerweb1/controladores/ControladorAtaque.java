package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAtaque;

@Controller
public class ControladorAtaque {
	
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ControladorAtaque(ServicioAtaque servicioAtaque){
		this.servicioAtaque = servicioAtaque;
	}
	
	@RequestMapping("/crear-ataque")
	public ModelAndView crearAtaque() {
		ModelMap model = new ModelMap();
		model.put("ataque", new Ataque());
		return new ModelAndView("crear-ataque", model);
	}
	
	@RequestMapping(path = "/guardar-ataque", method = RequestMethod.POST)
	public ModelAndView confirmarRegistro(@ModelAttribute("ataque") Ataque datosAtaque, HttpServletRequest request) throws CampoVacioException {
		ModelMap modelo = new ModelMap();
		
		try {
			this.servicioAtaque.guardarAtaque(datosAtaque);
			
		} catch (CampoVacioException e) {
			modelo.put("error", e.getMessage());
			return new ModelAndView("crear-ataque", modelo);
		}
		
		modelo.put("ataque", datosAtaque.getNombre());
		return new ModelAndView("guardar-ataque", modelo);
	}

}
