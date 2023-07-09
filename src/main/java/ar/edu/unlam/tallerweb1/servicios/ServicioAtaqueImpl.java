package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.NombreExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAtaque;

@Service("servicioAtaque")
@Transactional
public class ServicioAtaqueImpl implements ServicioAtaque {

	private RepositorioAtaque repositorioAtaque;

	@Autowired
	public ServicioAtaqueImpl(RepositorioAtaque repositorioAtaque){
		this.repositorioAtaque = repositorioAtaque;
	}
	
	@Override
	public List<Ataque> obtenerTodos() {
		return this.repositorioAtaque.obtenerTodos();
	}

	@Override
	public Ataque buscar(Long id) {
		return this.repositorioAtaque.buscar(id);
	}

	@Override
	public void guardar(Ataque datosAtaque) throws NombreExistenteException, CampoVacioException 
	{ 
		if (datosAtaque.getNombre().isBlank()) {
			throw new CampoVacioException ("El campo Nombre esta vacio");
		}
		if (datosAtaque.getPotencia()==null) {
			throw new CampoVacioException ("El campo Potencia esta vacio");
		}
		if (datosAtaque.getPrecataque()==null) {
			throw new CampoVacioException ("El campo Precision esta vacio");
		}
		if (datosAtaque.getPp()==null) {
			throw new CampoVacioException ("El campo Pp esta vacio");
		}
		if (datosAtaque.getEfecto()==null) {
			throw new CampoVacioException ("El campo Efecto esta vacio");
		}
		
		if (this.repositorioAtaque.buscar(datosAtaque.getNombre())!=null) {
			throw new NombreExistenteException("Ataque existente");
		}
		
		this.repositorioAtaque.guardar(datosAtaque);
		
	}

	@Override
	public void borrar(Long id) {
		this.repositorioAtaque.borrar(id);
	}

	@Override
	public void modificar(Ataque ataque) {
		this.repositorioAtaque.modificar(ataque);
		
	}

}
