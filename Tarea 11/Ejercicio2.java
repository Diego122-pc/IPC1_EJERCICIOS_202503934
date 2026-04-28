import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Ejercicio2 {

    private static final String TEXTO =
        "UNIVERSIDAD DE SAN CARLOS DE GUATEMALA\n" +
        "Facultad de Ingeniería — Boletín Informativo 2024-04-25\n" +
        "\n" +
        "Actividades programadas:\n" +
        "    - Conferencia el 2024-05-01 a las 10:30 en el Edificio T3\n" +
        "    - Examen parcial el 2024-05-15 a las 08:00 en el Edificio S11\n" +
        "    - Defensa de proyecto el 2024-06-10 a las 14:30 en el Edificio T7\n" +
        "\n" +
        "Contactos del departamento:\n" +
        "    - Coordinador:  coord.ipc1@ingenieria.usac.edu.gt   Tel: 2418-8000\n" +
        "    - Auxiliar 1:   aux01_ipc1@ingenieria.usac.edu.gt   Tel: 5555-1234\n" +
        "    - Auxiliar 2:   aux02.ipc1@gmail.com                Tel: 4321-9876";

    // Busca todas las fechas YYYY-MM-DD e imprime sus partes con grupos de captura
    public static void extraerFechas() {
        Pattern patron = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
        Matcher matcher = patron.matcher(TEXTO);
        while (matcher.find()) {
            System.out.println("Año: " + matcher.group(1) +
                               " | Mes: " + matcher.group(2) +
                               " | Día: " + matcher.group(3));
        }
    }

    // Busca todos los correos electronicos presentes en el texto e imprime uno por linea
    public static void extraerCorreos() {
        Pattern patron = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = patron.matcher(TEXTO);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    // Reemplaza correos con [CORREO] y telefonos XXXX-XXXX con [TEL] en ese orden
    public static String censurarDatos(String texto) {
        Pattern patronCorreo = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcherCorreo = patronCorreo.matcher(texto);
        StringBuffer sb = new StringBuffer();
        while (matcherCorreo.find()) {
            matcherCorreo.appendReplacement(sb, "[CORREO]");
        }
        matcherCorreo.appendTail(sb);

        // El patron de telefono aplica solo al formato XXXX-XXXX (con guion)
        Pattern patronTel = Pattern.compile("\\d{4}-\\d{4}");
        Matcher matcherTel = patronTel.matcher(sb.toString());
        StringBuffer sb2 = new StringBuffer();
        while (matcherTel.find()) {
            matcherTel.appendReplacement(sb2, "[TEL]");
        }
        matcherTel.appendTail(sb2);

        return sb2.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Método 1: Extraer fechas ===");
        extraerFechas();

        System.out.println("\n=== Método 2: Extraer correos electrónicos ===");
        extraerCorreos();

        System.out.println("\n=== Método 3: Censurar datos sensibles ===");
        // Caso de prueba del enunciado
        String entrada1 = "Llama a 5555-1234 o escribe a juan@usac.edu.gt para más info.";
        System.out.println("Entrada: " + entrada1);
        System.out.println("Salida:  " + censurarDatos(entrada1));

        // Caso con multiples datos
        String entrada2 = "Contacto: aux01_ipc1@ingenieria.usac.edu.gt Tel: 4321-9876";
        System.out.println("\nEntrada: " + entrada2);
        System.out.println("Salida:  " + censurarDatos(entrada2));

        // Censurando el texto completo
        System.out.println("\n--- Texto completo censurado ---");
        System.out.println(censurarDatos(TEXTO));
    }
}
