package model;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static void insertarPersona(Persona p) {

        String sql = "INSERT INTO Persona (nombre, apellido, telefono) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getTelefono());

            ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error al insertar persona: " + e.getMessage());
        }
    }

    //metodo para borrar un registro
    public static void eliminarPersona(int id) {

        String sql = "DELETE FROM Persona WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
