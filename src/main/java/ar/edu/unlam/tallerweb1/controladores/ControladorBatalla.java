package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private ServicioBatalla servicioBatalla;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaquePokemon servicioAtaquePokemon,
			ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario, ServicioBatalla servicioBatalla) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioBatalla = servicioBatalla;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla(HttpServletRequest request,
			@RequestParam(required = false) List<Long> pokemonsLista,
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

		List<Pokemon> pokemonsUsuario = new ArrayList<>();
		pokemonsLista.forEach(x -> pokemonsUsuario.add(this.servicioPokemon.buscarPokemon(x)));
		pokemonsUsuario.forEach(x -> x.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(x.getId())));

		List<Pokemon> pokemonsCpu = servicioPokemon.crearEquipoCpu();
		pokemonsCpu.forEach(x -> x.setAtaques(this.servicioAtaquePokemon.obtenerListaDeAtaques(x.getId())));

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

	@RequestMapping(path = "/final-batalla", method = RequestMethod.POST)
	@ResponseBody
	public void finalBatalla(@RequestParam String ganador, HttpServletRequest request) {
		this.servicioBatalla.finalBatalla(ganador, (Long) request.getSession().getAttribute("id"));
	}
}