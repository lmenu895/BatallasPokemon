package ar.edu.unlam.tallerweb1.exceptions;

public class PermisosInsuficientesException extends Exception {

	public PermisosInsuficientesException() {

		super("no cuentas con los permisos suficients para realizar esta accion");

	}


}