package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioAtaquePokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioBatalla;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaCorta;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaIncompatible;
import ar.edu.unlam.tallerweb1.exceptions.FormatoDeEmailIncorrecto;
import ar.edu.unlam.tallerweb1.exceptions.LimiteDeAtaquesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonNoObtenidoException;
import ar.edu.unlam.tallerweb1.exceptions.PuntosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Batalla;
import ar.edu.unlam.tallerweb1.modelo.DatosLogin;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;

@Controller
public class ControladorUsuario {

	private ServicioObjeto servicioObjeto;
	private ServicioUsuario servicioUsuario;
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	private ServicioPokemon servicioPokemon;
	private ServicioUsuarioObjeto servicioUsuarioObjeto;
	private ServicioLogin servicioLogin;
	private ServicioBatalla servicioBatalla;
	private ServicioUsuarioAtaquePokemon servicioUsuarioAtaquePokemon;

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario,
			ServicioUsuarioPokemon servicioUsuarioPokemon, ServicioPokemon servicioPokemon,
			ServicioUsuarioObjeto servicioUsuarioObjeto, ServicioLogin servicioLogin, ServicioBatalla servicioBatalla,
			ServicioUsuarioAtaquePokemon servicioUsuarioAtaquePokemon) {
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
		this.servicioPokemon = servicioPokemon;
		this.servicioUsuarioObjeto = servicioUsuarioObjeto;
		this.servicioLogin = servicioLogin;
		this.servicioBatalla = servicioBatalla;
		this.servicioUsuarioAtaquePokemon = servicioUsuarioAtaquePokemon;
	}

	@RequestMapping("lista-objetos")
	public ModelAndView ObtenerObjetos(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap modelo = new ModelMap();
		List<Objeto> objetos = this.servicioObjeto.listarObjetos();
		modelo.put("objetos", objetos);
		return new ModelAndView("lista-objetos", modelo);
	}

	@RequestMapping("elegir-equipo")
	public ModelAndView elegirEquipo(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| request.getSession().getAttribute("principiante") != null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("listaPokemon",
				this.servicioUsuario.obtenerListaDePokemons((Long) request.getSession().getAttribute("id")));
		model.put("listaUsuarioObjetos",
				this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("elegir-equipo", model);
	}

	@RequestMapping("comprar-objetos")
	public ModelAndView comprarObjetos(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| request.getSession().getAttribute("principiante") != null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("puntosUsuario",
				this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")).getPuntos());
		model.put("listaUsuarioObjetos",
				this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("comprar-objetos", model);
	}

	@RequestMapping(path = "comprar-objetos", method = RequestMethod.POST)
	public ModelAndView comprarObjetos(@RequestParam List<Integer> cantidad, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null
				|| request.getSession().getAttribute("principiante") != null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		try {
			this.servicioUsuario.comprarObjetos((Long) request.getSession().getAttribute("id"), cantidad);
		} catch (PuntosInsuficientesException e) {
			model.put("error", e.getMessage());
			model.put("puntosUsuario",
					this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")).getPuntos());
			model.put("listaUsuarioObjetos", this.servicioUsuarioObjeto
					.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("comprar-objetos", model);
		}
		return new ModelAndView("redirect:/elegir-equipo");
	}

	@RequestMapping(path = "guardar-equipo", method = RequestMethod.POST)
	public ModelAndView guardarPokemon(@RequestParam(required = false, name = "pokemonsLista") String[] pokemonsTraidos,
			@RequestParam(required = false, name = "objetosLista") String[] objetosTraidos,
			HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("id");
		List<UsuarioPokemon> lista = this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
		List<Pokemon> pokemons = servicioUsuarioPokemon.buscarPokemons(lista);
		if (pokemonsTraidos == null || pokemonsTraidos.length != 3) {
			model.put("error", "Debe seleccionar 3 pokemons");
			model.put("listaPokemon",
					this.servicioUsuario.obtenerListaDePokemons((Long) request.getSession().getAttribute("id")));
			model.put("listaObjetos", this.servicioUsuarioObjeto
					.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("elegir-equipo", model);
		}
		if (objetosTraidos != null) {
			List<UsuarioObjeto> listaO = this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(id);
			List<Objeto> objetos = this.servicioUsuarioObjeto.buscarObjeto(listaO);
			objetos = this.servicioObjeto.buscarObjetoPorGrupo(objetosTraidos);
			model.put("objetos", objetos);
		}
		pokemons = this.servicioPokemon.buscarPorGrupo(pokemonsTraidos);
		model.put("equipo", pokemons);
		return new ModelAndView("ver-equipos", model);

	}

	@RequestMapping("/datos-de-usuario")
	@ResponseBody
	public ModelAndView datosDeUsuario(@RequestParam(required = false) Boolean ajaxRequest,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap model = new ModelMap();
		model.put("datosLogin", new DatosLogin());
		model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
		if (ajaxRequest == null || !ajaxRequest) {
			model.put("contenido", "datos-de-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/datos-de-usuario", model);
		}
	}

	@RequestMapping("/historial-de-batallas")
	@ResponseBody
	public ModelAndView historialDeBatallas(@RequestParam(required = false) Boolean ajaxRequest,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		List<Batalla> batallas = this.servicioBatalla
				.obtenerBatallasUsuario((Long) request.getSession().getAttribute("id"));
		List<PokemonBatalla> pokemonsBatalla = new ArrayList<>();
		for (Batalla batalla : batallas) {
			this.servicioBatalla.obtenerPokemonsBatalla(batalla);
		}
		model.put("batallas", batallas);
		model.put("pokemonsBatalla", pokemonsBatalla);
		if (ajaxRequest == null || !ajaxRequest) {
			model.put("contenido", "historial-de-batallas");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/historial-de-batallas", model);
		}
	}

	@RequestMapping("/lista-pokemons-usuario")
	@ResponseBody
	public ModelAndView listaPokemonsUsuario(@RequestParam(required = false) Boolean ajaxRequest,
			HttpServletRequest request, @RequestParam(required = false) List<Long> pokemonsLista) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		//////////////////////////////////////////////////////////////////////////

		model.put("lista", this.servicioUsuarioPokemon
				.obtenerListaDeUsuarioPokemon((Long) request.getSession().getAttribute("id")));

		///////////////////////////////////////////////////////////////////////
		if (ajaxRequest == null || !ajaxRequest) {
			model.put("contenido", "lista-pokemons-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/lista-pokemons-usuario", model);
		}
	}

	@RequestMapping("/pokemon-usuario/{idPokemon}")
	@ResponseBody
	public ModelAndView verPokemonUsuario(@PathVariable Long idPokemon,
			@RequestParam(required = false) Boolean ajaxRequest, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		try {
			Pokemon pokemon = this.servicioUsuarioPokemon.buscarPokemon(idPokemon,
					(Long) request.getSession().getAttribute("id"));
			model.put("pokemon", pokemon);
			model.put("puntosUsuario",
					this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")).getPuntos());
			model.put("ataquesPokemon", this.servicioUsuarioAtaquePokemon
					.obtenerLista((Long) request.getSession().getAttribute("id"), pokemon.getId()));
		} catch (PokemonNoObtenidoException ex) {
			model.put("error", ex.getMessage());
		}
		if (ajaxRequest == null || !ajaxRequest) {
			model.put("contenido", "pokemon-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/pokemon-usuario", model);
		}
	}

	@RequestMapping(path = "/desbloquear-ataque", method = RequestMethod.POST)
	@ResponseBody
	public String desbloquearAtaque(@RequestParam Long idUAP, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return null;
		}
		try {
			this.servicioUsuarioAtaquePokemon.desbloquear(idUAP);
			return "exito";
		} catch (PuntosInsuficientesException ex) {
			return ex.getMessage();
		}
	}

	@RequestMapping(path = "/activar-ataque", method = RequestMethod.POST)
	@ResponseBody
	public String activarAtaque(@RequestParam Long idUAP, @RequestParam Long idPokemon, @RequestParam String accion,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return null;
		}
		try {
			this.servicioUsuarioAtaquePokemon.activarDesactivar(idUAP, (Long) request.getSession().getAttribute("id"),
					idPokemon, accion);
			return "exito";
		} catch (LimiteDeAtaquesException ex) {
			return ex.getMessage();
		}
	}

	@RequestMapping(path = "/cambiar-usuario", method = RequestMethod.POST)
	public ModelAndView cambiarUsuario(@ModelAttribute DatosLogin datosLogin, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("contenido", "datos-de-usuario");
		try {
			this.servicioLogin.cambiarUsuario(datosLogin, (Long) request.getSession().getAttribute("id"));
		} catch (UsuarioExistenteException | CampoVacioException ex) {
			model.put("error", ex.getMessage());
			model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Usuario Actualizado");
		model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("perfil-de-usuario", model);
	}

	@RequestMapping(path = "/cambiar-mail", method = RequestMethod.POST)
	public ModelAndView cambiarMail(@ModelAttribute DatosLogin datosLogin, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("contenido", "datos-de-usuario");
		try {
			this.servicioLogin.cambiarMail(datosLogin, (Long) request.getSession().getAttribute("id"));
		} catch (UsuarioExistenteException | FormatoDeEmailIncorrecto | CampoVacioException ex) {
			model.put("error", ex.getMessage());
			model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Mail Actualizado");
		model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("perfil-de-usuario", model);
	}

	@RequestMapping(path = "/cambiar-contrasenia", method = RequestMethod.POST)
	public ModelAndView cambiarContrasenia(@ModelAttribute DatosLogin datosLogin, HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelMap model = new ModelMap();
		model.put("contenido", "datos-de-usuario");
		try {
			this.servicioLogin.cambiarContrasenia(datosLogin, (Long) request.getSession().getAttribute("id"));
		} catch (ContraseniaCorta | ContraseniaIncompatible | CampoVacioException ex) {
			model.put("error", ex.getMessage());
			model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Contraseña Actualizada");
		model.put("usuario", this.servicioUsuario.buscar((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("perfil-de-usuario", model);
	}

	@RequestMapping(path = "/cambiar-foto-perfil", method = RequestMethod.POST)
	@ResponseBody
	public Boolean cambiarFotoPerfil(@RequestParam MultipartFile fotoPerfil, HttpServletRequest request) {
		try {
			this.servicioLogin.cambiarFotoPerfil(fotoPerfil, (Long) request.getSession().getAttribute("id"));
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}