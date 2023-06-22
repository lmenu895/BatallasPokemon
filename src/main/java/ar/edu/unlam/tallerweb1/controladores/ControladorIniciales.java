package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;

@Controller
public class ControladorIniciales {

	private ServicioUsuario servicioUsuario;
	private ServicioPokemon servicioPokemon;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;

	@Autowired
	public ControladorIniciales(ServicioUsuario servicioUsuario, ServicioPokemon servicioPokemon,
			ServicioUsuarioPokemon servicioUsuarioPokemon) {
		this.servicioUsuario = servicioUsuario;
		this.servicioPokemon = servicioPokemon;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
	}

	@RequestMapping("iniciales")
	public ModelAndView iniciales(HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		List<Pokemon> lista = this.servicioUsuario
				.obtenerListaDePokemons((Long) request.getSession().getAttribute("id"));
		if (lista.size() > 0) {
			return new ModelAndView("redirect:/home");
		}
		ModelMap model = new ModelMap();
		lista = this.servicioPokemon.obtenerTodosLosComunes();
		model.put("listaPokemonComunes", lista);
		return new ModelAndView("iniciales", model);
	}

	@RequestMapping(path = "guardar-inicial", method = RequestMethod.POST)
	public ModelAndView guardarInicial(@RequestParam(required = true, name = "pokemon") String pokemonElegido,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		Pokemon pokemon = servicioPokemon.buscar(Long.parseLong(pokemonElegido));
		this.servicioUsuarioPokemon
				.guardarUsuarioPokemon(new UsuarioPokemon().withUsuario(usuario).withPokemon(pokemon));
		ModelMap model = new ModelMap();
		model.put("usuario", usuario);
		return new ModelAndView("home", model);
	}

}
