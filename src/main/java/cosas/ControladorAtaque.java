package cosas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller 
public class ControladorAtaque {

	private ServicioAtaque servicioAtaque;

	@Autowired
	    public ControladorAtaque(ServicioAtaque servicioAtaque){
	        this.servicioAtaque = servicioAtaque;
	    }
}