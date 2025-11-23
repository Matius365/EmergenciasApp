package model;

public class EmergencyEvent {
    private String tipoEmergencia;
    private String ubicacion;
    private String datosUsuario;

    public EmergencyEvent(String tipoEmergencia, String ubicacion, String datosUsuario) {
        this.tipoEmergencia = tipoEmergencia;
        this.ubicacion = ubicacion;
        this.datosUsuario = datosUsuario;
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
        return "Tipo: " + tipoEmergencia + ", Ubicación: " + ubicacion + ", Usuario: " + datosUsuario;
    }
}
