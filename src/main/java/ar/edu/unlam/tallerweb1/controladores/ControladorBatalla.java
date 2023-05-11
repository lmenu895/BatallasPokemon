package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {
	
	private ServicioPokemon servicioPokemon;
	private ServicioAtaquePokemon servicioAtaquePokemon;
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaquePokemon servicioAtaquePokemon, ServicioAtaque servicioAtaque) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioAtaque = servicioAtaque;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla() {
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon(1l);
		model.put("pokemonUsuario", pokemonUsuario);
		model.put("pokemonCpu", pokemonCpu);
		return new ModelAndView("batalla", model);
	}
	
	@RequestMapping(path = "/obtener-pokemons-ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap obtenerPokemonsAjax() {
		ModelMap modelo = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon(1l);
		modelo.put("pokemonUsuario", pokemonUsuario);
		modelo.put("pokemonCpu", pokemonCpu);
		return modelo;
	}
}
