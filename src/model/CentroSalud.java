package model;

public class CentroSalud {

    private String codigo;
    private String nombre;
    private String direccion;
    private String cp;
    private String municipio;
    private String pedania;
    private String telefono;
    private String fax;
    private String email;
    private String urlReal;
    private String urlCorta;
    private String latitud;
    private String longitud;
    private String foto1;

    public CentroSalud() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getPedania() {
        return pedania;
    }

    public void setPedania(String pedania) {
        this.pedania = pedania;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlReal() {
        return urlReal;
    }

    public void setUrlReal(String urlReal) {
        this.urlReal = urlReal;
    }

    public String getUrlCorta() {
        return urlCorta;
    }

    public void setUrlCorta(String urlCorta) {
        this.urlCorta = urlCorta;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }
    @Override
    public String toString() {
        return nombre + " - " + municipio + " (" + telefono + ")";
    }
}
