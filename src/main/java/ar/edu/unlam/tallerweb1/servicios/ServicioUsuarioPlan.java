package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptions.SaldoInsuficienteException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioSinBilleteraException;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;

public interface ServicioUsuarioPlan {

	UsuarioPlan buscarPlanPorUsuario(Long idUsuario);

	void asignarPlan(Long idPlan, Long idUsuario) throws UsuarioSinBilleteraException, SaldoInsuficienteException;
	
	void agregarTiradas(Usuario usuario, Double precio);

	void agregar();
}
