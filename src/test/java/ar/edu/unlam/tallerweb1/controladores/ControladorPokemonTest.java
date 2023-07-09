package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;

public class ControladorPokemonTest {

	@Test
	public void siElNombreDelPokemonYaExisteFalla() {
		Pokemon pokemon = new Pokemon().withNombre("Pikachu");
		
		fail("not implemented");

	}

}
