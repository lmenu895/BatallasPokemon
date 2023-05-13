package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {
	
	private ServicioPokemon servicioPokemon;
	
	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon) {
		this.servicioPokemon = servicioPokemon;
	}

	@RequestMapping("batalla")
	public ModelAndView iniciarBatalla() {
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon(1l);
		model.put("pokemonUsuario", pokemonUsuario);
		model.put("pokemonCpu", pokemonCpu);
		return new ModelAndView("batalla", model);
	}
	
	@RequestMapping(path = "obtener-pokemons-ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap obtenerPokemonsAjax() {
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon(1l);
		model.put("pokemonUsuario", pokemonUsuario);
		model.put("pokemonCpu", pokemonCpu);
		return model;
	}
}
