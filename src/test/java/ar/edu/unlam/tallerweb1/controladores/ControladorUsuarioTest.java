package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;


public class ControladorUsuarioTest {
	
	private ServicioUsuarioPokemon servicioUsuarioPokemon;
	
	@Before
    public void init() {
		servicioUsuarioPokemon = mock(ServicioUsuarioPokemon.class);;
    }
	
	@Test
	public void queMeTraigaLosPokemonsDelUsuario () {
			servicioUsuarioPokemon = mock(ServicioUsuarioPokemon.class);
			Long id = (long) 1;
			List <UsuarioPokemon> lista = servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
			assertThat(lista != null);			
	}
	
	@Test
	public void queLosPokemonUsuarioSeanPokemons() {
		
	}

	
	@Test
	public void queLosEquiposSeanDe3PokemonsSiempre() {
		
	}
	
}
