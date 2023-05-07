package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.*;


import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {
	
	private ServicioPokemon servicioPokemon;
	private ServicioAtaque servicioAtaque;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaque servicioAtaque){
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaque = servicioAtaque;
	}
	
	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla() {
		ModelMap model = new ModelMap();
		Pokemon pokemonUsuarioObj = this.servicioPokemon.buscarPokemon(0l);
		Pokemon pokemonCpuObj = this.servicioPokemon.buscarPokemon(1l);
		String pokemonUsuarioStr = new Gson().toJson(pokemonUsuarioObj);
		String pokemonCpuStr = new Gson().toJson(pokemonCpuObj);
		model.put("usuarioString", pokemonUsuarioStr);
		model.put("cpuString", pokemonCpuStr);
		model.put("pokemonUsuario", pokemonUsuarioObj);
		model.put("pokemonCpu", pokemonCpuObj);
		return new ModelAndView("batalla", model);
	}

}
