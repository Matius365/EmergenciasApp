package alert;

import model.EmergencyEvent;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        // Creamos archivo .txt para guardar alertas
//        try (FileWriter writer = new FileWriter("src/resources/alertas.txt", true)) {
//            writer.write(event.toString() + "\n");
//        } catch (IOException e) {
//            System.err.println("Error al guardar alerta: " + e.getMessage());
//        }

        // Creamos archivo .json para guardar alertas
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/resources/alertas.json");

        try {
            List<EmergencyEvent> lista;

            if (file.exists()){
                lista = mapper.readValue(file,
                        new TypeReference<List<EmergencyEvent>>() {});
            } else {
                lista = new ArrayList<>();
            }
            //Añadimos la alerta a lista
            lista.add(event);

            //Guardamos el JSON actualizado
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, lista);

        } catch (Exception e){
            System.err.println("Error al guardar alertas.json: " + e.getMessage());
        }
    }

    public void notifyContacts() {
        System.out.println("Enviando alerta al 112 y contactos personales... \nMostrando Centros de Salud cercanos");
    }
}
