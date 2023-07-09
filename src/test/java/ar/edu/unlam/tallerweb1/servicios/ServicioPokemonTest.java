package ar.edu.unlam.tallerweb1.servicios;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.DatosPokemon;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.RarezaPokemon;
import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

public class ServicioPokemonTest {

	private RepositorioPokemon repositorioPokemon = mock(RepositorioPokemon.class);
	private ServletContext servletContext = mock(ServletContext.class);
	private MultipartFile multipartFile = mock(MultipartFile.class);
	private ServicioPokemonImpl servicioPokemon = new ServicioPokemonImpl(repositorioPokemon, servletContext);

	@Test(expected = NombreExistenteException.class)
	public void siAlGuardarPokemonElNombreYaExisteFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {
		DatosPokemon datosPokemon = new DatosPokemon();
		datosPokemon.setNombre("Pikachu");
		datosPokemon.setTipo(TipoPokemon.ELECTRICO);
		datosPokemon.setRareza(RarezaPokemon.NORMAL);
		datosPokemon.setVida(1.0);
		datosPokemon.setVelocidad(1.0);
		datosPokemon.setImagenFrente(multipartFile);
		datosPokemon.setImagenDorso(multipartFile);

		when(servletContext.getRealPath(anyString())).thenReturn("");
		when(repositorioPokemon.buscar(anyString())).thenReturn(new Pokemon());

		servicioPokemon.guardar(datosPokemon);
	}

	@Test(expected = SpriteNoIngresadoException.class)
	public void siAlGuardarPokemonNoIngresoElSpriteFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {

		DatosPokemon datosPokemon = new DatosPokemon();
		datosPokemon.setNombre("Pikachu");
		datosPokemon.setImagenFrente(multipartFile);
		datosPokemon.setImagenDorso(multipartFile);

		when(datosPokemon.getImagenFrente().isEmpty()).thenReturn(true);
		when(datosPokemon.getImagenDorso().isEmpty()).thenReturn(true);

		servicioPokemon.guardar(datosPokemon);
	}

	@Test(expected = CampoVacioException.class)
	public void siQuieroGuardarConUnCampoVacioFalla()
			throws IOException, NombreExistenteException, SpriteNoIngresadoException, CampoVacioException {
		DatosPokemon datosPokemon = new DatosPokemon();
		datosPokemon.setNombre("");
		datosPokemon.setImagenFrente(multipartFile);
		datosPokemon.setImagenDorso(multipartFile);

		servicioPokemon.guardar(datosPokemon);
	}
}
