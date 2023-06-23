package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;

public interface ServicioUsuarioPlan {

	UsuarioPlan buscarPlanPorUsuario(Long idUsuario);

	void asignarPlanAUsuario(Usuario u1, Plan p1);
	
}
