package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmergencyEvent {
    private String tipoEmergencia;
    private String ubicacion;
    private String municipio;
    private String datosUsuario;
    private String fechaHora; // nueva feature agrega la fecha y la hora de la emergencia
    private String gravedad;


    //constructor vacio para JSON
    public EmergencyEvent(){
    }

    public EmergencyEvent(String tipoEmergencia, String ubicacion, String municipio, String datosUsuario, String gravedad) {
        this.tipoEmergencia = tipoEmergencia;
        this.ubicacion = ubicacion;
        this.municipio = municipio;
        this.datosUsuario = datosUsuario;
        this.gravedad = gravedad;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters y setters
    public String getTipoEmergencia() {
        return tipoEmergencia;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public String getMunicipio(){return municipio; }
    public String getDatosUsuario() {
        return datosUsuario;
    }
    public String getFechaHora() {
        return fechaHora;
    }
    public String getGravedad() {return gravedad;
    }

    public void setTipoEmergencia(String tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    public void setGravedad(String gravedad) { this.gravedad = gravedad; }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setMunicipio(String municipio) {this.municipio = municipio; }

    public void setDatosUsuario(String datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public String toString() {
        return "Tipo: " + tipoEmergencia +
                ", Gravedad: " + gravedad +
                ", Ubicación: " + ubicacion +
                ", Municipio: " + municipio +
                ", Usuario: " + datosUsuario +
                ", FechaHora: " + fechaHora;
    }
}
