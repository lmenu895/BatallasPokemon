package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	public ModelAndView guardarPokemon(@ModelAttribute("pokemon") Pokemon pokemon,
			@RequestParam("ataquesLista") String[] ataques, @RequestParam("frente") MultipartFile frente,
			@RequestParam("dorso") MultipartFile dorso, HttpServletRequest request) {
		try {
			if (this.servicioPokemon.guardarPokemon(pokemon, ataques, frente, dorso)) {
				return new ModelAndView("redirect:/login");
			} else {
				ModelMap model = new ModelMap();
				model.put("error", "Error al crear el pokemon, el nombre ya existe");
				model.put("listaAtaques", obtenerListaDeAtaques());
				return new ModelAndView("crear-pokemon", model);
			}
		} catch (IOException e) {
			ModelMap model = new ModelMap();
			model.put("error", "Error al crear el pokemon, el nombre ya existe");
			model.put("listaAtaques", obtenerListaDeAtaques());
			return new ModelAndView("crear-pokemon", model);
		}
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
