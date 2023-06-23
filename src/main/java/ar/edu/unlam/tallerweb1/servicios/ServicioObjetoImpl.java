package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioObjeto;

@Service
@Transactional
public class ServicioObjetoImpl implements ServicioObjeto {

	private RepositorioObjeto repositorioObjeto;

	@Autowired
	public ServicioObjetoImpl(RepositorioObjeto repositorioObjeto) {
		this.repositorioObjeto = repositorioObjeto;
	}

	@Override
	public List<Objeto> listarObjetos() {
		List<Objeto> objetos = this.repositorioObjeto.listarObjetos();
		return objetos;
	}

	@Override
	public List<Objeto> buscarObjetoPorGrupo(List<Long> objetosTraidos) {
		List<Objeto> objetos = new ArrayList<Objeto>();

		for (Long idObjeto : objetosTraidos) {
			objetos.add(this.repositorioObjeto.buscar(idObjeto));
		}
		return objetos;	
	}

}