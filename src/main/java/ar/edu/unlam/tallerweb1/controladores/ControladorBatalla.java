package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.List;

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

		List<Pokemon> pokemonsUsuario = new ArrayList<>();
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Charizard"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Wartortle"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Gyarados"));
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon("Venusaur");

		model.put("pokemonsUsuario", pokemonsUsuario);

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

		ModelMap ataquesUsuario = new ModelMap();
		ModelMap ataquesCpu = new ModelMap();
		List<Pokemon> pokemonsUsuario = new ArrayList<>();
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Charizard"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Wartortle"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Gyarados"));
		for (Pokemon pokemon : pokemonsUsuario) {
			ataquesUsuario.put(pokemon.getNombre(), pokemon.getAtaques());
			pokemon.setAtaques(new ArrayList<>());
		}
		List<Pokemon> pokemonsCpu = new ArrayList<>();
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Venusaur"));
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Raticate"));
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Pikachu"));
		for (Pokemon pokemon : pokemonsCpu) {
			ataquesCpu.put(pokemon.getNombre(), pokemon.getAtaques());
			pokemon.setAtaques(new ArrayList<>());
		}
		model.put("pokemonsUsuario", pokemonsUsuario);
		model.put("pokemonsCpu", pokemonsCpu);
		model.put("ataquesUsuario", ataquesUsuario);
		model.put("ataquesCpu", ataquesCpu);

		return model;
	}
}
