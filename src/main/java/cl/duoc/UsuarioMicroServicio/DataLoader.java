package cl.duoc.UsuarioMicroServicio;

import java.util.Locale;
import java.util.Random;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.UsuarioMicroServicio.entity.Usuario;
import cl.duoc.UsuarioMicroServicio.service.UsuarioService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));
    private final Random random = new Random();

    @Autowired
    private UsuarioService usuarioservice;

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < 10; i++) {

            Usuario u = new Usuario();

            u.setIdUsuario(faker.number().digits(8));

            u.setNombre(faker.name().firstName());
            u.setApellido(faker.name().lastName());
            u.setContrasena(faker.internet().password());
            u.setEmail(faker.internet().emailAddress());

            u.setFecha_nacimiento(
                    faker.date().birthday()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(fmt));

            // DIRECCION segura
            String direccion = faker.address().streetName() + " " + faker.number().numberBetween(1, 3000);
            if (direccion.length() > 40) {
                direccion = direccion.substring(0, 40);
            }
            u.setDireccion(direccion);

            u.setTelefono(faker.number().digits(9));
            u.setTipo_usuario("cliente");
            u.setPuntos(0);


            usuarioservice.guardarUsuario(u);
            System.out.println("Usuario registrado: " + u.getIdUsuario());
        }
    }

}
