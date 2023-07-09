package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;
import ar.edu.unlam.tallerweb1.modelo.Ataque;

public interface RepositorioAtaque {

	List<Ataque> obtenerTodos();
	Ataque buscar(Long id);
	void guardar(Ataque datosAtaque);
	void borrar(Long id);
	void modificar(Ataque ataque);
	Ataque buscar(String nombre);

}
