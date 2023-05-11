package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
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
	public ModelAndView crearPokemon() {
		ModelMap model = new ModelMap();
		model.put("pokemon", new Pokemon());
		model.put("listaAtaques", obtenerListaDeAtaques());
		return new ModelAndView("crear-pokemon", model);
	}

	@RequestMapping(path = "/guardar-pokemon", method = RequestMethod.POST)
	public ModelAndView guardarPokemon(@ModelAttribute("pokemon") Pokemon pokemon,
			@RequestParam("ataquesLista") String[] ataques, @RequestParam("frente") MultipartFile frente,
			@RequestParam("dorso") MultipartFile dorso, HttpServletRequest request) {
		try {
			this.servicioPokemon.guardarPokemon(pokemon, ataques, frente, dorso);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (IOException | NombreExistenteException ex) {
			ModelMap model = new ModelMap();
			model.put("error", ex.getMessage());
			model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
			return new ModelAndView("crear-pokemon", model);
		}
	}

	@RequestMapping("lista-pokemons")
	public ModelAndView listaPokemons() {
		ModelMap model = new ModelMap();
		model.put("listaPokemons", this.servicioPokemon.obtenerTodosLosPokemons());
		return new ModelAndView("lista-pokemons", model);
	}

	@RequestMapping("modificar-pokemon")
	public ModelAndView modificarPokemon(@RequestParam("id") Long id) {
		ModelMap model = new ModelMap();
		Pokemon pokemon = this.servicioPokemon.buscarPokemon(id);
		model.put("pokemon", pokemon);
		model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
		return new ModelAndView("modificar-pokemon", model);
	}

	@RequestMapping(path = "/guardar-pokemon-modificado", method = RequestMethod.POST)
	public ModelAndView guardarPokemonModificado(@ModelAttribute("pokemon") Pokemon pokemon,
			@RequestParam("ataquesLista") String[] ataques, @RequestParam("frente") MultipartFile frente,
			@RequestParam("dorso") MultipartFile dorso, HttpServletRequest request) {
		try {
			this.servicioPokemon.modificarPokemon(pokemon, ataques, frente, dorso);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (Exception ex) {
			ModelMap model = new ModelMap();
			model.put("error", ex.getMessage());
			model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
			return new ModelAndView("modificar-pokemon", model);
		}
	}

	@RequestMapping("borrar-pokemon")
	@ResponseBody
	public void borrarPokemon(@RequestParam("id") String id) {
		this.servicioPokemon.borrarPokemon(Long.parseLong(id));
	}

//	@RequestMapping(path = "/imagen", method = RequestMethod.POST)
//	@ResponseBody
//	public String imagen(@RequestParam("imagen") MultipartFile imagen) {
//		return StringUtils.cleanPath(imagen.getOriginalFilename());
//	}

	public List<Ataque> obtenerListaDeAtaques() {
		return this.servicioAtaque.obtenerTodosLosAtaques();
	}
}
