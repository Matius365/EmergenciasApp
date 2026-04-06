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
                System.out.println("======Menu======");
                System.out.println(CYAN + BOLD + "" + RESET + GREEN + "  1. Declarar Emergencia      " + CYAN + BOLD + "" + RESET);
                System.out.println(CYAN + BOLD + "" + RESET + GREEN + "  2. Centros de Salud Murcia  " + CYAN + BOLD + "" + RESET);
                System.out.println(CYAN + BOLD + "" + RESET + GREEN + "  3. Histórico de Alertas     " + CYAN + BOLD + "" + RESET);
                System.out.println(CYAN + BOLD + "" + RESET + GREEN + "  4. Acceso a Agenda de teléfonos       " + CYAN + BOLD + "" + RESET);
                System.out.println(CYAN + BOLD + "" + RESET + RED   + "  0. Salir                    " + CYAN + BOLD + "" + RESET);
                System.out.println(CYAN + BOLD + "" + RESET);
                System.out.print(YELLOW + "  Elige una opción: " + RESET);


                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion){
                    case 1: manager.startSystem();
                    break;

                    case 2: mostrarCentros();
                    break;

                    case 3: historicoAlertas();
                    break;

//                    case 4: accesoTelefonos();
//                    break;

                    case 0: System.out.println(RED + BOLD + "\n  Saliendo del Sistema.¡Hasta pronto!" + RESET);
                    break;
                    default: System.out.println(YELLOW + "  Opción no válida. Elige entre 0 y 3." + RESET);

                }

            }catch (InputMismatchException e){
                System.out.println("Introduce un numero, por favor." + RESET);
                sc.nextLine();
            }
        }while (opcion !=0);
    }

    //metodos para mostrar los datos del json y de alertas.txt
    //hay dos archivos json para los centros de salud, uno tal cual viene en Aules y otro modificado,
    //para poder leer el de Aules, he tenido que utilizar @JsonProperty y asi no se cambian los campos
    // del archivo json y lo lee tal cual.

    //metodo para mostrar los centros en consola
//    public static void mostrarCentros(){
//        List<CentroSalud> centros =
//                CentroSaludService.cargarCentros("src/resources/CentrosdeSalud.json");
//
//        if (centros == null || centros.isEmpty()) {
//            System.out.println(RED + "  No se pueden cargar los centros de salud." + RESET);
//            return;
//        }
//
//        Scanner sc = new Scanner(System.in);
//        int porPagina = 5;
//        int totalPaginas = (int) Math.ceil((double) centros.size() / porPagina);
//        int paginaActual = 1;
//
//        while (true) {
//            int inicio = (paginaActual - 1) * porPagina;
//            int fin = Math.min(inicio + porPagina, centros.size());
//
//            System.out.println(CYAN + BOLD + "\n " + RESET);
//            System.out.println(CYAN + BOLD + "    CENTROS DE SALUD         " + RESET);
//            System.out.println(CYAN + BOLD + "  Página " + paginaActual + " de " + totalPaginas
//                    + "                ".substring(String.valueOf(paginaActual).length() + String.valueOf(totalPaginas).length())
//                    + "║" + RESET);
//            System.out.println(CYAN + BOLD + "" + RESET);
//
//            for (int i = inicio; i < fin; i++) {
//                System.out.println(GREEN + "  [" + (i + 1) + "] " + RESET + centros.get(i));
//            }
//
//            System.out.println();
//            if (paginaActual > 1)           System.out.println(YELLOW + "  [A] Página anterior" + RESET);
//            if (paginaActual < totalPaginas) System.out.println(YELLOW + "  [S] Página siguiente" + RESET);
//            System.out.println(RED +    "  [0] Volver al menú" + RESET);
//            System.out.print(YELLOW + "  Elige una opción: " + RESET);
//
//            String input = sc.nextLine().trim().toLowerCase();
//
//            switch (input) {
//                case "s":
//                    if (paginaActual < totalPaginas) paginaActual++;
//                    else System.out.println(YELLOW + "  Ya estás en la última página." + RESET);
//                    break;
//                case "a":
//                    if (paginaActual > 1) paginaActual--;
//                    else System.out.println(YELLOW + "  Ya estás en la primera página." + RESET);
//                    break;
//                case "0":
//                    return;
//                default:
//                    System.out.println(RED + "  Opción no válida." + RESET);
//            }
//        }
//    }

    //metodo para mostrar los centros de salud en javafx
    public static String mostrarCentros(){

        List<CentroSalud> centros =
                CentroSaludService.cargarCentros("src/resources/CentrosdeSalud.json");

        if (centros == null || centros.isEmpty()) {
            return "No se pueden cargar los centros de salud.";
        }

        StringBuilder texto = new StringBuilder();

        for (CentroSalud c : centros) {
            texto.append(c.toString()).append("\n\n");
        }

        return texto.toString();
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

//    //Metodo para leer el historico alertas.json en consola
//    public static void historicoAlertas(){
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            File file = new File("src/resources/alertas.json");
//
//            if(!file.exists()){
//                System.out.println("No hay alertas registradas." + RESET);
//                return;
//            }
//            List<EmergencyEvent> lista =
//                    mapper.readValue(file,
//                            new TypeReference<List<EmergencyEvent>>() {});
//
//            System.out.println("====HISTORICO DE ALERTAS===="  + RESET);
//
//            for (EmergencyEvent e : lista){
//                System.out.println(e.getTipoEmergencia() + " - "
//                + e.getGravedad() + " - "
//                + e.getUbicacion() + " - "
//                + e.getDatosUsuario() +" - "
//                + e.getFechaHora());
//            }
//
//        } catch (Exception e){
//            System.out.println("Error leyendo alertas.json: " + e.getMessage() + RESET);
//        }
//    }

    //metodo para leer el historico de alertas en javafx
    public static String historicoAlertas(){

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/resources/alertas.json");

            if(!file.exists()){
                return "No hay alertas registradas.";
            }

            List<EmergencyEvent> lista =
                    mapper.readValue(file,
                            new TypeReference<List<EmergencyEvent>>() {});

            StringBuilder texto = new StringBuilder();

            for (EmergencyEvent e : lista){
                texto.append(e.toString()).append("\n\n");
            }

            return texto.toString();

        } catch (Exception e){
            return "Error leyendo alertas.json";
        }
    }
}
