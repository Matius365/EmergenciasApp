package main;

import detector.EmergencyDetector;
import alert.AlertSender;
import controller.EmergencyManager;

public class Main {
    public static void main(String[] args) {

        // Inicialización con parámetros
        EmergencyDetector detector = new EmergencyDetector(1); // umbral
        AlertSender sender = new AlertSender("112"); // destino

        EmergencyManager manager = new EmergencyManager(detector, sender);
        manager.startSystem();
    }
}