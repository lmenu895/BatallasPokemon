package ar.edu.unlam.tallerweb1.exceptions;

public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}