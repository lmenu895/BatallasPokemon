package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.edu.unlam.tallerweb1.servicios.ServicioAtaque;

@Controller 
public class ControladorAtaque {

	private ServicioAtaque servicioAtaque;

	@Autowired
	    public ControladorAtaque(ServicioAtaque servicioAtaque){
	        this.servicioAtaque = servicioAtaque;
	    }
}