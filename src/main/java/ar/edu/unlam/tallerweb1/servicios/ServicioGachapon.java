package ar.edu.unlam.tallerweb1.servicios;



import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;



public interface ServicioGachapon {

	Pokemon tiradaGachapon(Integer monedas);

	

}