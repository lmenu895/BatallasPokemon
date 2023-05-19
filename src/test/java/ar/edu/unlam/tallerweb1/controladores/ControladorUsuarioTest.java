package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioPokemon;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuarioPokemon;

public class ControladorUsuarioTest {

	ServicioUsuario servicioUsuario = mock(ServicioUsuario.class);
	ServicioUsuarioPokemon servicioUsuarioPokemon = mock(ServicioUsuarioPokemon.class);
	ServicioPokemon servicioPokemon = mock(ServicioPokemon.class);

	
	
	@Test
	public void queTraigaLaListaDePokemons() {
		Long id = (long)1;
		givenUsuarioPokemonYUsuarioPokemon();
		
		List<Pokemon> pokemons = servicioUsuario.obtenerListaDePokemons(id);
		assertThat(pokemons.size() == 3);
	}
	
	@Test
	public void queTraigaLaListaDeUsuarioPokemon() {
		Long id = (long)1;
		givenUsuarioPokemonYUsuarioPokemon();
		
		
		List <UsuarioPokemon> lista = this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
		assertThat(lista.size() == 3);
	}
	
	@Test
	public void queLaListaDeUsuarioPokemonSoloPaseATenerLosPokemons() {
		Long id = (long)1;
		givenUsuarioPokemonYUsuarioPokemon();
		List <UsuarioPokemon> lista = this.servicioUsuarioPokemon.obtenerListaDeUsuarioPokemon(id);
		List <Pokemon> pokemons = servicioUsuarioPokemon.buscarPokemon(lista);
		Integer pokemonsInList = 0;
		for(Pokemon pokemon : pokemons){
			pokemonsInList++;
		}
		assertThat(pokemonsInList == 3);
	}
	
	@Test
	public void queTengaQueSeleccionarTresPokemonsObligatoriamente() {
		String[] pokemonsTraidos = new String[3];
		pokemonsTraidos[0] = "Charmander";
		pokemonsTraidos[1] = "Squirtle";
		pokemonsTraidos[2] = "Glaceon";
		List<Pokemon> pokemons;
		ModelAndView mav = calcularLengthDeLaLista(pokemonsTraidos);
		assertEquals(mav.getViewName(), ("ver-equipos"));
	}
	
	public ModelAndView calcularLengthDeLaLista(String[] pokemonsTraidos) {
		ModelMap model = new ModelMap();
		List<Pokemon> pokemons;
		if(pokemonsTraidos.length == 3) {
			pokemons = this.servicioPokemon.buscarPokemonPorGrupo(pokemonsTraidos);
			model.put("equipo", pokemons);
			return new ModelAndView("ver-equipos", model);
		}
		return null;
	}

	public void givenUsuarioPokemonYUsuarioPokemon() {
		Long id = (long)1;
		Long id2 = (long)2;
		Long id3 = (long)3;
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		Pokemon pokemon = new Pokemon();
		Pokemon pokemon2 = new Pokemon();
		Pokemon pokemon3 = new Pokemon();
		pokemon.setId(id);
		pokemon3.setId(id2);
		pokemon2.setId(id3);
		
		UsuarioPokemon usuarioPokemon = new UsuarioPokemon(usuario, pokemon);
		usuarioPokemon.setId(id);
		UsuarioPokemon usuarioPokemon2 = new UsuarioPokemon(usuario, pokemon2);
		usuarioPokemon.setId(id2);
		UsuarioPokemon usuarioPokemon3 = new UsuarioPokemon(usuario, pokemon3);
		usuarioPokemon.setId(id3);
	}

}
