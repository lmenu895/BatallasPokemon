package ar.edu.unlam.tallerweb1.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioBatalla;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaCorta;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaIncompatible;
import ar.edu.unlam.tallerweb1.exceptions.FormatoDeEmailIncorrecto;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Batalla;
import ar.edu.unlam.tallerweb1.modelo.DatosLogin;
import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.PokemonBatalla;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
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

	@Autowired
	public ControladorUsuario(ServicioObjeto servicioObjeto, ServicioUsuario servicioUsuario,
			ServicioUsuarioPokemon servicioUsuarioPokemon, ServicioPokemon servicioPokemon,
			ServicioUsuarioObjeto servicioUsuarioObjeto, ServicioLogin servicioLogin, ServicioBatalla servicioBatalla) {
		this.servicioObjeto = servicioObjeto;
		this.servicioUsuario = servicioUsuario;
		this.servicioUsuarioPokemon = servicioUsuarioPokemon;
		this.servicioPokemon = servicioPokemon;
		this.servicioUsuarioObjeto = servicioUsuarioObjeto;
		this.servicioLogin = servicioLogin;
		this.servicioBatalla = servicioBatalla;
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
		model.put("listaObjetos", this.servicioObjeto.listarObjetos() );
		model.put("listaUsuarioObjetos",
				this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
		return new ModelAndView("comprar-objetos", model);
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
		List<Pokemon> pokemons = servicioUsuarioPokemon.buscarPokemon(lista);
		if (pokemonsTraidos == null || pokemonsTraidos.length != 3) {
			model.put("error", "Debe seleccionar 3 pokemons");
			model.put("listaPokemon",
					this.servicioUsuario.obtenerListaDePokemons((Long) request.getSession().getAttribute("id")));
			model.put("listaObjetos",
					this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("elegir-equipo", model);
		}
		if (objetosTraidos != null) {
			List<UsuarioObjeto> listaO = this.servicioUsuarioObjeto.obtenerListaDeUsuarioObjeto(id);
			List<Objeto> objetos = this.servicioUsuarioObjeto.buscarObjeto(listaO);
			objetos = this.servicioObjeto.buscarObjetoPorGrupo(objetosTraidos);
			model.put("objetos", objetos);
		}
		pokemons = this.servicioPokemon.buscarPokemonPorGrupo(pokemonsTraidos);
		model.put("equipo", pokemons);
		return new ModelAndView("ver-equipos", model);

	}

	@RequestMapping("/datos-de-usuario")
	public ModelAndView datosDeUsuario(HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		model.put("datosLogin", new DatosLogin());
		model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "datos-de-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/datos-de-usuario", model);
		}
	}

	@RequestMapping("/historial-de-batallas")
	public ModelAndView historialDeBatallas(HttpServletRequest request) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		List<Batalla> batallas = this.servicioBatalla
				.obtenerBatallasUsuario((Long) request.getSession().getAttribute("id"));
		List<PokemonBatalla> pokemonsBatalla = new ArrayList<>();
		for (Batalla batalla : batallas) {
			this.servicioBatalla.obtenerPokemonsBatalla(batalla);
		}
		model.put("batallas", batallas);
		model.put("pokemonsBatalla", pokemonsBatalla);
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "historial-de-batallas");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/historial-de-batallas", model);
		}
	}

	@RequestMapping("/lista-pokemons-usuario")
	public ModelAndView listaPokemonsUsuario(HttpServletRequest request, @RequestParam(required = false) List<Long> pokemonsLista) {
		if (request.getSession().getAttribute("usuario") == null) {
			return new ModelAndView("redirect:/login");
		}
		String tipoDeRequest = request.getHeader("X-Requested-With");
		ModelMap model = new ModelMap();
		//////////////////////////////////////////////////////////////////////////
		
		model.put("lista", this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon((Long)request.getSession().getAttribute("id")));
		
		///////////////////////////////////////////////////////////////////////
		if (tipoDeRequest == null || !tipoDeRequest.equals("XMLHttpRequest")) {
			model.put("contenido", "lista-pokemons-usuario");
			return new ModelAndView("perfil-de-usuario", model);
		} else {
			return new ModelAndView("partial/lista-pokemons-usuario",model);
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
			model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Usuario Actualizado");
		model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
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
		} catch (UsuarioExistenteException | FormatoDeEmailIncorrecto ex) {
			model.put("error", ex.getMessage());
			model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Mail Actualizado");
		model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
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
			model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
			return new ModelAndView("perfil-de-usuario", model);
		}
		model.put("success", "Contrase�a Actualizada");
		model.put("usuario", this.servicioUsuario.buscarUsuario((Long) request.getSession().getAttribute("id")));
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