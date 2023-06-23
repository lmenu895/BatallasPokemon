package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface RepositorioPlan {
	
	public void darDeBajaPlan(Long id);

	public List<Plan> listaDePlanes();

	public Plan consultarPlan(Long id);
	

}
