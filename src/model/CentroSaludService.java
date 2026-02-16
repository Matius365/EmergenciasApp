package model;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CentroSaludService {
    public static List<CentroSalud> cargarCentros(String ruta) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            List<CentroSalud> listaCentros = mapper.readValue(
                    new File(ruta),
                    new TypeReference<List<CentroSalud>>() {}
            );

            // Filtramos solo los que tienen coordenadas
            List<CentroSalud> filtrados = new ArrayList<>();

            for (CentroSalud c : listaCentros) {
                if (c.tieneCoordenadas()) {
                    filtrados.add(c);
                }
            }
            return listaCentros;


        } catch (Exception e) {
            System.out.println("Error cargando centros de salud: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
