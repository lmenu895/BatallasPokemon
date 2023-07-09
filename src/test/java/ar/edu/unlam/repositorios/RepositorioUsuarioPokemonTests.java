package ar.edu.unlam.repositorios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.tallerweb1.controladores.ControladorLogin;
import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;
import junit.framework.Assert;

public class RepositorioUsuarioPokemonTests {
    private ServicioUsuarioPokemon serviciousuariopokemon;
    private RepositorioUsuarioPokemon repoUsuarioPokemon;
    private HttpServletRequest request;
    private HttpSession sesion;

    @Before
    public void init() {
        serviciousuariopokemon = mock(ServicioUsuarioPokemon.class);
        repoUsuarioPokemon= mock(RepositorioUsuarioPokemon.class);
        sesion = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
    }

	@Test
	public void buscarUsuarioPokemon() {
		Long idUsuario = (long) 5;
		Long idPokemon = (long) 1;
		
		UsuarioPokemon usuariopokemonbuscado= repoUsuarioPokemon.buscarUsuarioPokemon(idUsuario, idPokemon);
		assertNotNull(usuariopokemonbuscado);
		
		

	}

}
