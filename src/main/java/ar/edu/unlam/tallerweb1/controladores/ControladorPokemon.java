package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorPokemon {

	private ServicioPokemon servicioPokemon;
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ControladorPokemon(ServicioPokemon servicioPokemon, ServicioAtaque servicioAtaque) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaque = servicioAtaque;
	}

	@RequestMapping("/crear-pokemon")
	public ModelAndView crearPokemon() {
		ModelMap model = new ModelMap();
		model.put("pokemon", new Pokemon());
		model.put("listaAtaques", obtenerListaDeAtaques());
		return new ModelAndView("crear-pokemon", model);
	}

	@RequestMapping(path = "/guardar-pokemon", method = RequestMethod.POST)
	public ModelAndView guardarPokemon(@ModelAttribute("pokemon") Pokemon pokemon, HttpServletRequest request) {
		if (this.servicioPokemon.guardarPokemon(pokemon)) {
			return new ModelAndView("redirect:/login");
		} else {
			ModelMap model = new ModelMap();
			model.put("error", "Error al crear el pokemon, el nombre ya existe");
			model.put("listaAtaques", obtenerListaDeAtaques());
			return new ModelAndView("crear-pokemon", model);
		}
	}

	public List<Ataque> obtenerListaDeAtaques() {
		return this.servicioAtaque.obtenerTodosLosAtaques();
	}

}
