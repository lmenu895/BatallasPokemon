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

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaquePokemon servicioAtaquePokemon) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla() {
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuarioObj = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpuObj = this.servicioPokemon.buscarPokemon(1l);
		pokemonUsuarioObj.setAtaques(this.servicioAtaquePokemon.buscarAtaques(pokemonUsuarioObj.getId()));
		pokemonCpuObj.setAtaques(this.servicioAtaquePokemon.buscarAtaques(pokemonCpuObj.getId()));
		model.put("pokemonUsuario", pokemonUsuarioObj);
		model.put("pokemonCpu", pokemonCpuObj);
		return new ModelAndView("batalla", model);
	}
	
	@RequestMapping(path = "/obtener-pokemons-ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap obtenerPokemonsAjax() {
		ModelMap modelo = new ModelMap();
		Pokemon pokemonUsuario = this.servicioPokemon.buscarPokemon(2l);
		Pokemon pokemonCpu = this.servicioPokemon.buscarPokemon(1l);
		pokemonUsuario.setAtaques(this.servicioAtaquePokemon.buscarAtaques(pokemonUsuario.getId()));
		pokemonCpu.setAtaques(this.servicioAtaquePokemon.buscarAtaques(pokemonCpu.getId()));
		modelo.put("pokemonUsuario", pokemonUsuario);
		modelo.put("pokemonCpu", pokemonCpu);
		return modelo;
	}
}
