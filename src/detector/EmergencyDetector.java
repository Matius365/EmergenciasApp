package detector;

import model.EmergencyEvent;
import java.util.Scanner;

public class EmergencyDetector {
    private int umbral; // ejemplo de validación de gravedad

    public EmergencyDetector(int umbral) {
        this.umbral = umbral;
    }
    // establecemos el bloque try-catch para manejar el error
    // en este caso si algun dato esta vacio no manda el aviso
    // y lo considera como falso positivo

    public EmergencyEvent detectEvent() {
        Scanner scanner = new Scanner(System.in);
        try {

        System.out.println("¿Desea activar emergencia? (S/N)");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduce tipo de emergencia: ");
            String tipo = scanner.nextLine();

            System.out.print("Introduce el nivel de gravedad: Leve - Medio - Alto: ");
            String gravedadTexto = scanner.nextLine();

            String gravedad = convertirGravedad(gravedadTexto);

            System.out.print("Introduce ubicación: ");
            String ubicacion = scanner.nextLine();

            System.out.print("Introduce municipio: ");
            String municipio = scanner.nextLine();

            System.out.print("Introduce datos usuario (nombre/teléfono): ");
            String datosUsuario = scanner.nextLine();

            if (tipo.isEmpty() || municipio.isEmpty() || datosUsuario.isEmpty()) {
                System.out.println("Error: todos los campos son obligatorios.");
                return null;
            }
            // Validación de gravedad
            if (validateSeverity(tipo)) {
                return new EmergencyEvent(tipo, ubicacion, municipio, datosUsuario, gravedad);
            } else {
                System.out.println("Emergencia no válida (falso positivo).");
            }
        }

        } catch (Exception e){
                System.err.println("Error al detectar emergencia: " + e.getMessage());
            }

        return null;
    }

// he añadido para valorar la gravedad, no hace falta poner todo el texto, y si la gravedad es
//alta, sale otro mensaje

    private String convertirGravedad(String gravedadTexto) {

        gravedadTexto = gravedadTexto.toLowerCase();

        if (gravedadTexto.startsWith("lev")) {
            return "LEVE";
        } else if (gravedadTexto.startsWith("med")) {
            return "MEDIA";
        } else if (gravedadTexto.startsWith("alt")) {
            return "ALTA";
        } else {
            System.out.println("Gravedad no reconocida, se asigna leve.");
            return "LEVE";
        }
    }

    private boolean validateSeverity(String tipo) {
        // Ejemplo simple: si el tipo tiene "grave" retorna true
        return tipo.toLowerCase().contains("grave") || umbral > 0;
    }
}
