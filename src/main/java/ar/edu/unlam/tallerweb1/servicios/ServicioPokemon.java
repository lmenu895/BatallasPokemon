package ar.edu.unlam.tallerweb1.servicios;



import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	Boolean guardarPokemon(Pokemon pokemon);

	Pokemon buscarPokemon(Long id);
	
}
