package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ServicioAgregarAtaques {

	void agregar(Long idUsuario);
	
}
