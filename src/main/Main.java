package main;

import detector.EmergencyDetector;
import alert.AlertSender;
import controller.EmergencyManager;
import model.CentroSalud;
import model.CentroSaludService;
import model.EmergencyEvent;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Colores ANSI
    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String GREEN  = "\u001B[32m";
    static final String RED    = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String BOLD   = "\u001B[1m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EmergencyDetector detector = new EmergencyDetector(1); // umbral
        AlertSender sender = new AlertSender("112"); // destino
        EmergencyManager manager = new EmergencyManager(detector, sender);

        int opcion = 0;

        do{
            try{
                System.out.println(CYAN + BOLD +"======Menu======");
                System.out.println(CYAN + BOLD +""+RESET + GREEN +"1. Declarar Emergencia.");
                System.out.println(CYAN + BOLD +""+RESET + GREEN +"2. Mostrar Centros de Salud de Murcia.");
                System.out.println(CYAN + BOLD +""+RESET + GREEN +"3. Ver Histórico de Alertas Registradas.");
                System.out.println(CYAN + BOLD +""+RESET + RED +"0. Salir.");
                System.out.println(YELLOW +"Elige opcion:"+ RESET);

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion){
                    case 1: manager.startSystem();
                    break;

                    case 2: mostrarCentros();
                    break;

                    case 3: historicoAlertas();
                    break;

                    case 0: System.out.println(RED + BOLD +"\n Saliendo del Sistema. ¿Hasta pronto!" + RESET);
                    break;
                    default: System.out.println(YELLOW + "  Opción no válida. Elige entre 0 y 3." + RESET);

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
            System.out.println("=== CENTROS CARGADOS: " + centros.size() + " ===");

            for (CentroSalud c : centros) {
                System.out.println(c);
            }
        }
        else {
            System.out.println("No se puede cargar los centros de salud.");
        }
    }


    //Este es el metodo para leer el historico de alertas.txt
//    public static void historicoAlertas(){
//        try (BufferedReader br =
//                new BufferedReader(new FileReader("src/resources/alertas.txt"))){
//
//            String linea;
//            System.out.println("===HISTORICO DE ALERTAS===");
//
//            while ((linea = br.readLine()) != null){
//                System.out.println(linea);
//            }
//
//        } catch (java.io.IOException e) {
//            System.out.println("Error al leer el archivo " + e.getMessage());
//        }
//
//    }

    //Metodo para leer alertas.json
    public static void historicoAlertas(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/resources/alertas.json");

            if(!file.exists()){
                System.out.println("No hay alertas registradas.");
                return;
            }
            List<EmergencyEvent> lista =
                    mapper.readValue(file,
                            new TypeReference<List<EmergencyEvent>>() {});

            System.out.println("====HISTORICO DE ALERTAS====");

            for (EmergencyEvent e : lista){
                System.out.println(e.getTipoEmergencia() + " - "
                + e.getUbicacion() + " - "
                + e.getDatosUsuario() +" - "
                + e.getFechaHora());
            }

        } catch (Exception e){
            System.out.println("Error leyendo alertas.json: " + e.getMessage());
        }
    }
}
