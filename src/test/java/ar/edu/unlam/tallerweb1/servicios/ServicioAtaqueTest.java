package ar.edu.unlam.tallerweb1.servicios;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAtaque;

public class ServicioAtaqueTest {

	private RepositorioAtaque repositorioAtaque = mock(RepositorioAtaque.class);
	private ServicioAtaqueImpl servicioAtaque = new ServicioAtaqueImpl(repositorioAtaque);
	
	@Test (expected = CampoVacioException.class) 
	public void queNoSePuedanDejarUnCampoVacio() throws NombreExistenteException, CampoVacioException {
		
		Ataque ataque = new Ataque(); 
		ataque.setNombre("");
		ataque.setTipo(TipoPokemon.AGUA);
		ataque.setPotencia(90.0);
		ataque.setPrecataque(100.0);
		ataque.setPp(10.0);
		ataque.setEfecto(false);
		
		servicioAtaque.guardar(ataque);
	}
	
	@Test (expected = NombreExistenteException.class) 
	public void queNoSePuedanCrearAtaquesIguales() throws NombreExistenteException, CampoVacioException {
		
		Ataque ataque = new Ataque(); 
		ataque.setNombre("Surf");
		ataque.setTipo(TipoPokemon.AGUA);
		ataque.setPotencia(90.0);
		ataque.setPrecataque(100.0);
		ataque.setPp(10.0);
		ataque.setEfecto(false);
		
		when(repositorioAtaque.buscar(ataque.getNombre())).thenReturn(new Ataque());
		servicioAtaque.guardar(ataque);
	}
	
}
