package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.util.Random;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SpriteNoIngresadoException;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;


@Service
@Transactional
public class ServicioGachaponImpl implements ServicioGachapon {

		private RepositorioPokemon repositorioPokemon;

		@Autowired
		public ServicioGachaponImpl(RepositorioPokemon repositorioPokemon) {
			this.repositorioPokemon = repositorioPokemon;
		}
		

	@Override
	public Pokemon tiradaGachapon(Integer monedas) {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(101);
		int comun= 0;
		int raro= 1;
		int epico=2;
		Pokemon pokemon;
		
		switch (monedas) {
		case 100: if(numeroAleatorio<=90) {
			List<Pokemon> pokemonesFiltrados= this.repositorioPokemon.obtenerPokemonsPorRareza(comun);
			pokemon= pokemonesFiltrados.get(random.nextInt(pokemonesFiltrados.size()));
			return pokemon;
		}	 List<Pokemon> pokemonesFiltrados= this.repositorioPokemon.obtenerPokemonsPorRareza(raro);
			pokemon= pokemonesFiltrados.get(random.nextInt(pokemonesFiltrados.size()));
			return pokemon;
						
		case 500: if(numeroAleatorio<=60) {
			List<Pokemon> pokemonesFiltrados2= this.repositorioPokemon.obtenerPokemonsPorRareza(comun);
			pokemon= pokemonesFiltrados2.get(random.nextInt(pokemonesFiltrados2.size()));
			return pokemon;
		}	 if(numeroAleatorio>60 && numeroAleatorio<95) { 
			List<Pokemon> pokemonesFiltrados2= this.repositorioPokemon.obtenerPokemonsPorRareza(raro);
			pokemon= pokemonesFiltrados2.get(random.nextInt(pokemonesFiltrados2.size()));
			return pokemon;
		} List<Pokemon> pokemonesFiltrados2= this.repositorioPokemon.obtenerPokemonsPorRareza(epico);
		pokemon= pokemonesFiltrados2.get(random.nextInt(pokemonesFiltrados2.size()));
		return pokemon;
			
		case 1000:	if(numeroAleatorio<=30) {
			List<Pokemon> pokemonesFiltrados3= this.repositorioPokemon.obtenerPokemonsPorRareza(comun);
			pokemon= pokemonesFiltrados3.get(random.nextInt(pokemonesFiltrados3.size()));
			return pokemon;
		}	 if(numeroAleatorio>30 && numeroAleatorio<80) { 
			List<Pokemon> pokemonesFiltrados3= this.repositorioPokemon.obtenerPokemonsPorRareza(raro);
			pokemon= pokemonesFiltrados3.get(random.nextInt(pokemonesFiltrados3.size()));
			return pokemon;
		} List<Pokemon> pokemonesFiltrados3= this.repositorioPokemon.obtenerPokemonsPorRareza(epico);
		pokemon= pokemonesFiltrados3.get(random.nextInt(pokemonesFiltrados3.size()));
		return pokemon;
			
		}
		return null;
	}
}
