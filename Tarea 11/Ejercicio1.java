import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Ejercicio1 {

    // Carnet USAC: exactamente 9 digitos numericos, sin guiones ni espacios
    public static boolean validarCarnet(String carnet) {
        Pattern patron = Pattern.compile("^\\d{9}$");
        Matcher matcher = patron.matcher(carnet);
        return matcher.matches();
    }

    // Correo USAC: parte local no puede iniciar con punto o guion bajo, dominio obligatorio @usac.edu.gt
    public static boolean validarCorreo(String correo) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9._]*@usac\\.edu\\.gt$");
        Matcher matcher = patron.matcher(correo);
        return matcher.matches();
    }

    // Telefono guatemalteco: 8 digitos, inicia con 3, 4, 5 o 6, guion central opcional
    public static boolean validarTelefono(String telefono) {
        Pattern patron = Pattern.compile("^[3456]\\d{3}-?\\d{4}$");
        Matcher matcher = patron.matcher(telefono);
        return matcher.matches();
    }

    public static void main(String[] args) {
        // Pruebas Metodo 1 - Carnet universitario
        System.out.println("=== Carnet universitario ===");
        System.out.println("202300123 -> " + (validarCarnet("202300123") ? "Valido" : "Invalido"));   // Valido
        System.out.println("202512345 -> " + (validarCarnet("202512345") ? "Valido" : "Invalido"));   // Valido
        System.out.println("20230012  -> " + (validarCarnet("20230012")  ? "Valido" : "Invalido"));   // Invalido
        System.out.println("2023ABC12 -> " + (validarCarnet("2023ABC12") ? "Valido" : "Invalido"));   // Invalido

        // Pruebas Metodo 2 - Correo institucional USAC
        System.out.println("\n=== Correo institucional USAC ===");
        System.out.println("juan.perez@usac.edu.gt -> " + (validarCorreo("juan.perez@usac.edu.gt") ? "Valido" : "Invalido"));  // Valido
        System.out.println("carla_001@usac.edu.gt  -> " + (validarCorreo("carla_001@usac.edu.gt")  ? "Valido" : "Invalido"));  // Valido
        System.out.println("juan@gmail.com         -> " + (validarCorreo("juan@gmail.com")          ? "Valido" : "Invalido"));  // Invalido
        System.out.println(".juan@usac.edu.gt      -> " + (validarCorreo(".juan@usac.edu.gt")       ? "Valido" : "Invalido"));  // Invalido

        // Pruebas Metodo 3 - Telefono guatemalteco
        System.out.println("\n=== Telefono guatemalteco ===");
        System.out.println("5555-1234 -> " + (validarTelefono("5555-1234") ? "Valido" : "Invalido")); // Valido
        System.out.println("30001234  -> " + (validarTelefono("30001234")  ? "Valido" : "Invalido")); // Valido
        System.out.println("1234-5678 -> " + (validarTelefono("1234-5678") ? "Valido" : "Invalido")); // Invalido
        System.out.println("555-1234  -> " + (validarTelefono("555-1234")  ? "Valido" : "Invalido")); // Invalido
    }
}
