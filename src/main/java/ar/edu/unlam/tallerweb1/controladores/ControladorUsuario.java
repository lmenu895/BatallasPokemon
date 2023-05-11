package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.modelo.Objeto;

@Controller
public class ControladorUsuario {

	private ServicioObjeto servicioObjeto;

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto) {
		this.servicioObjeto = servicioObjeto;
	}

	@RequestMapping("/lista-objetos")
	public ModelAndView ObtenerObjetos() {
		ModelMap modelo = new ModelMap();
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		modelo.put("objetos", objetos);
		return new ModelAndView("lista-objetos", modelo);
	}

}