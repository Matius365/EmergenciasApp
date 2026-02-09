package main;

import detector.EmergencyDetector;
import alert.AlertSender;
import controller.EmergencyManager;
import model.CentroSalud;
import model.CentroSaludService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Inicialización con parámetros
        EmergencyDetector detector = new EmergencyDetector(1); // umbral
        AlertSender sender = new AlertSender("112"); // destino

        EmergencyManager manager = new EmergencyManager(detector, sender);
        manager.startSystem();


        List<CentroSalud> centros =
                CentroSaludService.cargarCentros("centros_salud.json");

        if (centros != null) {
            System.out.println("Centros cargados: " + centros.size());

            for (CentroSalud c : centros) {
                System.out.println(c);
            }
        }
    }
}
