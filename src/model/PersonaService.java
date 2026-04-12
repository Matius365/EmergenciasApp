package model;

import database.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonaService {
    public static List<Persona> obtenerPersonas() {

        List<Persona> lista = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Persona")) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono")
                );
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener personas: " + e.getMessage());
        }

        return lista;
    }
}
