package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;

import java.util.ArrayList;
import java.util.Random;

@Service
@Transactional
public class ServicioGachaponImpl implements ServicioGachapon {
	
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


}
