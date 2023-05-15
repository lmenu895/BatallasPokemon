package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
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
	public void guardarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso)
			throws IOException, NombreExistenteException {
		validarPokemon(pokemon, frente, dorso);
		this.repositorioPokemon.guardarPokemon(pokemon);
		for (String ataque : ataques) {
			this.servicioAtaquePokemon.guardarAtaque(
					new AtaquePokemon(this.servicioAtaque.buscarAtaque(Long.parseLong(ataque)), pokemon));
		}
	}

	@Override
	public void modificarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso) {
		Boolean flag;
		this.repositorioPokemon.modificarPokemon(pokemon);
		this.servicioAtaquePokemon.borrarAtaquesDeUnPokemon(pokemon.getId());
		for (String ataque : ataques) {
			this.servicioAtaquePokemon.guardarAtaque(new AtaquePokemon(this.servicioAtaque.buscarAtaque(Long.parseLong(ataque)), pokemon));
		}
	}

	private void validarPokemon(Pokemon pokemon, MultipartFile frente, MultipartFile dorso)
			throws IOException, NombreExistenteException {
		if (this.repositorioPokemon.buscarPokemonPorNombre(pokemon.getNombre()) == null) {
			/*String frenteFileName = frente.getOriginalFilename();
			String dorsoFileName = dorso.getOriginalFilename();
			String uploadDir = servletContext.getRealPath(".") + "/images/sprites/" + pokemon.getNombre();
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = frente.getInputStream()) {
				Path filePath = uploadPath.resolve(frenteFileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				pokemon.setImagenFrente(frenteFileName);
			} catch (IOException ex) {
				throw new IOException("No se pudo guardar el archivo: " + frenteFileName);
			}
			try (InputStream inputStream = dorso.getInputStream()) {
				Path filePath = uploadPath.resolve(dorsoFileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				pokemon.setImagenDorso(dorsoFileName);
			} catch (IOException ex) {
				throw new IOException("No se pudo guardar el archivo: " + dorsoFileName);
			}*/
		} else
			throw new NombreExistenteException("El nombre del pokemon ya existe");
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		Pokemon pokemon = this.repositorioPokemon.buscarPokemon(id);
		List<Ataque> ataques = new ArrayList<>();
		for (AtaquePokemon aprendido : this.servicioAtaquePokemon.obtenerListaDeAtaquePokemon(id)) {
			ataques.add(this.servicioAtaque.buscarAtaque(aprendido.getAtaque().getId()));
		}
		pokemon.setAtaques(ataques);
		return pokemon;
	}

	@Override
	public Pokemon buscarPokemonPorNombre(String nombre) {
		return this.repositorioPokemon.buscarPokemonPorNombre(nombre);
	}

	@Override
	public List<Pokemon> obtenerTodosLosPokemons() {
		return this.repositorioPokemon.obtenerTodosLosPokemons();
	}

	@Override
	public void borrarPokemon(Long id) {
		this.repositorioPokemon.borrarPokemon(id);
	}

}
