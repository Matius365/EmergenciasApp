package alert;

import model.CentroSalud;
import model.CentroSaludService;
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
    private static final String RUTA_CENTROS = "src/resources/CentrosdeSalud.json";

    public AlertSender(String destino) {
        this.destino = destino;
    }

//    public void sendAlert(EmergencyEvent event) {
//        if (event == null) {
//            System.out.println("No hay emergencia que enviar.");
//            return;
//        }
//
//        System.out.println("\n NUEVA ALERTA");
//        System.out.println(event);
//
//        // Activación total si la gravedad es ALTA
//        if(event.getGravedad().equalsIgnoreCase("ALTA")){
//            System.out.println("\n🚨 ACTIVANDO TODOS LOS SERVICIOS DE EMERGENCIA:");
//            System.out.println("SAMU, GUARDIA CIVIL, BOMBEROS, POLICÍA...");
//        }
//
//        // BUSCAR CENTROS DEL MUNICIPIO
//        List<CentroSalud> centros =
//                CentroSaludService.buscarPorMunicipio(
//                        RUTA_CENTROS,
//                        event.getMunicipio()
//                );
//
//        if (centros.isEmpty()) {
//            System.out.println("No se encontraron centros en el municipio "
//                    + event.getMunicipio());
//        } else {
//            System.out.println("\n Enviando alerta a los siguientes centros:");
//
//            for (CentroSalud c : centros) {
//                System.out.println("- " + c.getNombre());
//                System.out.println("- " + c.getDireccion());
//                System.out.println("- " + c.getMunicipio());
//                System.out.println("- " + c.getTelefono());
//
//
//            }
//        }
//
//
////        System.out.println("Enviando alerta a " + destino + ": " + event);
//
//        // Creamos archivo .txt para guardar alertas
////        try (FileWriter writer = new FileWriter("src/resources/alertas.txt", true)) {
////            writer.write(event.toString() + "\n");
////        } catch (IOException e) {
////            System.err.println("Error al guardar alerta: " + e.getMessage());
////        }
//
//        // Creamos archivo .json para guardar alertas
//        ObjectMapper mapper = new ObjectMapper();
//        File file = new File("src/resources/alertas.json");
//
//        try {
//            List<EmergencyEvent> lista;
//
//            if (file.exists()){
//                lista = mapper.readValue(file,
//                        new TypeReference<List<EmergencyEvent>>() {});
//            } else {
//                lista = new ArrayList<>();
//            }
//            //Añadimos la alerta a lista
//            lista.add(event);
//
//            //Guardamos el JSON actualizado
//            mapper.writerWithDefaultPrettyPrinter()
//                    .writeValue(file, lista);
//
//        } catch (Exception e){
//            System.err.println("Error al guardar alertas.json: " + e.getMessage());
//        }
//    }
//
//    public void notifyContacts() {
//        System.out.println("Enviando alerta al 112 y contactos personales...");
//    }

//nuevo metodo para javafx
public void sendAlert(EmergencyEvent event) {
    if (event == null) return;

    // Guardar la alerta en JSON
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/resources/alertas.json");
    try {
        List<EmergencyEvent> lista;
        if (file.exists()){
            lista = mapper.readValue(file, new TypeReference<List<EmergencyEvent>>() {});
        } else {
            lista = new ArrayList<>();
        }
        lista.add(event);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, lista);
    } catch (Exception e){
        System.err.println("Error al guardar alertas.json: " + e.getMessage());
    }
}

    // Método para obtener texto de la alerta listo para mostrar en interfaz
    public String generarMensajeAlerta(EmergencyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("NUEVA ALERTA\n");
        sb.append(event).append("\n\n");

        // Mensaje de activación si gravedad ALTA
        if(event.getGravedad().equalsIgnoreCase("ALTA")){
            sb.append("🚨 ACTIVANDO TODOS LOS SERVICIOS DE EMERGENCIA:\n");
            sb.append("SAMU, GUARDIA CIVIL, BOMBEROS, POLICÍA...\n\n");
        }

        // Centros de salud del municipio
        List<CentroSalud> centros = CentroSaludService.buscarPorMunicipio(RUTA_CENTROS, event.getMunicipio());
        if (centros.isEmpty()) {
            sb.append("No se encontraron centros en el municipio ").append(event.getMunicipio()).append("\n");
        } else {
            sb.append("Centros de salud en ").append(event.getMunicipio()).append(":\n");
            for (CentroSalud c : centros) {
                sb.append("- ").append(c.getNombre())
                        .append(", ").append(c.getDireccion())
                        .append(", Tel: ").append(c.getTelefono())
                        .append("\n");
            }
        }

        return sb.toString();
    }

    public void notifyContacts() {
        System.out.println("Enviando alerta al 112 y contactos personales...");
    }
}
