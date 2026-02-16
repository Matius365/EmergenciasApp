package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmergencyEvent {
    private String tipoEmergencia;
    private String ubicacion;
    private String datosUsuario;
    private String fechaHora; // nueva feature agrega la fecha y la hora de la emergencia

    public EmergencyEvent(){} //constructor vacio para JSON

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
    public String getFechaHora() {
        return fechaHora;
    }

    public void setTipoEmergencia(String tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDatosUsuario(String datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipoEmergencia + ", Ubicación: " + ubicacion + ", Usuario: " + datosUsuario
                + ", FechaHora: " + fechaHora;
    }
}
