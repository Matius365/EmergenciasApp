package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MainApp;
import model.EmergencyEvent;
import model.UserData;
import alert.AlertSender;

public class MainController {

    @FXML private TextArea areaTexto;
    @FXML private VBox emergenciaForm;
    @FXML private TextField ubicacionField;
    @FXML private TextField municipioField;
    @FXML private TextField usuarioField;
    @FXML private TextField tipoField;
    @FXML private ChoiceBox<String> gravedadChoiceBox;
    @FXML private Label usuarioLabel;

    private UserData usuarioActual;
    private MainApp mainApp;
    private AlertSender sender = new AlertSender("112"); // número de emergencias


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setUsuarioActual(UserData usuario) {
        this.usuarioActual = usuario;
        if (usuario != null) {
            usuarioLabel.setText("Usuario: " + usuario);
            usuarioField.setVisible(false);
            usuarioField.setManaged(false);
        } else {
            usuarioLabel.setText("No has iniciado sesión");
            usuarioField.setVisible(true);
            usuarioField.setManaged(true);
        }
    }

    //metodo para cerrar sesion
    @FXML
    public void logoutUsuario() {
        usuarioActual = null;
        if (mainApp != null) {
            Stage stage = (Stage) areaTexto.getScene().getWindow();
            mainApp.mostrarLogin(stage);
        }
    }

    //para que aparezca al inicio
    @FXML
    public void initialize() {
        usuarioLabel.setText("No has iniciado sesión");
    }


    //metodo para declarar la emergencia
    @FXML
    public void declararEmergencia() {
        boolean visible = emergenciaForm.isVisible();
        emergenciaForm.setVisible(!visible);
        emergenciaForm.setManaged(!visible);
        areaTexto.setPrefHeight(visible ? 600 : 200);
        if (!visible) {
            // 👇 LIMPIAR TEXTO cuando abres el formulario
            areaTexto.clear();

            if (usuarioActual != null) {
                areaTexto.setText("Usuario activo: " + usuarioActual +
                        "\nRellena el formulario para declarar una emergencia");
            } else {
                areaTexto.setText("Introduce los datos de la emergencia.");
            }
        }
    }


    //metodo para mostrar centros
    @FXML
    public void mostrarCentros() {
        // Ocultar formulario si estaba abierto
        emergenciaForm.setManaged(false);
        emergenciaForm.setVisible(false);
        // Ajustar altura del área de texto
        areaTexto.setPrefHeight(600);
        // Mostrar los centros de salud (llamando a tu clase Main)
        areaTexto.setText(Main.mostrarCentros());
    }


    //metodo para mostrar las emergencias-historico
    @FXML
    public void verHistorico() {
        // Ocultar formulario si estaba abierto
        emergenciaForm.setManaged(false);
        emergenciaForm.setVisible(false);
        // Ajustar altura del área de texto
        areaTexto.setPrefHeight(600);
        // Mostrar histórico de alertas
        areaTexto.setText(Main.historicoAlertas());
    }

        // Enviar la emergencia al pulsar el botón del formulario
    @FXML
    public void enviarEmergencia() {
        String ubicacion = ubicacionField.getText().trim();
        String municipio = municipioField.getText().trim();
//        String usuario = usuarioField.getText().trim();
        String tipo = tipoField.getText().trim();
        String gravedad = gravedadChoiceBox.getValue();

        String datosUsuario;
        if (usuarioActual != null) {
//            datosUsuario = usuarioActual.toString();
            datosUsuario = usuarioActual.getNombre() + " - " + usuarioActual.getTelefono();
        } else {
            String usuario = usuarioField.getText().trim();

            if (usuario.isEmpty()) {
                areaTexto.setText("Introduce datos de usuario o inicia sesión.");
                return;
            }
            datosUsuario = usuario;
        }

        if (ubicacion.isEmpty() || municipio.isEmpty() || //usuario.isEmpty()||
                 tipo.isEmpty() || tipo == null || tipo.isBlank() || gravedad == null) {
            areaTexto.setText("Faltan datos para declarar la emergencia.");
            return;
        }

        EmergencyEvent event = new EmergencyEvent(tipo, ubicacion, municipio, datosUsuario, gravedad);

        //guardamos la alerta
        sender.sendAlert(event);

        // Mostrar mensaje completo en TextArea
        String mensaje = sender.generarMensajeAlerta(event);
//        areaTexto.setText(mensaje);

        // Limpiar formulario y ocultarlo
        ubicacionField.clear();
        municipioField.clear();
        usuarioField.clear();
        tipoField.clear();
        gravedadChoiceBox.getSelectionModel().clearSelection();
        emergenciaForm.setVisible(false);

        areaTexto.setText(mensaje + "\nEmergencia declarada correctamente!");
    }

    //metodo para mostrar la agenda de telefonos

    @FXML
    public void accesoTelefonos() {
        // Ocultar formulario si estaba abierto
        emergenciaForm.setManaged(false);
        emergenciaForm.setVisible(false);

        // Ajustar tamaño del área
        areaTexto.setPrefHeight(600);

        // Mostrar datos de la BD
        areaTexto.setText(Main.accesoTelefonos());
    }
}


//package controller;
//
//import alert.AlertSender;
//import detector.EmergencyDetector;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.stage.Stage;
//import main.Main;
//import main.MainApp;
//import model.EmergencyEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import model.UserData;
//
//public class MainController {
//
//    @FXML private TextArea areaTexto;
//
//    // Campos del formulario
//    @FXML private VBox emergenciaForm;
//    @FXML private TextField ubicacionField;
//    @FXML private TextField municipioField;
//    @FXML private TextField usuarioField;
//    @FXML private TextField tipoField;
//    @FXML private ChoiceBox<String> gravedadChoiceBox;
//    @FXML private Label usuarioLabel;
//
//    private EmergencyDetector detector = new EmergencyDetector(1);
//    private AlertSender sender = new AlertSender("112");
//    private EmergencyManager manager = new EmergencyManager(detector, sender);
//    private UserData usuarioActual = null;
//    private MainApp mainApp;

//    public void setMainApp(MainApp mainApp) {
//        this.mainApp = mainApp;
//    }
//
//    // Mostrar el formulario al pulsar "Declarar Emergencia"
//    @FXML
//    public void declararEmergencia() {
//
//        boolean visible = emergenciaForm.isVisible();
//
//        if (!visible) {
//            // Mostrar formulario
//            emergenciaForm.setManaged(true);
//            emergenciaForm.setVisible(true);
//
//            // Reducir área de texto
//            areaTexto.setPrefHeight(200);
//
//        } else {
//            // Ocultar formulario
//            emergenciaForm.setManaged(false);
//            emergenciaForm.setVisible(false);
//
//            areaTexto.setPrefHeight(600);
//        }
//
//        if (usuarioActual != null) {
//            areaTexto.setText("Usuario activo: " + usuarioActual + "\nIntroduce los datos de la emergencia.");
//        } else {
//            areaTexto.setText("Introduce los datos de la emergencia.\n(Usuario manual requerido)");
//        }
//    }
//
//    // Enviar la emergencia al pulsar el botón del formulario
//    @FXML
//    public void enviarEmergencia() {
//        String ubicacion = ubicacionField.getText().trim();
//        String municipio = municipioField.getText().trim();
////        String usuario = usuarioField.getText().trim();
//        String tipo = tipoField.getText().trim();
//        String gravedad = gravedadChoiceBox.getValue();
//
//        String datosUsuario;
//        if (usuarioActual != null) {
////            datosUsuario = usuarioActual.toString();
//            datosUsuario = usuarioActual.getNombre() + " - " + usuarioActual.getTelefono();
//        } else {
//            String usuario = usuarioField.getText().trim();
//
//            if (usuario.isEmpty()) {
//                areaTexto.setText("Introduce datos de usuario o inicia sesión.");
//                return;
//            }
//            datosUsuario = usuario;
//        }
//
//        if (ubicacion.isEmpty() || municipio.isEmpty() || //usuario.isEmpty()||
//                 tipo.isEmpty() || tipo == null || tipo.isBlank() || gravedad == null) {
//            areaTexto.setText("Faltan datos para declarar la emergencia.");
//            return;
//        }
//
//        EmergencyEvent event = new EmergencyEvent(tipo, ubicacion, municipio, datosUsuario, gravedad);
//
//        //guardamos la alerta
//        sender.sendAlert(event);
//
//        // Mostrar mensaje completo en TextArea
//        String mensaje = sender.generarMensajeAlerta(event);
////        areaTexto.setText(mensaje);
//
//        // Limpiar formulario y ocultarlo
//        ubicacionField.clear();
//        municipioField.clear();
//        usuarioField.clear();
//        tipoField.clear();
//        gravedadChoiceBox.getSelectionModel().clearSelection();
//        emergenciaForm.setVisible(false);
//
//        areaTexto.setText(mensaje + "\nEmergencia declarada correctamente!");
//    }
//
//    // Mostrar centros en el área de texto
//    @FXML
//    public void mostrarCentros() {
//        emergenciaForm.setManaged(false);
//        emergenciaForm.setVisible(false);
//        areaTexto.setPrefHeight(600); // más pequeño
//        areaTexto.setText(Main.mostrarCentros());
//
//    }
//
//    // Mostrar histórico en el área de texto
//    @FXML
//    public void verHistorico() {
//        emergenciaForm.setManaged(false);
//        emergenciaForm.setVisible(false);
//        areaTexto.setPrefHeight(600); // más pequeño
//        areaTexto.setText(Main.historicoAlertas());
//
//    }
//
//    @FXML
//    public void initialize() {
//        usuarioLabel.setText("No has iniciado sesión");
//    }
//
//    //metodo para el loguin
////    @FXML
////    public void loginUsuario() {
////        // loguin simple
////        usuarioActual = new UserData("Jose M.L.", "600111999");
////        usuarioField.setVisible(false);
////        usuarioField.setManaged(false);
////        usuarioField.setText(usuarioActual.toString());
////        usuarioField.setDisable(true);
////        usuarioLabel.setText("Usuario: " + usuarioActual);
////        areaTexto.setText("Sesión iniciada correctamente.");
////    }
//
//    //metodo para cerrar sesion
////    @FXML
////    public void logoutUsuario() {
//        usuarioActual = null;
//        usuarioLabel.setText("No has iniciado sesión");
//        // Volver a mostrar campo manual
//        usuarioField.setVisible(true);
//        usuarioField.setManaged(true);
//        usuarioField.setDisable(false);
//        usuarioField.clear();
//        areaTexto.setText("Sesión cerrada.");
//    }

//    //nuevo metodo para cerrar sesion
//    @FXML
//    public void logoutUsuario() {
//
//            if (mainApp != null) {
//                Stage stage = (Stage) areaTexto.getScene().getWindow();
//                mainApp.mostrarLogin(stage); //usamos MainApp
//            }

//        try {
//            // Cargar login
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/LoginView.fxml"));
//            Parent root = loader.load();
//
//            Stage stage = (Stage) areaTexto.getScene().getWindow();
//            stage.setScene(new Scene(root, 400, 300));
//            stage.setTitle("Login");
//            stage.centerOnScreen(); // lo centra
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    //para el nuevo login
//    public void setUsuarioActual(UserData usuario) {
//        this.usuarioActual = usuario;
//
//        if (usuario != null) {
//            usuarioLabel.setText("Usuario: " + usuario);
//
//            // ocultar campo manual
//}