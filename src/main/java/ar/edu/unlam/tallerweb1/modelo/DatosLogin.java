package ar.edu.unlam.tallerweb1.modelo;

import org.springframework.web.multipart.MultipartFile;

public class DatosLogin {
	
	private String usuario;
    private String email;
    private String password;
    private String oldPassword;
    private MultipartFile fotoPerfil;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public MultipartFile getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(MultipartFile fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
}
