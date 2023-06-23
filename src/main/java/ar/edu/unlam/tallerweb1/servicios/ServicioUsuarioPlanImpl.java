package ar.edu.unlam.tallerweb1.servicios;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPlan;

@Service("servicioUsuarioPlanImpl")
@Transactional
public class ServicioUsuarioPlanImpl implements ServicioUsuarioPlan{

	private RepositorioUsuarioPlan repositorioUsuarioPlan;
	
	@Autowired
	public ServicioUsuarioPlanImpl(RepositorioUsuarioPlan repositorioUsuarioPlan) {
		this.repositorioUsuarioPlan = repositorioUsuarioPlan;
	}
	
	@Override
	public UsuarioPlan buscarPlanPorUsuario(Long idUsuario) {
		return this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario);
	}

	@Override
	public void asignarPlanAUsuario(Usuario usuario, Plan plan) {
		repositorioUsuarioPlan.asignarPlanAUsuario(usuario,plan);
	}

}
