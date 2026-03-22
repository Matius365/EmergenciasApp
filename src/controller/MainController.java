package controller;

import alert.AlertSender;
import detector.EmergencyDetector;
import javafx.scene.control.Label;
import main.Main;
import model.EmergencyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.UserData;

public class MainController {

    @FXML private TextArea areaTexto;

    // Campos del formulario
    @FXML private VBox emergenciaForm;
    @FXML private TextField ubicacionField;
    @FXML private TextField municipioField;
    @FXML private TextField usuarioField;
    @FXML private TextField tipoField;
    @FXML private ChoiceBox<String> gravedadChoiceBox;
    @FXML private Label usuarioLabel;

    private EmergencyDetector detector = new EmergencyDetector(1);
    private AlertSender sender = new AlertSender("112");
    private EmergencyManager manager = new EmergencyManager(detector, sender);
    private UserData usuarioActual = null;

    // Mostrar el formulario al pulsar "Declarar Emergencia"
    @FXML
    public void declararEmergencia() {

        boolean visible = emergenciaForm.isVisible();

        if (!visible) {
            // Mostrar formulario
            emergenciaForm.setManaged(true);
            emergenciaForm.setVisible(true);

            // Reducir área de texto
            areaTexto.setPrefHeight(200);

        } else {
            // Ocultar formulario
            emergenciaForm.setManaged(false);
            emergenciaForm.setVisible(false);

            areaTexto.setPrefHeight(600);
        }

        if (usuarioActual != null) {
            areaTexto.setText("Usuario activo: " + usuarioActual + "\nIntroduce los datos de la emergencia.");
        } else {
            areaTexto.setText("Introduce los datos de la emergencia.\n(Usuario manual requerido)");
        }
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

    // Mostrar centros en el área de texto
    @FXML
    public void mostrarCentros() {
        emergenciaForm.setManaged(false);
        emergenciaForm.setVisible(false);
        areaTexto.setPrefHeight(600); // más pequeño
        areaTexto.setText(Main.mostrarCentros());

    }

    // Mostrar histórico en el área de texto
    @FXML
    public void verHistorico() {
        emergenciaForm.setManaged(false);
        emergenciaForm.setVisible(false);
        areaTexto.setPrefHeight(600); // más pequeño
        areaTexto.setText(Main.historicoAlertas());

    }

    @FXML
    public void initialize() {
        usuarioLabel.setText("No has iniciado sesión");
    }

    //metodo para el loguin
    @FXML
    public void loginUsuario() {
        // loguin simple
        usuarioActual = new UserData("Jose M.L.", "600111999");
        usuarioField.setVisible(false);
        usuarioField.setManaged(false);
        usuarioField.setText(usuarioActual.toString());
        usuarioField.setDisable(true);
        usuarioLabel.setText("Usuario: " + usuarioActual);
        areaTexto.setText("Sesión iniciada correctamente.");
    }

    //metodo para cerrar sesion
    @FXML
    public void logoutUsuario() {
        usuarioActual = null;
        usuarioLabel.setText("No has iniciado sesión");
        // Volver a mostrar campo manual
        usuarioField.setVisible(true);
        usuarioField.setManaged(true);
        usuarioField.setDisable(false);
        usuarioField.clear();
        areaTexto.setText("Sesión cerrada.");
    }
}