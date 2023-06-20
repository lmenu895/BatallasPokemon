package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
	public void guardarPokemon(DatosPokemon datosPokemon)
			throws IOException, NombreExistenteException, SpriteNoIngresadoException {
		if (datosPokemon.getImagenFrente().isEmpty() | datosPokemon.getImagenDorso().isEmpty()) {
			throw new SpriteNoIngresadoException("No ha ingresado los dos sprites del pokemon");
		}
		Pokemon pokemon = new Pokemon();
		this.validarPokemon(datosPokemon, pokemon);
		this.repositorioPokemon.guardarPokemon(pokemon);
		datosPokemon.getAtaquesDesbloqueados().forEach(x -> this.servicioAtaquePokemon
				.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon, false)));
		datosPokemon.getAtaquesBloqueados().forEach(x -> this.servicioAtaquePokemon
				.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon, true)));
	}

	@Override
	public void modificarPokemon(DatosPokemon datosPokemon, Long idPokemon)
			throws IOException, NombreExistenteException {
		Pokemon pokemon = this.buscarPokemon(idPokemon);
		this.validarPokemon(datosPokemon, pokemon);
		Long ataque;
		List<Long> ataquesDesbloqueados = datosPokemon.getAtaquesDesbloqueados();
		List<Long> ataquesBloqueados = datosPokemon.getAtaquesBloqueados();
		for (Long aprendido : this.servicioAtaquePokemon.obtenetAtaquesDesbloqueados(idPokemon)) {
			ataque = this.verificarAtaqueOlvidado(aprendido, ataquesDesbloqueados);
			if (ataque == null) {
				this.servicioAtaquePokemon.borrarAtaquePokemon(aprendido, pokemon.getId());
			} else {
				ataquesDesbloqueados.remove(ataque);
			}
		}
		for (Long aprendido : this.servicioAtaquePokemon.obtenetAtaquesBloqueados(idPokemon)) {
			ataque = this.verificarAtaqueOlvidado(aprendido, ataquesBloqueados);
			if (ataque == null) {
				this.servicioAtaquePokemon.borrarAtaquePokemon(aprendido, pokemon.getId());
			} else {
				ataquesBloqueados.remove(ataque);
			}
		}
		this.repositorioPokemon.modificarPokemon(pokemon);
		ataquesDesbloqueados.forEach(x -> this.servicioAtaquePokemon
				.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon, false)));
		ataquesBloqueados.forEach(x -> this.servicioAtaquePokemon
				.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(x), pokemon, true)));
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
	public Pokemon buscarPokemonString(String nombre) {
		return this.repositorioPokemon.buscarPokemon(Long.parseLong(nombre));
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

	@Override
	public List<Pokemon> crearEquipoCpu(HttpServletRequest request) {
		Long[] idsPokemonsCpu = new Long[3];
		List<Pokemon> pokemons = new ArrayList<>();
		if ((Long[]) request.getSession().getAttribute("idsPokemonsCpu") == null) {
			Random random = new Random();
			List<Pokemon> todosLosPokemons = repositorioPokemon.obtenerTodosLosPokemons();
			while (pokemons.size() < 3) {
				int indexPokemon = random.nextInt(todosLosPokemons.size());
				pokemons.add(todosLosPokemons.get(indexPokemon));
				todosLosPokemons.remove(indexPokemon);
			}
			for (Integer i = 0; i < pokemons.size(); i++) {
				idsPokemonsCpu[i] = pokemons.get(i).getId();
			}
			request.getSession().setAttribute("idsPokemonsCpu", idsPokemonsCpu);
			return pokemons;
		} else {
			idsPokemonsCpu = (Long[]) request.getSession().getAttribute("idsPokemonsCpu");
			for (Integer i = 0; i < idsPokemonsCpu.length; i++) {
				pokemons.add(this.buscarPokemon(idsPokemonsCpu[i]));
			}
			return pokemons;
		}
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

	private void validarPokemon(DatosPokemon datosPokemon, Pokemon pokemon)
			throws IOException, NombreExistenteException {
		if (pokemon.getNombre() != null && pokemon.getNombre().equals(datosPokemon.getNombre())
				|| this.repositorioPokemon.buscarPokemon(datosPokemon.getNombre()) == null) {
			try {
				if (!datosPokemon.getImagenFrente().isEmpty()) {
					this.guardarImagen(datosPokemon.getImagenFrente(), datosPokemon.getNombre());
					pokemon.setImagenFrente(datosPokemon.getImagenFrente().getOriginalFilename());
				}
				if (!datosPokemon.getImagenDorso().isEmpty()) {
					this.guardarImagen(datosPokemon.getImagenDorso(), datosPokemon.getNombre());
					pokemon.setImagenDorso(datosPokemon.getImagenDorso().getOriginalFilename());
				}
				if (pokemon.getNombre() != null && !pokemon.getNombre().equals(datosPokemon.getNombre())) {
					Path spriteFolder = Paths.get(servletContext.getRealPath("") + "images/sprites/" + pokemon.getNombre());
					Files.move(spriteFolder, spriteFolder.resolveSibling(datosPokemon.getNombre()));
				}
				pokemon.setNombre(datosPokemon.getNombre());
				pokemon.setTipo(datosPokemon.getTipo());
				pokemon.setRareza(datosPokemon.getRareza());
				pokemon.setVida(datosPokemon.getVida());
				pokemon.setVelocidad(datosPokemon.getVelocidad());
			} catch (IOException ex) {
				throw new IOException("No se pudo guardar los archivos");
			}
		} else {
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
	public List<Pokemon> obtenerTodosLosPokemonsComunes() {
		return this.repositorioPokemon.obtenerPokemonsPorRareza(0);
	}
}
