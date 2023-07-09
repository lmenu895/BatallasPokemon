package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
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
import ar.edu.unlam.tallerweb1.exceptions.ObjetosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.servicios.*;

@Controller
public class ControladorBatalla {

	private ServicioBatalla servicioBatalla;
	private ServicioPokemon servicioPokemon;
	private ServicioObjeto servicioObjeto;

	@Inject
	private ServicioAtaquePokemon servicioAtaquePokemon;
	@Inject
	private ServicioUsuario servicioUsuario;
	@Inject
	private ServicioUsuarioAtaquePokemon servicioUsuarioAtaquePokemon;
	@Inject
	private ServicioUsuarioObjeto servicioUsuarioObjeto;

	@Autowired
	public ControladorBatalla(ServicioBatalla servicioBatalla, ServicioPokemon servicioPokemon,
			ServicioObjeto servicioObjeto) {
		this.servicioBatalla = servicioBatalla;
		this.servicioPokemon = servicioPokemon;
		this.servicioObjeto = servicioObjeto;
	}

	@RequestMapping("/batalla")
	public ModelAndView iniciarBatalla(HttpServletRequest request,
			@RequestParam(required = false) List<Long> pokemonsLista,
			@RequestParam(required = false) List<Long> objetosLista) {

		if (request.getSession().getAttribute("usuario") == null
				|| request.getSession().getAttribute("principiante") != null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();

		try {
			this.servicioBatalla.inicioBatalla(pokemonsLista, objetosLista,
					(Long) request.getSession().getAttribute("id"));
			List<Pokemon> pokemonsUsuario = new ArrayList<>();
			pokemonsLista.forEach(x -> pokemonsUsuario.add(this.servicioPokemon.buscar(x)));
			pokemonsUsuario.forEach(x -> x.setAtaques(this.servicioUsuarioAtaquePokemon.obtenerListaDeActivos(x.getId(),
					(Long) request.getSession().getAttribute("id"))));
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
		} catch (PokemonsInsuficientesException | ExcesoDeObjetosException | ObjetosInsuficientesException ex) {
			return errorInicioBatalla(ex.getMessage(), (Long) request.getSession().getAttribute("id"));
		}
	}

	private ModelAndView errorInicioBatalla(String message, Long idUsuario) {
		ModelMap model = new ModelMap();
		model.put("error", message);
		model.put("listaPokemon", this.servicioUsuario.obtenerListaDePokemons(idUsuario));
		model.put("listaUsuarioObjetos", this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(idUsuario));
		return new ModelAndView("elegir-equipo", model);
	}

	@RequestMapping(path = "/final-batalla", method = RequestMethod.POST)
	@ResponseBody
	public void finalBatalla(@RequestParam Long duracion, @RequestParam("datosPokemons") String datosPokemonsJson,
			@RequestParam(name = "objetosUtilizados", required = false) String objetosUtilizadosJson,
			HttpServletRequest request) {
		DatosPokemonBatalla[] datosPokemons = new Gson().fromJson(datosPokemonsJson, DatosPokemonBatalla[].class);
		String[] objetosUtilizados = objetosUtilizadosJson != null
				? new Gson().fromJson(objetosUtilizadosJson, String[].class)
				: null;

		if (request.getSession().getAttribute("idsPokemonsCpu") != null) {
			request.getSession().removeAttribute("idsPokemonsCpu");
			this.servicioBatalla.finalBatalla(duracion, datosPokemons, (Long) request.getSession().getAttribute("id"),
					objetosUtilizados);
		}

	}
}