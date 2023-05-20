package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.PermisosInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {

	private ServicioPokemon servicioPokemon;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon) {
		this.servicioPokemon = servicioPokemon;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon("Gardevoir");
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon("Charizard");
		model.put("pokemonUsuario", pokemonUsuario);
		model.put("pokemonCpu", pokemonCpu);
		return new ModelAndView("batalla", model);
	}

	@RequestMapping(path = "/obtener-pokemons-ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap obtenerPokemonsAjax(HttpServletRequest request) throws PermisosInsuficientesException {
		
		if (request.getSession().getAttribute("usuario") == null) {
			throw new PermisosInsuficientesException();
		}
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon("Gardevoir");
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon("Charizard");
		model.put("ataquesUsuario", pokemonUsuario.getAtaques());
		model.put("ataquesCpu", pokemonCpu.getAtaques());
		pokemonUsuario.setAtaques(new ArrayList<>());
		pokemonCpu.setAtaques(new ArrayList<>());
		model.put("pokemonUsuario", pokemonUsuario);
		model.put("pokemonCpu", pokemonCpu);
		return model;
	}
}
