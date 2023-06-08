package ar.edu.unlam.tallerweb1.servicios;


import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
@Service
public interface ServicioGachapon {
	
	public Pokemon tiradaGachapon(Integer monedas);

}

