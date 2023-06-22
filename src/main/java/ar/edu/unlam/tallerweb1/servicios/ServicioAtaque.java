package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;

public interface ServicioAtaque {
	
	List<Ataque> obtenerTodos();

	Ataque buscar(Long id);

	void guardar(Ataque datosAtaque) throws NombreExistenteException;

	void borrar(Long id);

	void modificar(Ataque ataque);

}
