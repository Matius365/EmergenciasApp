package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserData;

import java.util.function.Consumer;

public class LoginController {

    @FXML private ChoiceBox<String> usuarioChoiceBox;
    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;

    private UserData usuario;
    private Consumer<UserData> onLoginSuccess;

    //al iniciar podemos elegir con un combobox, un usuario predeterminado o un nuevo usuario
    @FXML
    public void initialize() {
        usuarioChoiceBox.getItems().addAll(
                "Seleccionar usuario...",
                "Jose M.L. (600111999)",
                "Nuevo usuario"
        );
        usuarioChoiceBox.setValue("Seleccionar usuario...");

        usuarioChoiceBox.setOnAction(e -> manejarSeleccion());
        nombreField.setDisable(true);
        telefonoField.setDisable(true);
    }

    private void manejarSeleccion() {
        String seleccion = usuarioChoiceBox.getValue();
        if ("Nuevo usuario".equals(seleccion)) {
            nombreField.setDisable(false);
            telefonoField.setDisable(false);
        } else {
            nombreField.setDisable(true);
            telefonoField.setDisable(true);
        }
    }

    public void setOnLoginSuccess(Consumer<UserData> callback) {
        this.onLoginSuccess = callback;
    }

    //metodo una vez elegimos usuario
    @FXML
    public void login() {
        String seleccion = usuarioChoiceBox.getValue();

        if ("Jose M.L. (600111999)".equals(seleccion)) {
            usuario = new UserData("Jose M.L.", "600111999");
        } else if ("Nuevo usuario".equals(seleccion)) {
            String nombre = nombreField.getText().trim();
            String telefono = telefonoField.getText().trim();
            if (nombre.isEmpty() || telefono.isEmpty()) return;
            usuario = new UserData(nombre, telefono);
        } else {
            return;
        }

        if (onLoginSuccess != null) {
            onLoginSuccess.accept(usuario);
        }
    }

    //metodo boton salir
    @FXML
    public void salirApp() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
}


//package controller;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import model.UserData;
//
//import java.util.function.Consumer;
//
//
//public class LoginController {
//
//    @FXML private ChoiceBox<String> usuarioChoiceBox;
//    @FXML private TextField nombreField;
//    @FXML private TextField telefonoField;
//
//    private UserData usuario;
//
//    @FXML
//    public void initialize() {
//        usuarioChoiceBox.getItems().addAll(
//                "Seleccionar usuario...",
//                "Jose M.L. (600111999)",
//                "Nuevo usuario"
//        );
//
//        usuarioChoiceBox.setValue("Seleccionar usuario...");
//
//        // Listener para cambiar comportamiento
//        usuarioChoiceBox.setOnAction(e -> manejarSeleccion());
//    }
//
//    private void manejarSeleccion() {
//        String seleccion = usuarioChoiceBox.getValue();
//
//        if (seleccion.equals("Nuevo usuario")) {
//            nombreField.setDisable(false);
//            telefonoField.setDisable(false);
//        } else if (seleccion.contains("Jose")) {
//            nombreField.setDisable(true);
//            telefonoField.setDisable(true);
//        }
//    }
//    private Consumer<UserData> onLoginSuccess;
//
//    public void setOnLoginSuccess(Consumer<UserData> callback) {
//        this.onLoginSuccess = callback;
//    }
//
//    public UserData getUsuario() {
//        return usuario;
//    }
//
//    @FXML
//    public void login() {
//        String seleccion = usuarioChoiceBox.getValue();
//
//        if (seleccion.equals("Jose M.L. (600111999)")) {
//            usuario = new UserData("Jose M.L.", "600111999");
//
//        } else if (seleccion.equals("Nuevo usuario")) {
//
//            String nombre = nombreField.getText().trim();
//            String telefono = telefonoField.getText().trim();
//
//            if (nombre.isEmpty() || telefono.isEmpty()) {
//                return;
//            }
//            usuario = new UserData(nombre, telefono);
//        } else {
//            return;
//        }
//        if (onLoginSuccess != null) {
//            onLoginSuccess.accept(usuario);
//        }
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/MainView.fxml"));
//            Parent root = loader.load();
//
//            // 👉 PASAR USUARIO AL MAIN
//            MainController controller = loader.getController();
//            controller.setUsuario(usuario);
//
//            Stage stage = (Stage) nombreField.getScene().getWindow();
//
//            stage.setScene(new Scene(root, 900, 800)); // tamaño app
//            stage.centerOnScreen(); // 🔥 centrado
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Stage stage = (Stage) nombreField.getScene().getWindow();
//        stage.close();
//    }
//
//}
