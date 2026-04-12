package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MainApp;
import model.EmergencyEvent;
import model.Persona;
import model.PersonaService;
import model.UserData;
import alert.AlertSender;

import java.util.List;

public class MainController {

    @FXML private TextArea areaTexto;
    @FXML private VBox emergenciaForm;
    @FXML private TextField ubicacionField;
    @FXML private TextField municipioField;
    @FXML private TextField usuarioField;
    @FXML private TextField tipoField;
    @FXML private ChoiceBox<String> gravedadChoiceBox;
    @FXML private Label usuarioLabel;
    @FXML private TextField nombreField;
    @FXML private TextField apellidoField;
    @FXML private TextField telefonoField;
    @FXML private VBox contactoForm;
    @FXML private VBox tablaContainer;
    @FXML private TableView<Persona> tablaPersonas;
    @FXML private TableColumn<Persona, Integer> colId;
    @FXML private TableColumn<Persona, String> colNombre;
    @FXML private TableColumn<Persona, String> colApellido;
    @FXML private TableColumn<Persona, String> colTelefono;

//    @FXML private TableView<Persona> tablaPersonas;
//    @FXML private TableColumn<Persona, String> colNombre;
//    @FXML private TableColumn<Persona, String> colTelefono;
//    @FXML private TableColumn<Persona, String> colTipo;

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

        //metodo para inicializar las columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }


    //metodo para declarar la emergencia
    @FXML
    public void declararEmergencia() {
        ocultarTodo();
        boolean visible = emergenciaForm.isVisible();
//        emergenciaForm.setVisible(!visible);
//        emergenciaForm.setManaged(!visible);
        contactoForm.setVisible(false);
        contactoForm.setManaged(false);
        emergenciaForm.setVisible(!emergenciaForm.isVisible());
        emergenciaForm.setManaged(emergenciaForm.isVisible());
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
        areaTexto.setVisible(true);
        areaTexto.setManaged(true);
    }


    //metodo para mostrar centros
    @FXML
    public void mostrarCentros() {
        ocultarTodo();
        // Ocultar formulario si estaba abierto
        emergenciaForm.setVisible(false);
        emergenciaForm.setManaged(false);

        contactoForm.setVisible(false);
        contactoForm.setManaged(false);

        // Ajustar altura del área de texto
        areaTexto.setPrefHeight(600);
        // Mostrar los centros de salud (llamando a tu clase Main)
        areaTexto.setText(Main.mostrarCentros());
        areaTexto.setVisible(true);
        areaTexto.setManaged(true);
    }


    //metodo para mostrar las emergencias-historico
    @FXML
    public void verHistorico() {
        ocultarTodo();
        // Ocultar formulario si estaba abierto
        emergenciaForm.setVisible(false);
        emergenciaForm.setManaged(false);

        contactoForm.setVisible(false);
        contactoForm.setManaged(false);

        // Ajustar altura del área de texto
        areaTexto.setPrefHeight(600);
        // Mostrar histórico de alertas
        areaTexto.setText(Main.historicoAlertas());
        areaTexto.setVisible(true);
        areaTexto.setManaged(true);
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

        ocultarTodo();

        //OCULTAR TEXTAREA
        areaTexto.setVisible(false);
        areaTexto.setManaged(false);

        //MOSTRAR TABLA
        tablaContainer.setVisible(true);
        tablaContainer.setManaged(true);

        List<Persona> personas = PersonaService.obtenerPersonas();
        tablaPersonas.setItems(FXCollections.observableArrayList(personas));

//        // Ocultar formulario si estaba abierto
//        emergenciaForm.setVisible(false);
//        emergenciaForm.setManaged(false);
//
//        // Ajustar tamaño del área
//        areaTexto.setPrefHeight(600);
//
//        // mostrar contacto
//        contactoForm.setVisible(true);
//        contactoForm.setManaged(true);
//
//        // Mostrar datos de la BD
//        areaTexto.setText(Main.accesoTelefonos());
    }

    //metodo para añadir contactos
    @FXML
    public void anadirContacto() {

        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String telefono = telefonoField.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || apellido.isEmpty()) {
            mostrarAlerta("Error","Rellena todos los campos, por favor.");
            return;
        }

        try {
            // 1. Crear objeto Persona
            Persona persona = new Persona(nombre, apellido, telefono);

            // 2. Guardar en base de datos
            PersonaService.insertarPersona(persona);

            // 3. Mensaje de confirmación
            mostrarAlerta("Persona añadida correctamente.", nombre + " "
                    + apellido + " - " + telefono);

            // 4. Limpiar campos
            nombreField.clear();
            apellidoField.clear();
            telefonoField.clear();
            accesoTelefonos();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Rellena todos los campos.");
//            areaTexto.setText("Error al añadir contacto: " + e.getMessage());
//            areaTexto.setText("Error al añadir contacto. Mira consola.");

        }
    }
    //metodo para mostrar alerta
    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    private void ocultarTodo() {

        emergenciaForm.setVisible(false);
        emergenciaForm.setManaged(false);

        contactoForm.setVisible(false);
        contactoForm.setManaged(false);

        tablaContainer.setVisible(false);
        tablaContainer.setManaged(false);

        areaTexto.setVisible(false);
        areaTexto.setManaged(false);
    }

    //metodo para mostrar el formulario para añadir contactos a la agenda
    @FXML
    public void mostrarFormularioContacto() {

        ocultarTodo();
        //OCULTAR TEXTAREA
        areaTexto.setVisible(false);
        areaTexto.setManaged(false);

        //MOSTRAR TABLA
        tablaContainer.setVisible(true);
        tablaContainer.setManaged(true);
        contactoForm.setVisible(true);
        contactoForm.setManaged(true);

        List<Persona> personas = PersonaService.obtenerPersonas();
        tablaPersonas.setItems(FXCollections.observableArrayList(personas));
    }

    //metodo para borrar contacto de agenda
    @FXML
    public void borrarSeleccionado() {

        //metodo para confirmacion antes de borrar
        Persona seleccionada = tablaPersonas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            areaTexto.setText("Selecciona una persona primero.");
            return;
        }

        // 🔴 ALERTA DE CONFIRMACIÓN
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Eliminar contacto?");
        alert.setContentText("Vas a eliminar a: "
                + seleccionada.getNombre() + " " + seleccionada.getApellido());

        // Esperar respuesta del usuario
        ButtonType resultado = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (resultado == ButtonType.OK) {

            // 👉 BORRAR
            PersonaService.eliminarPersona(seleccionada.getId());

            areaTexto.setText("Contacto eliminado correctamente.");

            // refrescar tabla
            accesoTelefonos();

        } else {
            areaTexto.setText("Eliminación cancelada.");
        }

//        Persona seleccionada = tablaPersonas.getSelectionModel().getSelectedItem();
//
//        if (seleccionada == null) {
//            areaTexto.setText("Selecciona una persona primero.");
//            return;
//        }
//
//        PersonaService.eliminarPersona(seleccionada.getId());
//
//        areaTexto.setText("Eliminado: " + seleccionada.getNombre());
//
//        // refrescar tabla
//        accesoTelefonos();
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