package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;

@Controller
public class ControladorPokemon {
	
	private ServicioPokemon servicioPokemon;

	@Autowired
	public ControladorPokemon(ServicioPokemon servicioPokemon){
		this.servicioPokemon = servicioPokemon;
	}
	
	@RequestMapping("/crear-pokemon")
	public ModelAndView crearPokemon() {
		ModelMap model = new ModelMap();
		model.put("pokemon", new Pokemon());
		return new ModelAndView("crear-pokemon", model);
	}
	
}
