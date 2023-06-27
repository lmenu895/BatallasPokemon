package ar.edu.unlam.tallerweb1.repositorios;

import java.time.LocalDateTime;

import ar.edu.unlam.tallerweb1.modelo.Pago;

public interface RepositorioPago {

	Pago buscar(Long idPago, LocalDateTime localDateTime);

	void guardar(Pago pago);

}
