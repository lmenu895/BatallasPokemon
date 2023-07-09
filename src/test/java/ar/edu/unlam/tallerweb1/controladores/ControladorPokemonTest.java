package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioAtaque;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;

public class ControladorPokemonTest {

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpSession session = mock(HttpSession.class);
	private ServicioPokemon servicioPokemon = mock(ServicioPokemon.class);
	private ServicioAtaque servicioAtaque = mock(ServicioAtaque.class);
	private ControladorPokemon controladorPokemon = new ControladorPokemon(servicioPokemon, servicioAtaque);

	@Before
	public void init() {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuario")).thenReturn("Admin");
		when(session.getAttribute("esAdmin")).thenReturn(true);
	}

	@Test
	public void siAlCrearPokemonElNombreYaExisteFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {

		doThrow(NombreExistenteException.class).when(servicioPokemon).guardar(any(DatosPokemon.class));

		ModelAndView mav = controladorPokemon.crearPokemon(new DatosPokemon(), request);
		assertThat(mav.getViewName().equals("crear-pokemon"));
	}

	@Test
	public void siAlCrearPokemonNoIngresoElSpriteFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {

		doThrow(SpriteNoIngresadoException.class).when(servicioPokemon).guardar(any(DatosPokemon.class));

		ModelAndView mav = controladorPokemon.crearPokemon(new DatosPokemon(), request);
		assertThat(mav.getViewName().equals("crear-pokemon"));
	}

	@Test
	public void siAlCrearDejoUnCampoVacioFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {
		
		doThrow(CampoVacioException.class).when(servicioPokemon).guardar(any(DatosPokemon.class));

		ModelAndView mav = controladorPokemon.crearPokemon(new DatosPokemon(), request);
		assertThat(mav.getViewName().equals("crear-pokemon"));
	}

	@Test
	public void siAlCrearPokemonPasaLasValidacionesSeCreaElPokemon() {
		
		ModelAndView mav = controladorPokemon.crearPokemon(new DatosPokemon(), request);
		assertThat(mav.getViewName().equals("redirect:/lista-pokemons"));
	}
}
