package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
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

	@RequestMapping("crear-pokemon")
	public ModelAndView crearPokemon() {
		ModelMap model = new ModelMap();
		model.put("pokemon", new Pokemon());
		model.put("listaAtaques", obtenerListaDeAtaques());
		return new ModelAndView("crear-pokemon", model);
	}

	@RequestMapping(path = "crear-pokemon", method = RequestMethod.POST)
	public ModelAndView crearPokemon(@ModelAttribute Pokemon pokemon, @RequestParam("ataquesLista") List<Long> ataques,
			@RequestParam MultipartFile frente, @RequestParam MultipartFile dorso, HttpServletRequest request) {
		try {
			this.servicioPokemon.guardarPokemon(pokemon, ataques, frente, dorso);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (IOException | NombreExistenteException | SpriteNoIngresadoException ex) {
			ModelMap model = new ModelMap();
			List<Ataque> ataquesSeleccionados = new ArrayList<>();
			ataques.forEach(x -> ataquesSeleccionados.add(this.servicioAtaque.buscarAtaque(x)));
			pokemon.setAtaques(ataquesSeleccionados);
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
	public ModelAndView modificarPokemon(@RequestParam Long id) {
		ModelMap model = new ModelMap();
		Pokemon pokemon = this.servicioPokemon.buscarPokemon(id);
		model.put("pokemon", pokemon);
		model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
		return new ModelAndView("modificar-pokemon", model);
	}

	@RequestMapping(path = "modificar-pokemon", method = RequestMethod.POST)
	public ModelAndView modificarPokemon(@ModelAttribute Pokemon pokemon, @RequestParam List<Long> ataquesLista,
			@RequestParam MultipartFile frente, @RequestParam MultipartFile dorso, @RequestParam String nombreAnterior,
			@RequestParam String frenteAnterior, @RequestParam String dorsoAnterior, HttpServletRequest request) {
		pokemon.setImagenFrente(frenteAnterior);
		pokemon.setImagenDorso(dorsoAnterior);
		try {
			this.servicioPokemon.modificarPokemon(pokemon, ataquesLista, frente, dorso, nombreAnterior);
			return new ModelAndView("redirect:/lista-pokemons");
		} catch (Exception ex) {
			ModelMap model = new ModelMap();
			List<Ataque> ataquesSeleccionados = new ArrayList<>();
			for (Long ataque : ataquesLista) {
				ataquesSeleccionados.add(this.servicioAtaque.buscarAtaque(ataque));
			}
			pokemon.setAtaques(ataquesSeleccionados);
			model.put("error", ex.getMessage());
			model.put("listaAtaques", this.servicioAtaque.obtenerTodosLosAtaques());
			return new ModelAndView("modificar-pokemon", model);
		}
	}

	@RequestMapping("borrar-pokemon")
	@ResponseBody
	public void borrarPokemon(@RequestParam String id) {
		this.servicioPokemon.borrarPokemon(Long.parseLong(id));
	}

	public List<Ataque> obtenerListaDeAtaques() {
		return this.servicioAtaque.obtenerTodosLosAtaques();
	}
}
