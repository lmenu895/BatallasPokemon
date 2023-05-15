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

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
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
	public ModelAndView confirmarRegistro(@ModelAttribute("ataque") Ataque datosAtaque, HttpServletRequest request) throws NombreExistenteException {
		ModelMap modelo = new ModelMap();
		
		try {
			this.servicioAtaque.guardarAtaque(datosAtaque);
			modelo.put("nombre", datosAtaque.getNombre());
			return new ModelAndView("redirect:/lista-ataques");
			
		} catch (NombreExistenteException e) {
			modelo.put("error", e.getMessage());
			return new ModelAndView("crear-ataque", modelo);
		}
		
	
	}
	
	@RequestMapping("/eliminar-ataque")
	@ResponseBody //Para usar con ajax
	public Boolean eliminarAtaque(@RequestParam("id") String id) {
		this.servicioAtaque.borrarAtaque(Long.parseLong(id));
		System.out.println(id);
		return true;
	}
	
	@RequestMapping("/lista-ataques")
	public ModelAndView listarAtaques() {
		ModelMap model = new ModelMap();
		model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
		return new ModelAndView("lista-ataques", model);
	}
	
	@RequestMapping("/modificar-ataque")
	@ResponseBody //Para usar con ajax
	public ModelAndView modificarAtaque(@RequestParam("id") Long id) {
		ModelMap model = new ModelMap();
		model.put("ataque", this.servicioAtaque.buscarAtaque(id));//le pasamos el modelattribute y traemos el ataque a la vista
		return new ModelAndView("modificar-ataque", model);
	}
	
	@RequestMapping(path = "/guardar-ataque-modificado", method = RequestMethod.POST)
	public ModelAndView confirmarRegistroModificado(@ModelAttribute("ataque") Ataque datosAtaque, HttpServletRequest request) throws CampoVacioException {
		this.servicioAtaque.modificarAtaque(datosAtaque);
		return new ModelAndView("redirect:/lista-ataques");
	}
	
	
	
	
	
	
	
	
	
	
	

}
