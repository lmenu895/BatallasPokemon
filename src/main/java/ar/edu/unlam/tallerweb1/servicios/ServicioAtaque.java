package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;

public interface ServicioAtaque {
	
	List<Ataque> obtenerTodosLosAtaques();

	Ataque buscarAtaque(Long id);

	void guardarAtaque(Ataque datosAtaque) throws NombreExistenteException;

}
