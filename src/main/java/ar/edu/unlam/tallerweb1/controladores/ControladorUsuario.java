package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioAtaquePokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;

@Controller
public class ControladorUsuario {

	private ServicioObjeto servicioObjeto;
	private ServicioPokemon servicioPokemon;
	public ServicioUsuario servicioUsuario;

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto, ServicioPokemon servicioPokemon, ServicioUsuario servicioUsuario) {
		this.servicioObjeto = servicioObjeto;
		this.servicioPokemon = servicioPokemon;
		this.servicioUsuario = servicioUsuario;
	}

	@RequestMapping("/lista-objetos")
	public ModelAndView ObtenerObjetos() {
		ModelMap modelo = new ModelMap();
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		modelo.put("objetos", objetos);
		return new ModelAndView("lista-objetos", modelo);
	}

	@RequestMapping("elegir-equipo")
	public ModelAndView elegirEquipo() {
		ModelMap model = new ModelMap();
		model.put("listaPokemon", this.servicioPokemon.obtenerTodosLosPokemons());
		return new ModelAndView("elegir-equipo", model);
	}
	
	@RequestMapping(path = "/guardar-equipo", method = RequestMethod.POST)
	public ModelAndView guardarPokemon(@ModelAttribute("pokemon") Pokemon pokemon,
			@RequestParam("pokemonsLista") String[] pokemons,  HttpServletRequest request) {
			Long id = (Long)request.getSession().getAttribute("id");
			this.servicioUsuario.guardarEquipo(pokemons, id);
			
			return new ModelAndView("ver-equipos");
	}
}
