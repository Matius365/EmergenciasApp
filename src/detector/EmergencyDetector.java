package detector;

import model.EmergencyEvent;
import java.util.Scanner;

public class EmergencyDetector {
    private int umbral; // ejemplo de validación de gravedad

    public EmergencyDetector(int umbral) {
        this.umbral = umbral;
    }

    public EmergencyEvent detectEvent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Desea activar emergencia? (S/N)");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Introduce tipo de emergencia: ");
            String tipo = scanner.nextLine();

            System.out.print("Introduce ubicación: ");
            String ubicacion = scanner.nextLine();

            System.out.print("Introduce datos usuario (nombre/teléfono): ");
            String datosUsuario = scanner.nextLine();

            // Validación simple de gravedad
            if (validateSeverity(tipo)) {
                return new EmergencyEvent(tipo, ubicacion, datosUsuario);
            } else {
                System.out.println("Emergencia no válida (falso positivo).");
            }
        }

        return null;
    }

    private boolean validateSeverity(String tipo) {
        // Ejemplo simple: si el tipo tiene "grave" retorna true
        return tipo.toLowerCase().contains("grave") || umbral > 0;
    }
}
