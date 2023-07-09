package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.ObjetosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.servicios.ServicioBatalla;
import ar.edu.unlam.tallerweb1.servicios.ServicioObjeto;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;

public class ControladorBatallaTest {

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpSession session = mock(HttpSession.class);
	private ServicioBatalla servicioBatalla = mock(ServicioBatalla.class);
	private ServicioPokemon servicioPokemon = mock(ServicioPokemon.class);
	private ServicioObjeto servicioObjeto = mock(ServicioObjeto.class);
	private ControladorBatalla controladorBatalla = new ControladorBatalla(servicioBatalla, servicioPokemon,
			servicioObjeto);

	@Before
	public void init() {
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void siSeleccionoMenosOMasDe3PokemonsLaBatallaNoInicia()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {

		doThrow(PokemonsInsuficientesException.class).when(servicioBatalla).inicioBatalla(any(), anyList(), anyLong());

		ModelAndView mav = controladorBatalla.iniciarBatalla(request, new ArrayList<>(), new ArrayList<>());
		assertThat(mav.getViewName().equals("elegir-equipo"));
	}

	@Test
	public void siSeleccionoMasDe3ObjetosLaBatallaNoInicia()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {

		doThrow(ExcesoDeObjetosException.class).when(servicioBatalla).inicioBatalla(any(), anyList(), anyLong());

		ModelAndView mav = controladorBatalla.iniciarBatalla(request, new ArrayList<>(), new ArrayList<>());
		assertThat(mav.getViewName().equals("elegir-equipo"));
	}

	@Test
	public void siSeleccionoObjetosQueNoTengoLaBatallaNoInicia()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {
		
		doThrow(ObjetosInsuficientesException.class).when(servicioBatalla).inicioBatalla(any(), anyList(), anyLong());

		ModelAndView mav = controladorBatalla.iniciarBatalla(request, new ArrayList<>(), new ArrayList<>());
		assertThat(mav.getViewName().equals("elegir-equipo"));
	}

	@Test
	public void siPasanTodasLasValidacionesIniciaLaBatalla()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {
		
		ModelAndView mav = controladorBatalla.iniciarBatalla(request, new ArrayList<>(), new ArrayList<>());
		assertThat(mav.getViewName().equals("batalla"));
	}
}
