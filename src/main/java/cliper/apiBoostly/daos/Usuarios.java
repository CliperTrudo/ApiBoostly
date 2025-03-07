package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Clase que representa un usuario en el sistema.
 * Esta clase está mapeada a la tabla 'usuarios' en la base de datos.
 * @author Sergio Alfonseca
 */
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre_usuario", length = 255, nullable = false)
    private String nombreUsuario;

    @ManyToOne
    @JoinColumn(name = "rol_id_rol", nullable = false)
    private Roles rol;  // Relación con la clase 'Roles'

    @Column(name = "apellidos_usuarios", length = 255, nullable = false)
    private String apellidosUsuario;

    @Column(name = "mail_usuario", length = 255, nullable = false)
    private String mailUsuario;

    @Column(name = "fecha_nacimiento_usuario")
    private Date fechaNacimientoUsuario;

    @Column(name = "nickname_usuario", length = 255)
    private String nicknameUsuario;

    @Column(name = "contrasenya_usuario", length = 255, nullable = false)
    private String contrasenyaUsuario;

    @Column(name = "fecha_alta_usuario")
    private Date fechaAltaUsuario;

    @Column(name = "descripcion_usuario", length = 255)
    private String descripcionUsuario;

    @Column(name = "dni_usuario", length = 255)
    private String dniUsuario;

    @Column(name = "telefono_usuario", length = 255)
    private String telefonoUsuario;

    @Lob
    @Column(name = "img_usuario")
    private byte[] imgUsuario;

    @Column(name = "google_usuario")
    private Boolean googleUsuario;

    @Column(name = "token_recuperacion", length = 255)
    private String tokenRecuperacion;

    @Column(name = "token_expiracion")
    private Timestamp tokenExpiracion;

    // Constructor vacío requerido por JPA
    public Usuarios() {}

    // Constructor con parámetros
    public Usuarios(String nombreUsuario, Roles rol, String apellidosUsuario, String mailUsuario,
                    Date fechaNacimientoUsuario, String nicknameUsuario, String contrasenyaUsuario,
                    Date fechaAltaUsuario, String descripcionUsuario, String dniUsuario, String telefonoUsuario,
                    byte[] imgUsuario, Boolean googleUsuario, String tokenRecuperacion, Timestamp tokenExpiracion) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.apellidosUsuario = apellidosUsuario;
        this.mailUsuario = mailUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.nicknameUsuario = nicknameUsuario;
        this.contrasenyaUsuario = contrasenyaUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.descripcionUsuario = descripcionUsuario;
        this.dniUsuario = dniUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.imgUsuario = imgUsuario;
        this.googleUsuario = googleUsuario;
        this.tokenRecuperacion = tokenRecuperacion;
        this.tokenExpiracion = tokenExpiracion;
    }

    /**
     * Métodos Getters y Setters:
     * - getId() y setId(): Obtienen y establecen el ID del usuario.
     * - getNombreUsuario() y setNombreUsuario(): Obtienen y establecen el nombre del usuario.
     * - getRol() y setRol(): Obtienen y establecen el rol del usuario.
     * - getApellidosUsuario() y setApellidosUsuario(): Obtienen y establecen los apellidos del usuario.
     * - getMailUsuario() y setMailUsuario(): Obtienen y establecen el correo electrónico del usuario.
     * - getFechaNacimientoUsuario() y setFechaNacimientoUsuario(): Obtienen y establecen la fecha de nacimiento del usuario.
     * - getNicknameUsuario() y setNicknameUsuario(): Obtienen y establecen el apodo del usuario.
     * - getContrasenyaUsuario() y setContrasenyaUsuario(): Obtienen y establecen la contraseña del usuario.
     * - getFechaAltaUsuario() y setFechaAltaUsuario(): Obtienen y establecen la fecha de alta del usuario.
     * - getDescripcionUsuario() y setDescripcionUsuario(): Obtienen y establecen la descripción del usuario.
     * - getDniUsuario() y setDniUsuario(): Obtienen y establecen el DNI del usuario.
     * - getTelefonoUsuario() y setTelefonoUsuario(): Obtienen y establecen el número de teléfono del usuario.
     * - getImgUsuario() y setImgUsuario(): Obtienen y establecen la imagen del usuario.
     * - getGoogleUsuario() y setGoogleUsuario(): Obtienen y establecen si el usuario tiene cuenta de Google.
     * - getTokenRecuperacion() y setTokenRecuperacion(): Obtienen y establecen el token de recuperación de contraseña.
     * - getTokenExpiracion() y setTokenExpiracion(): Obtienen y establecen la fecha de expiración del token de recuperación.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public Date getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(Date fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public String getNicknameUsuario() {
        return nicknameUsuario;
    }

    public void setNicknameUsuario(String nicknameUsuario) {
        this.nicknameUsuario = nicknameUsuario;
    }

    public String getContrasenyaUsuario() {
        return contrasenyaUsuario;
    }

    public void setContrasenyaUsuario(String contrasenyaUsuario) {
        this.contrasenyaUsuario = contrasenyaUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public String getDescripcionUsuario() {
        return descripcionUsuario;
    }

    public void setDescripcionUsuario(String descripcionUsuario) {
        this.descripcionUsuario = descripcionUsuario;
    }

    public String getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(String dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public byte[] getImgUsuario() {
        return imgUsuario;
    }

    public void setImgUsuario(byte[] imgUsuario) {
        this.imgUsuario = imgUsuario;
    }

    public Boolean getGoogleUsuario() {
        return googleUsuario;
    }

    public void setGoogleUsuario(Boolean googleUsuario) {
        this.googleUsuario = googleUsuario;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public Timestamp getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(Timestamp tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String del usuario, incluyendo su ID, nombre, apellidos, correo, rol, etc.
     */
    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", rol=" + rol +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", mailUsuario='" + mailUsuario + '\'' +
                ", fechaNacimientoUsuario=" + fechaNacimientoUsuario +
                ", nicknameUsuario='" + nicknameUsuario + '\'' +
                ", contrasenyaUsuario='" + contrasenyaUsuario + '\'' +
                ", fechaAltaUsuario=" + fechaAltaUsuario +
                ", descripcionUsuario='" + descripcionUsuario + '\'' +
                ", dniUsuario='" + dniUsuario + '\'' +
                ", telefonoUsuario='" + telefonoUsuario + '\'' +
                ", googleUsuario=" + googleUsuario +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                ", tokenExpiracion=" + tokenExpiracion +
                '}';
    }
}
