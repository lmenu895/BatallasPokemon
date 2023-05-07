package ar.edu.unlam.tallerweb1.excepciones;

public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}