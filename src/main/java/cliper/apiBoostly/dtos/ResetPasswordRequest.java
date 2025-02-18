package cliper.apiBoostly.dtos;

public class ResetPasswordRequest {

    private String mailUsuario;
    private String nuevoPassword;
    private String tokenRecuperacion;

    // Constructor vacío
    public ResetPasswordRequest() {}

    // Constructor con parámetros
    public ResetPasswordRequest(String mailUsuario, String nuevoPassword, String tokenRecuperacion) {
        this.mailUsuario = mailUsuario;
        this.nuevoPassword = nuevoPassword;
        this.tokenRecuperacion = tokenRecuperacion;
    }

    // Getters y Setters
    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "mailUsuario='" + mailUsuario + '\'' +
                ", nuevoPassword='" + nuevoPassword + '\'' +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                '}';
    }
}
