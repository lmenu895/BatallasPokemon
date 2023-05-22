package ar.edu.unlam.tallerweb1.servicios;

<<<<<<< HEAD
import java.util.Random;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

=======
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

import java.util.ArrayList;
import java.util.Random;
>>>>>>> 0df91bd (gacha terminado sin front2)

@Service
@Transactional
public class ServicioGachaponImpl implements ServicioGachapon {
<<<<<<< HEAD

		private RepositorioPokemon repositorioPokemon;

		@Autowired
		public ServicioGachaponImpl(RepositorioPokemon repositorioPokemon) {
			this.repositorioPokemon = repositorioPokemon;
		}
		
	@Override
	public Pokemon tiradaGachapon(Integer monedas) {
		Pokemon pokemon;
		
		switch (monedas) {
			case 100: return pokemon = tirada(90, 100);
				
			case 500: return pokemon = tirada(60, 95);
				
			case 1000: return pokemon = tirada(30, 80);
				
			case 10000:	return pokemon = tirada(0,30);
		}

		return null;
	}
	
	private Pokemon tirada(Integer normal, Integer raroP) {
		Pokemon pokemon;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(101);
		int comun= 0;
		int raro= 1;
		int epico=2;
		
		if(numeroAleatorio <= normal) {
			List<Pokemon> pokemonesFiltrados4= this.repositorioPokemon.obtenerPokemonsPorRareza(comun);
			pokemon= pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
			return pokemon;
			}	 
		if(numeroAleatorio > normal && numeroAleatorio < raroP) { 
			List<Pokemon> pokemonesFiltrados4= this.repositorioPokemon.obtenerPokemonsPorRareza(raro);
			pokemon= pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
			return pokemon;
		} 
		List<Pokemon> pokemonesFiltrados4= this.repositorioPokemon.obtenerPokemonsPorRareza(epico);
		pokemon= pokemonesFiltrados4.get(random.nextInt(pokemonesFiltrados4.size()));
		return pokemon;
	}
	
=======
	
	 private RepositorioPokemon repositorioPokemon;
	 
	 @Autowired
	 public ServicioGachaponImpl(RepositorioPokemon repositorioPokemon) {
		 this.repositorioPokemon= repositorioPokemon;
	 }

	@Override
	public Pokemon tiradaGachapon(Integer monedas) {
		Random random = new Random();
        int numeroAleatorio = random.nextInt(101);
        ArrayList<Pokemon> pokemones= new ArrayList<Pokemon>();
        Pokemon pokemon;
        int comun= 0;
        int raro= 1;
        int epico= 2;
		switch (monedas) {
		
		case 100: if(numeroAleatorio<=90) {
			pokemones=repositorioPokemon.buscarPorRareza(comun);
			int numeroObtenido= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido);
			return pokemon;
		}
			pokemones= repositorioPokemon.buscarPorRareza(raro);
			int numeroObtenido= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido);
			return pokemon;
				
		case 500: if(numeroAleatorio<=70) {
			pokemones=repositorioPokemon.buscarPorRareza(comun);
			int numeroObtenido2= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido2);
			return pokemon;
		} if(numeroAleatorio>70 && numeroAleatorio<=95) {
			pokemones= repositorioPokemon.buscarPorRareza(raro);
			int numeroObtenido2= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido2);
			return pokemon;
		}	pokemones= repositorioPokemon.buscarPorRareza(epico);
			int numeroObtenido2= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido2);
			return pokemon;
		case 1000: if(numeroAleatorio<=40) {
			pokemones=repositorioPokemon.buscarPorRareza(comun);
			int numeroObtenido3= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido3);
			return pokemon;
		} if(numeroAleatorio>40 && numeroAleatorio<=85) {
			pokemones= repositorioPokemon.buscarPorRareza(raro);
			int numeroObtenido3= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido3);
			return pokemon;
		}	pokemones= repositorioPokemon.buscarPorRareza(epico);
			int numeroObtenido3= random.nextInt(pokemones.size());
			pokemon= pokemones.get(numeroObtenido3);
			return pokemon;
			
		}
		return  null;

	}


>>>>>>> 0df91bd (gacha terminado sin front2)
}
