package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;

public interface ServicioPokemon {

	Boolean guardarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso) throws IOException;

	Pokemon buscarPokemon(Long id);
	
	Pokemon buscarPokemonPorNombre(String nombre);

}
