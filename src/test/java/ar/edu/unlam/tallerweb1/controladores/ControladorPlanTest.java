package ar.edu.unlam.tallerweb1.controladores;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.servicios.ServicioPlan;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPlan;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ControladorPlanTest {

	private ControladorPlan controlador;
	private ServicioUsuario servicioUsuarioMock;
	private ServicioUsuarioPlan servicioUsuarioPlanMock;
	private ServicioPlan servicioPlanMock;
	private HttpServletRequest requestMock;

	@Before
	public void setUp() {
		
		servicioUsuarioMock = Mockito.mock(ServicioUsuario.class);
		servicioUsuarioPlanMock = Mockito.mock(ServicioUsuarioPlan.class);
		servicioPlanMock = Mockito.mock(ServicioPlan.class);

		
		requestMock = Mockito.mock(HttpServletRequest.class);

		
		controlador = new ControladorPlan(servicioPlanMock, servicioUsuarioMock, servicioUsuarioPlanMock, null);

	}

	@Test
	public void testSiElUsuarioNoInicioSesionRedirigeALogin() {
		
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
		Mockito.when(sessionMock.getAttribute("usuario")).thenReturn(null);

		
		ModelAndView modelAndView = controlador.planes(requestMock);

	
		Assert.assertEquals("redirect:/login", modelAndView.getViewName());
	}

	@Test
	public void testCuandoElUsuarioSeLogueaDirigeAPlanes() {
		
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
		Mockito.when(sessionMock.getAttribute("usuario")).thenReturn("usuario");

		
		Long idUsuario = 1L;
		Usuario usuarioMock = new Usuario();
		Mockito.when(servicioUsuarioMock.buscar(idUsuario)).thenReturn(usuarioMock);

	
		UsuarioPlan usuarioPlanMock = new UsuarioPlan();
		Plan planMock = new Plan();
		planMock.setNombre("Nombre del plan");
		usuarioPlanMock.setPlan(planMock);
		Mockito.when(servicioUsuarioPlanMock.buscarPlanPorUsuario(idUsuario)).thenReturn(usuarioPlanMock);

		
		List<Plan> planesMock = new ArrayList<>();
		planesMock.add(planMock);
		Mockito.when(servicioPlanMock.obtenerPlanes()).thenReturn(planesMock);

	
		ModelAndView modelAndView = controlador.planes(requestMock);

		Assert.assertEquals("planes", modelAndView.getViewName());
	}
}