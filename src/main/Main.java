package main;

import detector.EmergencyDetector;
import alert.AlertSender;
import controller.EmergencyManager;
import model.CentroSalud;
import model.CentroSaludService;
import tools.jackson.databind.ObjectMapper;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EmergencyDetector detector = new EmergencyDetector(1); // umbral
        AlertSender sender = new AlertSender("112"); // destino
        EmergencyManager manager = new EmergencyManager(detector, sender);

        int opcion = 0;

        do{
            try{
                System.out.println("======Menu======");
                System.out.println("1. Declarar Emergencia.");
                System.out.println("2. Mostrar Centros de Salud de Murcia.");
                System.out.println("3. Ver Alertas Registradas.");
                System.out.println("0. Salir.");
                System.out.println("Elige opcion:");

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion){
                    case 1: manager.startSystem();

                }

            }catch (InputMismatchException e){
                System.out.println("Introduce un numero, por favor.");
                sc.nextLine();
            }
        }while (opcion !=0);


        // Inicialización con parámetros



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
