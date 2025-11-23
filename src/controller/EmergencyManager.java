package controller;

import detector.EmergencyDetector;
import alert.AlertSender;
import model.EmergencyEvent;

public class EmergencyManager {
    private EmergencyDetector detector;
    private AlertSender sender;

    public EmergencyManager(EmergencyDetector detector, AlertSender sender) {
        this.detector = detector;
        this.sender = sender;
    }

    public void startSystem() {
        try {
            EmergencyEvent event = detector.detectEvent();
            if (event != null) {
                sender.sendAlert(event);
                sender.notifyContacts();
            } else {
                System.out.println("No se detectó emergencia.");
            }
        } catch (Exception e) {
            System.err.println("Error en el sistema de emergencias: " + e.getMessage());
        }
    }
}
