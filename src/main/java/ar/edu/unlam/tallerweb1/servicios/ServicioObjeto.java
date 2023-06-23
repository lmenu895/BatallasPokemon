package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Objeto;

@Service

public interface ServicioObjeto {

	List<Objeto> listarObjetos();

	List<Objeto> buscarObjetoPorGrupo(List<Long> objetosLista);

}
