package ar.edu.unlam.tallerweb1.exceptions;

public class PermisosInsuficientesException extends Exception {

	public PermisosInsuficientesException() {
		super("No cuentas con los permisos suficientes para realizar esta acción");
	}

}
