package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

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
	public ModelAndView iniciarBatalla(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();

		List<Pokemon> pokemonsUsuario = new ArrayList<>();
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Charizard"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Wartortle"));
		pokemonsUsuario.add(this.servicioPokemon.buscarPokemon("Gyarados"));
		for (Pokemon pokemon : pokemonsUsuario) {
			pokemon.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(pokemon.getId()));
		}

		List<Pokemon> pokemonsCpu = new ArrayList<>();
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Wartortle"));
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Raticate"));
		pokemonsCpu.add(this.servicioPokemon.buscarPokemon("Pikachu"));
		for (Pokemon pokemon : pokemonsCpu) {
			pokemon.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(pokemon.getId()));
		}

		model.put("pokemonsUsuario", pokemonsUsuario);
		model.put("pokemonsCpu", pokemonsCpu.get(0));
		model.put("pokemonsUsuarioJson", new Gson().toJson(pokemonsUsuario));
		model.put("pokemonsCpuJson", new Gson().toJson(pokemonsCpu));

		return new ModelAndView("batalla", model);
	}
}
