package main;

import controller.LoginController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserData;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        mostrarLogin(primaryStage);
    }

    public void mostrarLogin(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/LoginView.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setOnLoginSuccess(usuario -> mostrarMain(stage, usuario));

            Scene scene = new Scene(root, 600, 450); // Tamaño más grande
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarMain(Stage stage, UserData usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/MainView.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setUsuarioActual(usuario);
            controller.setMainApp(this); // Referencia para logout

            Scene scene = new Scene(root, 900, 800);
            stage.setScene(scene);
            stage.setTitle("EmergenciasApp");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//package main;
//
//import controller.MainController;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import model.UserData;
//import controller.LoginController;
//
//public class MainApp extends Application {
//
//    public void mostrarLogin(Stage stage) {
//        try {
//            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/main/LoginView.fxml"));
//            Parent loginRoot = loginLoader.load();
//
//            Scene loginScene = new Scene(loginRoot, 500, 400);
//            stage.setScene(loginScene);
//            stage.setTitle("Login");
//            stage.centerOnScreen(); // 👈 IMPORTANTE
//
//            LoginController loginController = loginLoader.getController();
//
//            // Cuando se haga login, cargar Main
//            loginController.setOnLoginSuccess(usuario -> mostrarMain(stage, usuario));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void mostrarMain(Stage stage, UserData usuario) {
//        try {
//            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/main/MainView.fxml"));
//            Parent mainRoot = mainLoader.load();
//
//            MainController mainController = mainLoader.getController();
//            mainController.setUsuarioActual(usuario);
//
//            // 👇 PASAMOS referencia de MainApp
//            mainController.setMainApp(this);
//
//            stage.setScene(new Scene(mainRoot, 900, 800));
//            stage.setTitle("EmergenciasApp");
//            stage.centerOnScreen();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        mostrarLogin(primaryStage);
//    }
////    @Override
////    public void start(Stage primaryStage) throws Exception {
//////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/MainView.fxml"));
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/LoginView.fxml"));
////        Parent root = loader.load();
////        Scene scene = new Scene(root);
////        primaryStage.setTitle("EmergenciasApp");
////        primaryStage.setScene(scene);
////        primaryStage.show();
////    }
////
////    public static void main(String[] args) {
////        launch(args);
////    }
//
//    //nuevo metodo para el login
////@Override
////public void start(Stage primaryStage) throws Exception {
////
////    // 1. Cargar LOGIN
////    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/main/LoginView.fxml"));
////    Parent loginRoot = loginLoader.load();
////
////    Stage loginStage = new Stage();
////    loginStage.setScene(new Scene(loginRoot, 500, 400));
////    loginStage.setTitle("Login");
////    loginStage.showAndWait(); // ⬅️ IMPORTANTE (espera al login)
////
////    // 2. Obtener usuario del login
////    LoginController loginController = loginLoader.getController();
////    UserData usuario = loginController.getUsuario();
////
////    if (usuario == null) {
////        System.exit(0); // si no inicia sesión
////    }
////
////    // 3. Cargar MAIN
////    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/main/MainView.fxml"));
////    Parent mainRoot = mainLoader.load();
////
////    // 4. Pasar usuario al MainController
////    MainController mainController = mainLoader.getController();
////    mainController.setUsuarioActual(usuario);
////
////    // 5. Mostrar app principal
////    primaryStage.setScene(new Scene(mainRoot));
////    primaryStage.setTitle("EmergenciasApp");
////    primaryStage.show();
////  }
//}