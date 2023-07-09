package ar.edu.unlam.tallerweb1.controladores;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import ar.edu.unlam.tallerweb1.modelo.TipoPokemon;

public class ControladorPokemonTest {

	@Test
	public void siElNombreDelPokemonYaExisteLaCreacionFalla() {
		String nombre = "Charizard";
		TipoPokemon tipo = TipoPokemon.FUEGO;
		Double vida = 2000.0;
		
		fail("not implemented");

	}

}
