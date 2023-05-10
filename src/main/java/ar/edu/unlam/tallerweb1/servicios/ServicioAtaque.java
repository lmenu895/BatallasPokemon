package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;

public interface ServicioAtaque {
	
	List<Ataque> obtenerTodosLosAtaques();

	Ataque buscarAtaque(Long id);

	void guardarAtaque(Ataque datosAtaque) throws CampoVacioException;

	void borrarAtaque(Long id);

	void modificarAtaque(Ataque ataque);

}
