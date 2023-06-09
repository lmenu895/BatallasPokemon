package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;

public class ControladorInicialesTest {
	
	ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
	ServicioUsuarioPokemon servicioUsuarioPokemon = mock(ServicioUsuarioPokemon.class);
	
	@Test
	public void queCheckeeQueSoloTengaUnPokemon() {
		ModelAndView mav;
		List <Pokemon> lista = new ArrayList<Pokemon>();
		mav = new ModelAndView("iniciales");
		if(lista.size() > 0) {
			mav = new ModelAndView("redirect:/home");
		}	
		assertEquals(mav.getViewName(), "iniciales");
	}
	
	@Test
	public void queSeLeAgregueElPokemonUnaVezQueLoElije() {
		Long id = (long)1;
		Usuario usuario = new Usuario();
		usuario.setId(id);
		Pokemon pokemon = new Pokemon();
		pokemon.setId(id);
		this.servicioUsuarioPokemon.guardarUsuarioPokemon(new UsuarioPokemon(usuario, pokemon));
		assertThat(this.servicioUsuario.obtenerListaDePokemons(id).size() == 1);
	}
	

}
