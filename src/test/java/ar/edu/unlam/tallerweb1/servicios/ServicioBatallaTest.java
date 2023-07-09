package ar.edu.unlam.tallerweb1.servicios;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.ObjetosInsuficientesException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioBatalla;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioObjeto;

public class ServicioBatallaTest {

	private RepositorioBatalla repositorioBatalla = mock(RepositorioBatalla.class);
	private RepositorioUsuarioObjeto repositorioUsuarioObjeto = mock(RepositorioUsuarioObjeto.class);
	private ServicioBatallaImpl servicioBatalla = new ServicioBatallaImpl(repositorioBatalla, repositorioUsuarioObjeto);

	@Test(expected = PokemonsInsuficientesException.class)
	public void siAlIniciarBatallaHayMasOMenosDe3PokemonsFalla()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {

		List<Long> pokemonsLista = Arrays.asList(new Long[] { 0l });
		
		servicioBatalla.inicioBatalla(pokemonsLista, new ArrayList<>(), 0l);
	}

	@Test(expected = ExcesoDeObjetosException.class)
	public void siAlIniciarBatallaHayMasDeTresObjetosFalla()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {

		List<Long> pokemonsLista = Arrays.asList(new Long[] { 0l, 1l, 2l });
		List<Long> objetosLista = Arrays.asList(new Long[] { 0l, 1l, 2l, 4l });

		servicioBatalla.inicioBatalla(pokemonsLista, objetosLista, 0l);
	}

	@Test(expected = ObjetosInsuficientesException.class)
	public void siAlIniciarBatallaNoTieneElObjetoFalla()
			throws PokemonsInsuficientesException, ExcesoDeObjetosException, ObjetosInsuficientesException {

		List<Long> pokemonsLista = Arrays.asList(new Long[] { 0l, 1l, 2l });
		List<Long> objetosLista = Arrays.asList(new Long[] { 0l, 1l, 2l });
		when(repositorioUsuarioObjeto.buscar(anyLong(), anyLong())).thenReturn(new UsuarioObjeto().withCantidad(0));

		servicioBatalla.inicioBatalla(pokemonsLista, objetosLista, 0l);
	}

}
