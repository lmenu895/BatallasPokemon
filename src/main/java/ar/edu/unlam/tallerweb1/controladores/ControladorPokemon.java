package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.PermisosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorPokemon {

	private ServicioPokemon servicioPokemon;
	private ServicioAtaque servicioAtaque;
	private ServicioAtaquePokemon servicioAtaquePokemon;

	@Autowired
	public ControladorPokemon(ServicioPokemon servicioPokemon, ServicioAtaque servicioAtaque,
			ServicioAtaquePokemon servicioAtaquePokemon) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaque = servicioAtaque;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
	}

	@RequestMapping("/crear-pokemon")
	public ModelAndView crearPokemon(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		model.put("datosPokemon", new DatosPokemon());
		model.put("listaAtaques", obtenerListaDeAtaques());
		return new ModelAndView("crear-pokemon", model);
	}

	@RequestMapping(path = "/crear-pokemon", method = RequestMethod.POST)
	public ModelAndView crearPokemon(@ModelAttribute DatosPokemon datosPokemon, HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		try {
			this.servicioPokemon.guardarPokemon(datosPokemon);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (IOException | NombreExistenteException | SpriteNoIngresadoException ex) {
			ModelMap model = new ModelMap();
			model.put("error", ex.getMessage());
			model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
			return new ModelAndView("crear-pokemon", model);
		}
	}

	@RequestMapping("/lista-pokemons")
	public ModelAndView listaPokemons(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		model.put("listaPokemons", this.servicioPokemon.obtenerTodosLosPokemons());
		return new ModelAndView("lista-pokemons", model);
	}

	@RequestMapping("/modificar-pokemon/{id}")
	public ModelAndView modificarPokemon(@PathVariable Long id, HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		Pokemon pokemon = this.servicioPokemon.buscarPokemon(id);
		DatosPokemon datosPokemon = new DatosPokemon();
		datosPokemon.setAtaquesDesbloqueados(this.servicioAtaquePokemon.obtenetAtaquesDesbloqueados(pokemon.getId()));
		datosPokemon.setAtaquesBloqueados(this.servicioAtaquePokemon.obtenetAtaquesBloqueados(pokemon.getId()));
		model.put("pokemon", pokemon);
		model.put("datosPokemon", datosPokemon);
		model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
		return new ModelAndView("modificar-pokemon", model);
	}

	@RequestMapping(path = "/modificar-pokemon", method = RequestMethod.POST)
	public ModelAndView modificarPokemon(@ModelAttribute DatosPokemon datosPokemon, @RequestParam Long id,
			HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			return new ModelAndView("redirect:/home");
		}
		try {
			this.servicioPokemon.modificarPokemon(datosPokemon, id);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (Exception ex) {
			ModelMap model = new ModelMap();
			model.put("pokemon", this.servicioPokemon.buscarPokemon(id));
			model.put("error", ex.getMessage());
			model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
			return new ModelAndView("modificar-pokemon", model);
		}
	}

	@RequestMapping(path = "/borrar-pokemon", method = RequestMethod.POST)
	@ResponseBody
	public void borrarPokemon(@RequestParam String id, HttpServletRequest request)
			throws PermisosInsuficientesException {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin")) {
			throw new PermisosInsuficientesException();
		}
		this.servicioPokemon.borrarPokemon(Long.parseLong(id));
	}

	public List<Ataque> obtenerListaDeAtaques() {
		return this.servicioAtaque.obtenerTodosLosAtaques();
	}
}
