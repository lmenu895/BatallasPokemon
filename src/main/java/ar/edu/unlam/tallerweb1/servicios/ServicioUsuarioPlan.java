package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptions.PlanInexistenteException;
import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;

public interface ServicioUsuarioPlan {

	UsuarioPlan buscarPlanPorUsuario(Long idUsuario);

	void asignarPlan(Long idPlan, Long idUsuario);
	
	void agregarTiradas(UsuarioPlan up);

	void agregar();

	void verificarPlanBasico(Long idUsuario) throws PlanInexistenteException;
}
