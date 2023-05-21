package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;

public interface ServicioUsuarioObjeto {

	List<UsuarioObjeto> obtenerListaDeUsuarioObjeto(Long idUsuario);

	List<Objeto> buscarObjeto(List<UsuarioObjeto> listaO);
	
}
