package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

@Service("servicioPokemon")
@EnableScheduling
@Transactional
public class ServicioPokemonImpl implements ServicioPokemon {

	@Inject
	private RepositorioPokemon repositorioPokemon;
	@Inject
	private ServicioAtaquePokemon servicioAtaquePokemon;
	@Inject
	private ServicioAtaque servicioAtaque;
	@Inject
	private ServletContext servletContext;

	@Override
	public void guardar(DatosPokemon datosPokemon)
			throws IOException, NombreExistenteException, SpriteNoIngresadoException {
		if (datosPokemon.getImagenFrente().isEmpty() | datosPokemon.getImagenDorso().isEmpty()) {
			throw new SpriteNoIngresadoException("No ha ingresado los dos sprites del pokemon");
		}
		Pokemon pokemon = new Pokemon();
		this.validarPokemon(datosPokemon, pokemon);
		this.repositorioPokemon.guardar(pokemon);
		datosPokemon.getAtaquesDesbloqueados().forEach(x -> this.servicioAtaquePokemon.guardar(new AtaquePokemon()
				.withAtaque(this.servicioAtaque.buscar(x)).withPokemon(pokemon).withBloqueado(false)));
		datosPokemon.getAtaquesBloqueados().forEach(x -> this.servicioAtaquePokemon.guardar(new AtaquePokemon()
				.withAtaque(this.servicioAtaque.buscar(x)).withPokemon(pokemon).withBloqueado(true)));
	}

	@Override
	public void modificar(DatosPokemon datosPokemon, Long idPokemon) throws IOException, NombreExistenteException {
		Pokemon pokemon = this.buscar(idPokemon);
		this.validarPokemon(datosPokemon, pokemon);
		Long ataque;
		List<Long> ataquesDesbloqueados = datosPokemon.getAtaquesDesbloqueados();
		List<Long> ataquesBloqueados = datosPokemon.getAtaquesBloqueados();
		for (Long aprendido : this.servicioAtaquePokemon.obtenetDesbloqueados(idPokemon)) {
			ataque = this.verificarAtaqueOlvidado(aprendido, ataquesDesbloqueados);
			if (ataque == null) {
				this.servicioAtaquePokemon.borrar(aprendido, pokemon.getId());
			} else {
				ataquesDesbloqueados.remove(ataque);
			}
		}
		for (Long aprendido : this.servicioAtaquePokemon.obtenetBloqueados(idPokemon)) {
			ataque = this.verificarAtaqueOlvidado(aprendido, ataquesBloqueados);
			if (ataque == null) {
				this.servicioAtaquePokemon.borrar(aprendido, pokemon.getId());
			} else {
				ataquesBloqueados.remove(ataque);
			}
		}
		this.repositorioPokemon.modificarPokemon(pokemon);
		ataquesDesbloqueados.forEach(x -> this.servicioAtaquePokemon.guardar(new AtaquePokemon()
				.withAtaque(this.servicioAtaque.buscar(x)).withPokemon(pokemon).withBloqueado(false)));
		ataquesBloqueados.forEach(x -> this.servicioAtaquePokemon.guardar(new AtaquePokemon()
				.withAtaque(this.servicioAtaque.buscar(x)).withPokemon(pokemon).withBloqueado(true)));
	}

	@Override
	public Pokemon buscar(Long id) {
		return this.repositorioPokemon.buscar(id);
	}

	@Override
	public Pokemon buscar(String nombre) {
		return this.repositorioPokemon.buscar(nombre);
	}

	@Override
	public List<Pokemon> obtenerTodos() {
		return this.repositorioPokemon.obtenerTodos();
	}

	@Override
	public void borrar(Long id) {
		this.repositorioPokemon.borrar(id);
	}

	@Override
	public List<Pokemon> buscarPorGrupo(String[] pokemonsTraidos) {

		List<Pokemon> pokemons = new ArrayList<Pokemon>();

		for (String pokemon : pokemonsTraidos) {
			pokemons.add(this.repositorioPokemon.buscar(Long.parseLong(pokemon)));
		}
		return pokemons;
	}

	@Override
	public List<Pokemon> crearEquipoCpu(HttpServletRequest request) {
		Long[] idsPokemonsCpu = new Long[3];
		List<Pokemon> pokemons = new ArrayList<>();
		if ((Long[]) request.getSession().getAttribute("idsPokemonsCpu") == null) {
			Random random = new Random();
			List<Pokemon> todosLosPokemons = repositorioPokemon.obtenerTodos();
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
				pokemons.add(this.buscar(idsPokemonsCpu[i]));
			}
			return pokemons;
		}
	}

	// Funciones privadas para utilizar dentro de la clase
	
	
	//@Scheduled(cron = "*/10 * * * * *")
	//@Override
	//public void prueba() {
	//	System.err.println("paso un minuto");
	//}

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
				|| this.repositorioPokemon.buscar(datosPokemon.getNombre()) == null) {
			try {
				if (!datosPokemon.getImagenFrente().isEmpty()) {
					this.guardarImagen(datosPokemon.getImagenFrente(), datosPokemon.getNombre());
					pokemon.withImagenFrente(datosPokemon.getImagenFrente().getOriginalFilename());
				}
				if (!datosPokemon.getImagenDorso().isEmpty()) {
					this.guardarImagen(datosPokemon.getImagenDorso(), datosPokemon.getNombre());
					pokemon.withImagenDorso(datosPokemon.getImagenDorso().getOriginalFilename());
				}
				if (pokemon.getNombre() != null && !pokemon.getNombre().equals(datosPokemon.getNombre())) {
					Path spriteFolder = Paths
							.get(servletContext.getRealPath("") + "images/sprites/" + pokemon.getNombre());
					Files.move(spriteFolder, spriteFolder.resolveSibling(datosPokemon.getNombre()));
				}
				pokemon.withNombre(datosPokemon.getNombre()).withTipo(datosPokemon.getTipo())
						.withRareza(datosPokemon.getRareza()).withVida(datosPokemon.getVida())
						.withVelocidad(datosPokemon.getVelocidad());
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
	public List<Pokemon> obtenerTodosLosComunes() {
		return this.repositorioPokemon.obtenerPorRareza(0);
	}
}
