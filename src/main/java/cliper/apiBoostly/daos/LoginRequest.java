package cliper.apiBoostly.daos;

public class LoginRequest {
    private String mail;
    private String contrasenya;

    // Getters y Setters
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

	public LoginRequest(String mail, String contrasenya) {
		super();
		this.mail = mail;
		this.contrasenya = contrasenya;
	}

	public LoginRequest() {
		super();
	}

	@Override
	public String toString() {
		return "LoginRequest [mailClub=" + mail + ", contrasenyaClub=" + contrasenya + "]";
	}
    
	
}
