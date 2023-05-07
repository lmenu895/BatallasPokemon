package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;

public interface ServicioAtaque {

	void guardarAtaque(Ataque datosAtaque) throws CampoVacioException;

}
