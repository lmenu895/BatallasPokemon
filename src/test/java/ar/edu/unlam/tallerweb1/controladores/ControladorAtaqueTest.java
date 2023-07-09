package ar.edu.unlam.tallerweb1.controladores;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioAtaque;

public class ControladorAtaqueTest {
	
	
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpSession session = mock(HttpSession.class);
	private ServicioAtaque servicioAtaque = mock(ServicioAtaque.class);
	private ControladorAtaque controladorAtaque = new ControladorAtaque(servicioAtaque);

	@Test //(expected=NombreExistenteException.class)
	public void queNoSePuedaCrearUnAtaqueDuplicado() throws NombreExistenteException, CampoVacioException {
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuario")).thenReturn("Admin");
		when(session.getAttribute("esAdmin")).thenReturn(true);
		
		Ataque ataque = new Ataque(); 
		ataque.setNombre("Surf");
		ataque.setTipo(TipoPokemon.AGUA);
		ataque.setPotencia(90.0);
		ataque.setPrecataque(100.0);
		ataque.setPp(10.0);
		ataque.setEfecto(false);
		
		doThrow(NombreExistenteException.class).when(servicioAtaque).guardar(ataque);
		ModelAndView mav = controladorAtaque.confirmarRegistro(ataque, request);
		assertThat(mav.getViewName()).isEqualTo("crear-ataque");
		
		
	}
	
	@Test //(expected=NombreExistenteException.class)
	public void queNoSePuedanDejarCamposVacios() throws NombreExistenteException, CampoVacioException {
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuario")).thenReturn("Admin");
		when(session.getAttribute("esAdmin")).thenReturn(true);
		
		Ataque ataque = new Ataque(); 
		ataque.setNombre("Surf");
		ataque.setTipo(TipoPokemon.AGUA);
		ataque.setPotencia(90.0);
		ataque.setPrecataque(100.0);
		ataque.setPp(10.0);
		ataque.setEfecto(false);
		
		doThrow(CampoVacioException.class).when(servicioAtaque).guardar(any());
		ModelAndView mav = controladorAtaque.confirmarRegistro(ataque, request);
		assertThat(mav.getViewName()).isEqualTo("crear-ataque");
		
		
	}
	

}
