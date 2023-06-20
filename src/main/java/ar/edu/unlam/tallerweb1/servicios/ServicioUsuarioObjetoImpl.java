package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioObjeto;

@Service("servicioUsuarioObjeto")
@Transactional
public class ServicioUsuarioObjetoImpl implements ServicioUsuarioObjeto{

	private RepositorioUsuarioObjeto repositorioUsuarioObjeto;
;
	@Autowired
	public ServicioUsuarioObjetoImpl(RepositorioUsuarioObjeto repositorioUsuarioObjeto) {
		this.repositorioUsuarioObjeto = repositorioUsuarioObjeto;
	}
	@Override
	public List<UsuarioObjeto> obtenerListaDeUsuarioObjeto(Long idUsuario) {
		return this.repositorioUsuarioObjeto.buscarObjeto(idUsuario);
	}
	
	@Override
	public List<Objeto> buscarObjeto(List<UsuarioObjeto> lista) {
		ArrayList<Objeto> objetos = new ArrayList<Objeto>();
		for (UsuarioObjeto objeto : lista) {
			objetos.add(objeto.getObjeto());
		}
		return objetos;
	}
	
}
