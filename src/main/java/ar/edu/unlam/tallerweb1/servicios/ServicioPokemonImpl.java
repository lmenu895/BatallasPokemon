package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	public ServicioPokemonImpl(RepositorioPokemon repositorioPokemon, ServicioAtaquePokemon servicioAtaquePokemon, ServicioAtaque servicioAtaque, ServletContext servletContext){
		this.repositorioPokemon = repositorioPokemon;
		this.servicioAtaquePokemon = servicioAtaquePokemon;
		this.servicioAtaque = servicioAtaque;
		this.servletContext = servletContext;
	}

	@Override
	public Boolean guardarPokemon(Pokemon pokemon, String[] ataques, MultipartFile frente, MultipartFile dorso) throws IOException {
		if(validarPokemon(pokemon, frente, dorso)) {
			this.repositorioPokemon.guardarPokemon(pokemon);
			for (String ataque : ataques) {
				this.servicioAtaquePokemon.guardarAtaques(new AtaquePokemon(this.servicioAtaque.buscarAtaque(Long.parseLong(ataque)), pokemon));
			}
			return true;
		}
		else
			return false;
	}

	
	private Boolean validarPokemon(Pokemon pokemon, MultipartFile frente, MultipartFile dorso) throws IOException {
		if(this.repositorioPokemon.buscarPokemonPorNombre(pokemon.getNombre()) == null) {
			String frenteFileName = frente.getOriginalFilename();
			String dorsoFileName = dorso.getOriginalFilename();
			String uploadDir = servletContext.getRealPath(".") + "/images/sprites/" + pokemon.getNombre();
			System.out.println(uploadDir);
			Path uploadPath = Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try(InputStream inputStream = frente.getInputStream()){
				Path filePath = uploadPath.resolve(frenteFileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				pokemon.setImagenFrente(frenteFileName);
			}
			catch(IOException ex){
				throw new IOException("No se pudo guardar el archivo: " + frenteFileName);
			}
			try(InputStream inputStream = dorso.getInputStream()){
				Path filePath = uploadPath.resolve(dorsoFileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				pokemon.setImagenDorso(dorsoFileName);
			}
			catch(IOException ex){
				throw new IOException("No se pudo guardar el archivo: " + dorsoFileName);
			}
			return true;
		}
		else
			return false;
	}

	@Override
	public Pokemon buscarPokemon(Long id) {
		return this.repositorioPokemon.buscarPokemon(id);
	}

	@Override
	public Pokemon buscarPokemonPorNombre(String nombre) {
		return this.repositorioPokemon.buscarPokemonPorNombre(nombre);
	}
	
}
