package cl.duoc.UsuarioMicroServicio.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un usuario")
public class Usuario {

    @Id
    @Column(name = "ID_USUARIO")
    @Schema(description = "id del usuario")
    private String idUsuario;

    @Column(name = "NOMBRE")
    @Schema(description = "nombre del usuario")
    private String nombre;

    @Column(name = "APELLIDO")
    @Schema(description = "apellido del usuario")
    private String apellido;

    @Column(name = "EMAIL")
    @Schema(description = "email del usuario")
    private String email;

    @Column(name = "CONTRASEÑA")
    @Schema(description = "contraseña del usuario")
    private String contrasena;

    @Column(name = "FECHA_NACIMIENTO")
    @Schema(description = "fecha de nacimiento del usuario")
    private String fecha_nacimiento;

    @Column(name = "TIPO_USUARIO")
    @Schema(description = "tipo de usuario")
    private String tipo_usuario;

    @Column(name = "TELEFONO")
    @Schema(description = "telefono del usuario")
    private String telefono;

    @Column(name = "DIRECCION")
    @Schema(description = "direccion del usuario")
    private String direccion;

    @Column(name = "PUNTOS")
    private Integer puntos = 0;

    @Column(name = "IDFIREBASE", nullable = true)
    private String idfirebase;

    // Imagen almacenada como binario (BLOB)
    @Lob
    @Column(name = "imagen", nullable = true)
    private byte[] imagen;

}
