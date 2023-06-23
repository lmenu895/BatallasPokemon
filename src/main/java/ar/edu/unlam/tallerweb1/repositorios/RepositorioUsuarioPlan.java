package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;

public interface RepositorioUsuarioPlan {

	UsuarioPlan buscarPorUsuario(Long idUsuario);

	void asignarPlanAUsuario(Usuario usuario, Plan plan);

	void darDeBajaElPlan(Usuario usuario);

	void modificar(UsuarioPlan usuarioPlan);

	
	
}
