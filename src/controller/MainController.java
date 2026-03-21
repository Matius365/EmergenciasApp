package controller;

import alert.AlertSender;
import detector.EmergencyDetector;
import main.Main;
import model.EmergencyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML private TextArea areaTexto;

    // Campos del formulario
    @FXML private VBox emergenciaForm;
    @FXML private TextField ubicacionField;
    @FXML private TextField municipioField;
    @FXML private TextField usuarioField;
    @FXML private TextField tipoField;
    @FXML private ChoiceBox<String> gravedadChoiceBox;

    private EmergencyDetector detector = new EmergencyDetector(1);
    private AlertSender sender = new AlertSender("112");
    private EmergencyManager manager = new EmergencyManager(detector, sender);


    // Mostrar el formulario al pulsar "Declarar Emergencia"
    @FXML
    public void declararEmergencia() {
        // Mostrar u ocultar el formulario
        boolean visible = emergenciaForm.isVisible();
        emergenciaForm.setVisible(!visible);
        areaTexto.clear();
        areaTexto.appendText("Introduce los datos de la emergencia.\n");
    }

    // Enviar la emergencia al pulsar el botón del formulario
    @FXML
    public void enviarEmergencia() {
        String ubicacion = ubicacionField.getText().trim();
        String municipio = municipioField.getText().trim();
        String usuario = usuarioField.getText().trim();
        String tipo = tipoField.getText().trim();
        String gravedad = gravedadChoiceBox.getValue();

        if (ubicacion.isEmpty() || municipio.isEmpty() || usuario.isEmpty() ||
                tipo == null || gravedad == null) {
            areaTexto.setText("Faltan datos para declarar la emergencia.");
            return;
        }

        EmergencyEvent event = new EmergencyEvent(tipo, ubicacion, municipio, usuario, gravedad);

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
        areaTexto.setText(Main.mostrarCentros());
    }

    // Mostrar histórico en el área de texto
    @FXML
    public void verHistorico() {
        areaTexto.setText(Main.historicoAlertas());
    }
}