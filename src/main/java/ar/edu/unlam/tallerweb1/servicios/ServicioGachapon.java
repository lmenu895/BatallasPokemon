package ar.edu.unlam.tallerweb1.servicios;


<<<<<<< HEAD

import ar.edu.unlam.tallerweb1.modelo.Pokemon;



public interface ServicioGachapon {

	Pokemon tiradaGachapon(Integer monedas);

	

}
=======
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
@Service
public interface ServicioGachapon {
	
	public Pokemon tiradaGachapon(Integer monedas, Usuario usuario);


}
>>>>>>> 0df91bd (gacha terminado sin front2)
