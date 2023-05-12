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
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Controller
public class ControladorUsuario {

	private ServicioObjeto servicioObjeto;
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario, ServicioUsuarioPokemon servicioUsuarioPokemon) {
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
	}

	@RequestMapping("/lista-objetos")
	public ModelAndView ObtenerObjetos() {
		ModelMap modelo = new ModelMap();
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		modelo.put("objetos", objetos);
		return new ModelAndView("lista-objetos", modelo);
	}

	@RequestMapping("elegir-equipo")
	public ModelAndView elegirEquipo(HttpServletRequest request) {
		ModelMap model = new ModelMap();
		Long id = (Long)request.getSession().getAttribute("id");
		List <UsuarioPokemon> lista = this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
		List <Pokemon> pokemons = servicioUsuarioPokemon.buscarPokemon(lista);
		model.put("listaPokemon", pokemons);
		return new ModelAndView("elegir-equipo", model);
	}
	
//	@RequestMapping(path = "/guardar-equipo", method = RequestMethod.POST)
//	public ModelAndView guardarPokemon(@ModelAttribute("pokemon") Pokemon pokemon,
//			@RequestParam("pokemonsLista") String[] pokemons,  HttpServletRequest request) {
//			Long id = (Long)request.getSession().getAttribute("id");
//			this.servicioUsuario.guardarEquipo(pokemons, id);
//			return new ModelAndView("ver-equipos");
//	}
	
	@RequestMapping(path = "/guardar-equipo", method = RequestMethod.POST)
	public ModelAndView guardarPokemon() {
			return new ModelAndView("ver-equipos");
	}
	
}
