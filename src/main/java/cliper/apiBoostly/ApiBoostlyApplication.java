package cliper.apiBoostly;

// Importación de clases necesarias para el arranque de la aplicación Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación que configura y arranca la aplicación Spring Boot.
 * Esta clase contiene el punto de entrada a la aplicación.
 * @author Sergio Alfonseca
 */
@SpringBootApplication
public class ApiBoostlyApplication {

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * Este método es el punto de entrada del programa.
     * 
     * @param args[] es un arreglo con los parámetros que el reciba por consola.
     * @return void
     * @author Sergio Alfonseca
     */
    public static void main(String[] args) {
        // Arranca la aplicación Spring Boot
        SpringApplication.run(ApiBoostlyApplication.class, args);
    }

}
