package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
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
import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {

	private ServicioPokemon servicioPokemon;
	private ServicioAtaquePokemon servicioAtaquePokemon;
	private ServicioObjeto servicioObjeto;
	private ServicioUsuario servicioUsuario;
	private ServicioBatalla servicioBatalla;
	private ServicioUsuarioAtaquePokemon servicioUsuarioAtaquePokemon;
	private ServicioUsuarioObjeto servicioUsuarioObjeto;

	@Autowired
	public ControladorBatalla(ServicioPokemon servicioPokemon, ServicioAtaquePokemon servicioAtaquePokemon,
			ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario, ServicioBatalla servicioBatalla,
			ServicioUsuarioAtaquePokemon servicioUsuarioAtaquePokemon, ServicioUsuarioObjeto servicioUsuarioObjeto) {
		this.servicioPokemon = servicioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioBatalla = servicioBatalla;
		this.servicioUsuarioAtaquePokemon = servicioUsuarioAtaquePokemon;
		this.servicioUsuarioObjeto = servicioUsuarioObjeto;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla(HttpServletRequest request,
			@RequestParam(required = false) List<Long> pokemonsLista,
			@RequestParam(required = false) String[] objetosLista) {

		if (request.getSession().getAttribute("usuario") == null
				|| request.getSession().getAttribute("principiante") != null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();

		try {
			this.servicioBatalla.inicioBatalla(pokemonsLista, objetosLista);
			List<Pokemon> pokemonsUsuario = new ArrayList<>();
			pokemonsLista.forEach(x -> pokemonsUsuario.add(this.servicioPokemon.buscarPokemon(x)));
			pokemonsUsuario.forEach(x -> x.setAtaques(this.servicioUsuarioAtaquePokemon
					.obtenerListaDeAtaquesActivos(x.getId(), (Long) request.getSession().getAttribute("id"))));
			List<Pokemon> pokemonsCpu = this.servicioPokemon.crearEquipoCpu(request);
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
		} catch (PokemonsInsuficientesException ex) {
			return errorInicioBatalla(ex.getMessage(), (Long) request.getSession().getAttribute("id"));
		} catch (ExcesoDeObjetosException ex) {
			return errorInicioBatalla(ex.getMessage(), (Long) request.getSession().getAttribute("id"));
		}
	}

	private ModelAndView errorInicioBatalla(String message, Long idUsuario) {
		ModelMap model = new ModelMap();
		model.put("error", message);
		model.put("listaPokemon", this.servicioUsuario.obtenerListaDePokemons(idUsuario));
		model.put("listaObjetos", this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(idUsuario));
		return new ModelAndView("elegir-equipo", model);
	}

	@RequestMapping(path = "/final-batalla", method = RequestMethod.POST)
	@ResponseBody
	public void finalBatalla(@RequestParam Long duracion, @RequestParam("datosPokemons") String datosPokemonsJson,
			HttpServletRequest request) {
		DatosPokemonBatalla[] listaDatosPokemons = new Gson().fromJson(datosPokemonsJson, DatosPokemonBatalla[].class);
		if (request.getSession().getAttribute("idsPokemonsCpu") != null) {
			request.getSession().removeAttribute("idsPokemonsCpu");
			this.servicioBatalla.finalBatalla(duracion, listaDatosPokemons,
					(Long) request.getSession().getAttribute("id"));
		}
	}
}