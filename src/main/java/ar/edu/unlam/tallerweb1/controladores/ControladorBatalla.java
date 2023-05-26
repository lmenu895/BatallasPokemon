package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {

	private ServicioPokemon servicioPokemon;
	private ServicioAtaquePokemon servicioAtaquePokemon;
	private ServicioObjeto servicioObjeto;
	private ServicioUsuario servicioUsuario;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaquePokemon servicioAtaquePokemon,
			ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla(HttpServletRequest request,
			@RequestParam(required = false) List<String> pokemonsLista,
			@RequestParam(required = false) String[] objetosLista) {

		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();

		if (pokemonsLista == null || pokemonsLista.size() != 3 || objetosLista != null && objetosLista.length > 3) {
			model.put("error", "Debe seleccionar 3 pokemons y un máximo de 3 objetos");
			model.put("listaPokemon",
					this.servicioUsuario.obtenerListaDePokemons((Long) request.getSession().getAttribute("id")));
			model.put("listaObjetos",
					this.servicioUsuario.obtenerListaDeObjetos((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("elegir-equipo", model);
		}
		Collections.sort(pokemonsLista);

		List<Pokemon> pokemonsUsuario = new ArrayList<>();
		pokemonsLista.forEach(x -> pokemonsUsuario
				.add(this.servicioPokemon.buscarPokemon(Long.parseLong(x.substring(x.indexOf('p') + 1)))));
		for (Pokemon pokemon : pokemonsUsuario) {
			pokemon.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(pokemon.getId()));
		}

		List<Pokemon> pokemonsCpu = servicioPokemon.crearEquipoCpu();
		for (Pokemon pokemon : pokemonsCpu) {
			pokemon.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(pokemon.getId()));
		}

		if (objetosLista != null) {
			List<Objeto> objetosUsuario = this.servicioObjeto.buscarObjetoPorGrupo(objetosLista);
			model.put("objetosUsuario", objetosUsuario);
			model.put("objetosUsuarioJson", new Gson().toJson(objetosUsuario));
		}

		model.put("pokemonsUsuario", pokemonsUsuario);
		model.put("pokemonsCpu", pokemonsCpu);
		model.put("pokemonsUsuarioJson", new Gson().toJson(pokemonsUsuario));
		model.put("pokemonsCpuJson", new Gson().toJson(pokemonsCpu));

		return new ModelAndView("batalla", model);
	}
}