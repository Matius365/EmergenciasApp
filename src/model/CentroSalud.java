package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CentroSalud {
/*Hay dos formas para leer el json, esta primera es declarando las variables del constructor
tal cual lo hacemos como en cualquier clase, pero tenemos que cambiar el json para que coincidan
los campos, es decir, no puede haber nada "ilegal" como espacios en blanco, acentos,
puntos y otros simbolos, ya que java no lo leeria, saldria como "null".
*/
//    private String codigo;
//    private String nombre;
//    private String direccion;
//    private String cp;
//    private String municipio;
//    private String pedania;
//    private String telefono;
//    private String fax;
//    private String email;
//    private String urlReal;
//    private String urlCorta;
//    private String latitud;
//    private String longitud;
//    private String foto1;

    /* esta forma, con el @JsonProperty("campo del json"), podemos ponerlo tal cual viene en el
    archivo json, ya que lee todo el campo tal cual.
    */

    @JsonProperty("Código")
    private String codigo;

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Dirección")
    private String direccion;

    @JsonProperty("C.P.")
    private String cp;

    @JsonProperty("Municipio")
    private String municipio;

    @JsonProperty("Pedanía")
    private String pedania;

    @JsonProperty("Teléfono")
    private String telefono;

    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("URL Real")
    private String urlReal;

    @JsonProperty("URL Corta")
    private String urlCorta;

    @JsonProperty("Latitud")
    private String latitud;

    @JsonProperty("Longitud")
    private String longitud;

    @JsonProperty("Foto 1")
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
        return codigo + " - " + nombre + " - " +
                direccion + " - " + cp + " - " + municipio + " - " + telefono +
                " - " + "latitud " + latitud + " - " + "longitud " + longitud;
    }
}
