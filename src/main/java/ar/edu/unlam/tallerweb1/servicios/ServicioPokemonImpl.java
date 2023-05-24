package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

@Service("servicioPokemon")
@Transactional
public class ServicioPokemonImpl implements ServicioPokemon {

	private RepositorioPokemon repositorioPokemon;
	private ServicioAtaquePokemon servicioAtaquePokemon;
	private ServicioAtaque servicioAtaque;
	private ServletContext servletContext;

	@Autowired
	public ServicioPokemonImpl(RepositorioPokemon repositorioPokemon, ServicioAtaquePokemon servicioAtaquePokemon,
			ServicioAtaque servicioAtaque, ServletContext servletContext) {
		this.repositorioPokemon = repositorioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioAtaque = servicioAtaque;
		this.servletContext = servletContext;
	}

	@Override
	public void guardarPokemon(Pokemon pokemon, List<Long> ataques, MultipartFile frente, MultipartFile dorso)
			throws IOException, NombreExistenteException, SpriteNoIngresadoException {
		if (frente.isEmpty() | dorso.isEmpty()) {
			throw new SpriteNoIngresadoException("No ha ingresado los dos sprites del pokemon");
		}
		this.validarPokemon(pokemon, frente, dorso, "");
		this.repositorioPokemon.guardarPokemon(pokemon);
		ataques.forEach(x -> this.servicioAtaquePokemon.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon)));
	}

	@Override
	public void modificarPokemon(Pokemon pokemon, List<Long> ataques, MultipartFile frente, MultipartFile dorso,
			String nombreAnterior, List<Long> ataquesAprendidos) throws IOException, NombreExistenteException {
		this.validarPokemon(pokemon, frente, dorso, nombreAnterior);
		Long ataque;
		for (Long aprendido : ataquesAprendidos) {
			ataque = this.verificarAtaqueOlvidado(aprendido, ataques);
			if (ataque == null) {
				this.servicioAtaquePokemon.borrarAtaquePokemon(aprendido, pokemon.getId());
			} else {
				ataques.remove(ataque);
			}
		}
		this.repositorioPokemon.modificarPokemon(pokemon);
		ataques.forEach(x -> this.servicioAtaquePokemon.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon)));

	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		Pokemon pokemon = this.repositorioPokemon.buscarPokemon(id);
		return pokemon;
	}

	@Override
	public Pokemon buscarPokemon(String nombre) {
		return this.repositorioPokemon.buscarPokemon(nombre);
	}

	@Override
	public List<Pokemon> obtenerTodosLosPokemons() {
		return this.repositorioPokemon.obtenerTodosLosPokemons();
	}

	@Override
	public void borrarPokemon(Long id) {
		this.repositorioPokemon.borrarPokemon(id);
	}

	@Override
	public List<Pokemon> buscarPokemonPorGrupo(String[] pokemonsTraidos) {

		List<Pokemon> pokemons = new ArrayList<Pokemon>();

		for (String pokemon : pokemonsTraidos) {
			pokemons.add(this.repositorioPokemon.buscarPokemon(Long.parseLong(pokemon)));
		}
		return pokemons;
	}

	// Funciones privadas para utilizar dentro de la clase

	private Long verificarAtaqueOlvidado(Long aprendido, List<Long> ataques) {
		for (Long ataque : ataques) {
			if (ataque == aprendido) {
				return ataque;
			}
		}
		return null;
	}

	private void validarPokemon(Pokemon pokemon, MultipartFile frente, MultipartFile dorso, String nombreAnterior)
			throws IOException, NombreExistenteException {
		if (nombreAnterior.equals(pokemon.getNombre())
				|| this.repositorioPokemon.buscarPokemon(pokemon.getNombre()) == null) {
			try {
				if (!frente.isEmpty()) {
					this.guardarImagen(frente, pokemon.getNombre());
					pokemon.setImagenFrente(frente.getOriginalFilename());
				}
				if (!dorso.isEmpty()) {
					this.guardarImagen(dorso, pokemon.getNombre());
					pokemon.setImagenDorso(dorso.getOriginalFilename());
				}
			} catch (IOException ex) {
				throw new IOException("No se pudo guardar los archivos");
			}
		} else {
			pokemon.setNombre(nombreAnterior);
			throw new NombreExistenteException("El nombre del pokemon ya existe");
		}
	}

	private void guardarImagen(MultipartFile imagen, String nombrePokemon) throws IOException {
		String fileName = imagen.getOriginalFilename();
		String uploadDir = servletContext.getRealPath("") + "images/sprites/" + nombrePokemon;
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		InputStream inputStream = imagen.getInputStream();
		Path filePath = uploadPath.resolve(fileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public List<Pokemon> crearEquipoCpu() {
		Random random = new Random();
		List<Pokemon> todosLosPokemons = repositorioPokemon.obtenerTodosLosPokemons();
		List<Pokemon> pokemons = new ArrayList<>();
		while(pokemons.size() < 3) {
			int indexPokemon = random.nextInt(todosLosPokemons.size()+1);
			pokemons.add(todosLosPokemons.get(indexPokemon));
		}
		return pokemons;
	}
}
