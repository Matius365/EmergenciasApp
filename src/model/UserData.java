package model;

public class UserData {
    private String nombre;
    private String telefono;

    public UserData(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return nombre + " (" + telefono + ")";
    }
}
