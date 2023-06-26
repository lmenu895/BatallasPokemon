package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;

public interface RepositorioUsuarioPlan {

	UsuarioPlan buscarPorUsuario(Long idUsuario);

	void asignarPlanAUsuario(Usuario usuario, Plan plan);

	void guardar(UsuarioPlan usuarioPlan);
	
	void darDeBajaElPlan(UsuarioPlan up);

	void modificar(UsuarioPlan usuarioPlan);

	List<UsuarioPlan> traerTodos();

}
