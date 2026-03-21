package controller;

import javafx.fxml.FXML;
import main.Main;

import javafx.scene.control.TextArea;

public class MainController {

    public void declararEmergencia() {
        System.out.println("Emergencia");
    }
    @FXML
    private TextArea areaTexto;

    public void mostrarCentros() {
        areaTexto.setText(Main.mostrarCentros());
    }

    public void verHistorico() {
        areaTexto.setText(Main.historicoAlertas());
    }
}