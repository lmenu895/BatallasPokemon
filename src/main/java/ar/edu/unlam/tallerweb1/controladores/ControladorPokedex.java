package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;


@Controller
public class ControladorPokedex {

	private ServicioUsuario servicioUsuario;
	private ServicioPokemon servicioPokemon;

	
	@Autowired
	public ControladorPokedex(ServicioUsuario servicioUsuario, ServicioPokemon servicioPokemon) {	
		this.servicioUsuario = servicioUsuario;
		this.servicioPokemon = servicioPokemon;
	}
	
	@RequestMapping("pokedex")
	public ModelAndView elegirEquipo(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("listaPokemonUsuario", this.servicioUsuario.obtenerListaDePokemons((Long)request.getSession().getAttribute("id")));
		model.put("listaPokemon", this.servicioPokemon.obtenerTodos());
		model.put("pokemonsUsuarioJson", new Gson().toJson(this.servicioUsuario.obtenerListaDePokemons((Long)request.getSession().getAttribute("id"))));
		model.put("pokemonsListaJson", new Gson().toJson(this.servicioPokemon.obtenerTodos()));
		return new ModelAndView("pokedex", model);
	}
	
}
