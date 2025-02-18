package cliper.apiBoostly.daos;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "usuarios") // El nombre coincide con el de la tabla en la base de datos
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa AUTO_INCREMENT de MySQL
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre_usuario", length = 255, nullable = false)
    private String nombreUsuario;

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

    @Column(name = "rol_usuario", length = 255)
    private String rolUsuario = "Usuario";

    @Column(name = "google_usuario")
    private Boolean googleUsuario;

    @Column(name = "token_recuperacion", length = 255)
    private String tokenRecuperacion;

    @Column(name = "token_expiracion")
    private Timestamp tokenExpiracion;

    // Constructor vacío requerido por JPA
    public Usuarios() {}

    // Constructor con parámetros
    public Usuarios(String nombreUsuario, String apellidosUsuario, String mailUsuario, Date fechaNacimientoUsuario,
                    String nicknameUsuario, String contrasenyaUsuario, Date fechaAltaUsuario, String descripcionUsuario,
                    String dniUsuario, String telefonoUsuario, byte[] imgUsuario, String rolUsuario, Boolean googleUsuario,
                    String tokenRecuperacion, Timestamp tokenExpiracion) {
        this.nombreUsuario = nombreUsuario;
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
        this.rolUsuario = rolUsuario;
        this.googleUsuario = googleUsuario;
        this.tokenRecuperacion = tokenRecuperacion;
        this.tokenExpiracion = tokenExpiracion;
    }

    // Getters y Setters
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

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
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

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", mailUsuario='" + mailUsuario + '\'' +
                ", fechaNacimientoUsuario=" + fechaNacimientoUsuario +
                ", nicknameUsuario='" + nicknameUsuario + '\'' +
                ", contrasenyaUsuario='" + contrasenyaUsuario + '\'' +
                ", fechaAltaUsuario=" + fechaAltaUsuario +
                ", descripcionUsuario='" + descripcionUsuario + '\'' +
                ", dniUsuario='" + dniUsuario + '\'' +
                ", telefonoUsuario='" + telefonoUsuario + '\'' +
                ", rolUsuario='" + rolUsuario + '\'' +
                ", googleUsuario=" + googleUsuario +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                ", tokenExpiracion=" + tokenExpiracion +
                '}';
    }
}
