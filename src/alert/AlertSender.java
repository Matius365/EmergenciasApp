package alert;

import model.EmergencyEvent;
import java.io.FileWriter;
import java.io.IOException;

public class AlertSender {
    private String destino;

    public AlertSender(String destino) {
        this.destino = destino;
    }

    public void sendAlert(EmergencyEvent event) {
        if (event == null) {
            System.out.println("No hay emergencia que enviar.");
            return;
        }

        System.out.println("Enviando alerta a " + destino + ": " + event);

        // Persistencia en archivo
        try (FileWriter writer = new FileWriter("src/resources/alertas.txt", true)) {
            writer.write(event.toString() + "\n");
        } catch (IOException e) {
            System.err.println("Error al guardar alerta: " + e.getMessage());
        }
    }

    public void notifyContacts() {
        System.out.println("Simulando contacto con servicios de emergencia y contactos personales...");
    }
}
