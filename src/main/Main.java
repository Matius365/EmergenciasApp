package main;

import detector.EmergencyDetector;
import alert.AlertSender;
import controller.EmergencyManager;
import model.CentroSalud;
import model.CentroSaludService;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
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
                System.out.println("3. Ver Histórico de Alertas Registradas.");
                System.out.println("0. Salir.");
                System.out.println("Elige opcion:");

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion){
                    case 1: manager.startSystem();
                    break;

                    case 2: mostrarCentros();
                    break;

                    case 3: historicoAlertas();
                    break;

                    case 0: System.out.println("Saliendo del Sistema.");
                    break;

                }

            }catch (InputMismatchException e){
                System.out.println("Introduce un numero, por favor.");
                sc.nextLine();
            }
        }while (opcion !=0);
    }

    //metodos para mostrar los datos del json y de alertas.txt
    //hay dos archivos json para los centros de salud, uno tal cual viene en Aules y otro modificado,
    //para poder leer el de Aules, he tenido que utilizar @JsonProperty y asi no se cambian los campos
    // del archivo json y lo lee tal cual.
    public static void mostrarCentros(){
        List<CentroSalud> centros =
                CentroSaludService.cargarCentros("src/resources/CentrosdeSalud.json");

        if (centros != null) {
            System.out.println("Centros cargados: " + centros.size());

            for (CentroSalud c : centros) {
                System.out.println(c);
            }
        }
        else {
            System.out.println("No se puede cargar los centros de salud.");
        }
    }

    public static void historicoAlertas(){
        try (BufferedReader br =
                new BufferedReader(new FileReader("src/resources/alertas.txt"))){

            String linea;
            System.out.println("===HISTORICO DE ALERTAS===");

            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }

        } catch (java.io.IOException e) {
            System.out.println("Error al leer el archivo " + e.getMessage());
        }

    }
}
