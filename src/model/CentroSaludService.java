package model;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class CentroSaludService {
    public static List<CentroSalud> cargarCentros(String ruta) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(
                    new File(ruta),
                    new TypeReference<List<CentroSalud>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
