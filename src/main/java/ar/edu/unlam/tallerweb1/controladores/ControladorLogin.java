package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

	// La anotacion @Autowired indica a Spring que se debe utilizar el contructor
	// como mecanismo de inyección de dependencias,
	// es decir, qeue lo parametros del mismo deben ser un bean de spring y el
	// framewrok automaticamente pasa como parametro
	// el bean correspondiente, en este caso, un objeto de una clase que implemente
	// la interface ServicioLogin,
	// dicha clase debe estar anotada como @Service o @Repository y debe estar en un
	// paquete de los indicados en
	// applicationContext.xml
	private ServicioLogin servicioLogin;
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	public ControladorLogin(ServicioLogin servicioLogin, ServicioUsuario servicioUsuario) {
		this.servicioLogin = servicioLogin;
		this.servicioUsuario = servicioUsuario;
	}

	// Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es
	// invocada por metodo http GET
	@RequestMapping("/login")
	public ModelAndView irALogin(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") != null) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap modelo = new ModelMap();
		modelo.put("datosLogin", new DatosLogin());
		return new ModelAndView("login", modelo);
	}

	// Este metodo escucha la URL validar-login siempre y cuando se invoque con
	// metodo http POST
	// El metodo recibe un objeto Usuario el que tiene los datos ingresados en el
	// form correspondiente y se corresponde con el modelAttribute definido en el
	// tag form:form
	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {

		ModelMap model = new ModelMap();

		// invoca el metodo consultarUsuario del servicio y hace un redirect a la URL
		// /home, esto es, en lugar de enviar a una vista
		// hace una llamada a otro action a traves de la URL correspondiente a esta
		Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
		if (usuarioBuscado != null) {
			request.getSession().setAttribute("esAdmin", usuarioBuscado.getEsAdmin());
			request.getSession().setAttribute("id", usuarioBuscado.getId());
			request.getSession().setAttribute("usuario", usuarioBuscado.getUsuario());
			if (usuarioBuscado.getEsAdmin()) {
				return new ModelAndView("redirect:/admin");
			}
			return new ModelAndView("redirect:/home");
		} else {
			// si el usuario no existe agrega un mensaje de error en el modelo.
			model.put("error", "Usuario o clave incorrecta");
			return new ModelAndView("login", model);
		}
	}

	@RequestMapping("/registrar-usuario") // RECIBE EL LLAMADO DEL ACTION
	@ResponseBody
	public ModelAndView irARegistrar(HttpServletRequest request) { // CREAS EL METODO MODEL AND VIEW

		String tipoDeRequest = request.getHeader("X-Requested-With");
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap modelo = new ModelMap(); // CREAS EL MODEL MAP
		modelo.put("usuario", new Usuario());// LE METIMOS USUARIO VACIO - MODEL ATTRIBUTE
		return new ModelAndView("partial/registro-usuario", modelo); // DEVOLVEMOS LA VISTA NUEVA CON EL MODELO DE DATOS
	}

	@RequestMapping(path = "/registrarme", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap confirmarRegistro(@ModelAttribute("usuario") Usuario datosUsuario) throws Exception {
		ModelMap model = new ModelMap();
		try {
			this.servicioLogin.guardarCliente(datosUsuario);
		} catch (UsuarioExistenteException | CampoVacioException e) {
			model.put("error", e.getMessage());
			return model;
		}
		model.put("exito", "Registro exitoso!");
		return model;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/login");
	}

	// Escucha la URL /home por GET, y redirige a una vista.
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ModelAndView irAHome( HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null)
			return new ModelAndView("redirect:/login");
		ModelMap model = new ModelMap();
		model.put("usuario", servicioUsuario.buscarUsuario((Long)request.getSession().getAttribute("id")));
		return new ModelAndView("home", model);
	}

	// Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la
	// url /login directamente.
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/home");
	}

}
