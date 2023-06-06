package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Controller
public class ControladorUsuario {

	private ServicioObjeto servicioObjeto;
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	private ServicioPokemon servicioPokemon;
	private ServicioUsuarioObjeto servicioUsuarioObjeto;

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario, ServicioUsuarioPokemon servicioUsuarioPokemon, ServicioPokemon servicioPokemon, ServicioUsuarioObjeto servicioUsuarioObjeto) {
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
		this.servicioPokemon = servicioPokemon;
		this.servicioUsuarioObjeto = servicioUsuarioObjeto;
	}
	
	@RequestMapping("lista-objetos")
	public ModelAndView ObtenerObjetos(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap modelo = new ModelMap();
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		modelo.put("objetos", objetos);
		return new ModelAndView("lista-objetos", modelo);
	}

	@RequestMapping("elegir-equipo")
	public ModelAndView elegirEquipo(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("listaPokemon", this.servicioUsuario.obtenerListaDePokemons((Long)request.getSession().getAttribute("id")));
		model.put("listaObjetos", this.servicioUsuario.obtenerListaDeObjetos((Long)request.getSession().getAttribute("id")));
		return new ModelAndView("elegir-equipo", model);
	}
	
	
	@RequestMapping(path = "guardar-equipo", method = RequestMethod.POST)
	public ModelAndView guardarPokemon(@RequestParam(required=false, name="pokemonsLista")  String[] pokemonsTraidos , @RequestParam(required=false, name="objetosLista")  String[] objetosTraidos,  HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		Long id = (Long)request.getSession().getAttribute("id");
		List <UsuarioPokemon> lista = this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
		List <Pokemon> pokemons = servicioUsuarioPokemon.buscarPokemon(lista);
		if(pokemonsTraidos == null || pokemonsTraidos.length != 3){
			model.put("error", "Debe seleccionar 3 pokemons");
			model.put("listaPokemon", this.servicioUsuario.obtenerListaDePokemons((Long)request.getSession().getAttribute("id")));
			model.put("listaObjetos", this.servicioUsuario.obtenerListaDeObjetos((Long)request.getSession().getAttribute("id")));
			return new ModelAndView("elegir-equipo", model);
		}
		if(objetosTraidos != null) {
			List <UsuarioObjeto> listaO = this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(id);
			List <Objeto> objetos = this.servicioUsuarioObjeto.buscarObjeto(listaO);
			objetos = this.servicioObjeto.buscarObjetoPorGrupo(objetosTraidos);
			model.put("objetos", objetos);
		}
		pokemons = this.servicioPokemon.buscarPokemonPorGrupo(pokemonsTraidos);
		model.put("equipo", pokemons);
		return new ModelAndView("ver-equipos", model);
		
	}
	
	@RequestMapping("/datos-de-usuario")
	public ModelAndView datosDeUsuario(HttpServletRequest request) {
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "datos-de-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/datos-de-usuario");
		}
	}
	
	@RequestMapping("/historial-de-batallas")
	public ModelAndView historialDeBatallas(HttpServletRequest request) {
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "historial-de-batallas");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/historial-de-batallas");
		}
	}
	
	@RequestMapping("/lista-pokemons-usuario")
	public ModelAndView listaPokemonsUsuario(HttpServletRequest request) {
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "lista-pokemons-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/lista-pokemons-usuario");
		}
	}
	
}
