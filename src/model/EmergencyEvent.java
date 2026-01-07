package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmergencyEvent {
    private String tipoEmergencia;
    private String ubicacion;
    private String datosUsuario;
    private String fechaHora; // nueva propiedad

    public EmergencyEvent(String tipoEmergencia, String ubicacion, String datosUsuario) {
        this.tipoEmergencia = tipoEmergencia;
        this.ubicacion = ubicacion;
        this.datosUsuario = datosUsuario;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters y setters
    public String getTipoEmergencia() {
        return tipoEmergencia;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public String getDatosUsuario() {
        return datosUsuario;
    }
    @Override
    public String toString() {
        return "Tipo: " + tipoEmergencia + ", Ubicación: " + ubicacion + ", Usuario: " + datosUsuario
                + ", FechaHora: " + fechaHora;
    }
}
