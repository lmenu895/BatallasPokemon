package ar.edu.unlam.tallerweb1.servicios;

import java.util.Random;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
}
