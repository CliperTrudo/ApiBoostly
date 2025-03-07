package cliper.apiBoostly.dtos;

import java.time.Instant;

public class TokenContraseñaDto {
    private String email;
    private String tokenRecuperacion;
    private String nuevaContrasena;
    private Instant fechaExpiracion;

    /**
     * Métodos Getters y Setters:
     * - getEmail() y setEmail(): Obtienen y establecen el correo electrónico del usuario.
     * - getTokenRecuperacion() y setTokenRecuperacion(): Obtienen y establecen el token de recuperación.
     * - getNuevaContrasena() y setNuevaContrasena(): Obtienen y establecen la nueva contraseña.
     * - getFechaExpiracion() y setFechaExpiracion(): Obtienen y establecen la fecha de expiración del token de recuperación.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public Instant getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Instant fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
