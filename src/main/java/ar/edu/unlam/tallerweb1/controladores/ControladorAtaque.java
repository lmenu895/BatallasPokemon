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
import ar.edu.unlam.tallerweb1.exceptions.PermisosInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.servicios.ServicioAtaque;

@Controller
public class ControladorAtaque {
	
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ControladorAtaque(ServicioAtaque servicioAtaque){
		this.servicioAtaque = servicioAtaque;
	}
	
	@RequestMapping("/crear-ataque") //se utiliza para asignar solicitudes web a clases de controlador específicas y/o métodos de controlador.
	public ModelAndView crearAtaque(HttpServletRequest request) {//Proporciona acceso a los datos de las cabeceras HTTP, cookies, parámetros pasados por el usuario, etc, sin tener que parsear nosotros a mano los datos de formulario de la petición. {
		
		if (request.getSession().getAttribute("usuario") == null //Si el usuario es null o no es admin te manda al home
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap(); //creamos el modelmap
		model.put("ataque", new Ataque());// mandamos un ataque con el ataque vacio
		return new ModelAndView("crear-ataque", model); //retornmos el modelo y la vista crear-ataque
	}
	
	@RequestMapping(path = "/guardar-ataque", method = RequestMethod.POST)
	public ModelAndView confirmarRegistro(@ModelAttribute("ataque") Ataque datosAtaque, HttpServletRequest request) throws NombreExistenteException {
		
		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap modelo = new ModelMap();  //creamos el modelmap
		
		try {
			this.servicioAtaque.guardarAtaque(datosAtaque);	 //Try del ataque a guardar
			modelo.put("nombre", datosAtaque.getNombre());
			return new ModelAndView("redirect:/lista-ataques");
			
		} catch (NombreExistenteException e) {
			modelo.put("error", e.getMessage());				//catch de la exception si ya creamos ese ataque
			return new ModelAndView("crear-ataque", modelo);
		}
		
	
	}
	
	@RequestMapping("/eliminar-ataque")
	@ResponseBody //Para usar con ajax
	public Boolean eliminarAtaque(@RequestParam("id") String id, HttpServletRequest request) throws PermisosInsuficientesException {
		
		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {  //si no es admin no deja borrar
			throw new PermisosInsuficientesException();
		}
		this.servicioAtaque.borrarAtaque(Long.parseLong(id)); //borramos el ataque por ID
		System.out.println(id);
		return true;
	}
	
	@RequestMapping("/lista-ataques")
	public ModelAndView listarAtaques(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());  //creamos el modelo leponemos la lista de ataque y la retornamos
		return new ModelAndView("lista-ataques", model);
	}
	
	@RequestMapping("/modificar-ataque")
	@ResponseBody //Para usar con ajax
	public ModelAndView modificarAtaque(@RequestParam("id") Long id, HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		model.put("ataque", this.servicioAtaque.buscarAtaque(id));//le pasamos el modelattribute y traemos el ataque a la vista
		return new ModelAndView("modificar-ataque", model); 
	}
	
	@RequestMapping(path = "/guardar-ataque-modificado", method = RequestMethod.POST)
	public ModelAndView confirmarRegistroModificado(@ModelAttribute("ataque") Ataque datosAtaque, HttpServletRequest request) throws CampoVacioException {
		
		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		this.servicioAtaque.modificarAtaque(datosAtaque);
		return new ModelAndView("redirect:/lista-ataques");
	}
	
	
	
	
	
	
	
	
	
	
	

}
