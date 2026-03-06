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
                if (c != null
                    && c.getMunicipio() != null
                    && c.tieneCoordenadas()) {
                    filtrados.add(c);
                }
            }
            return filtrados;


        } catch (Exception e) {
            System.out.println("Error cargando centros de salud: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<CentroSalud> buscarPorMunicipio(String ruta, String municipioBuscado) {

        List<CentroSalud> centros = cargarCentros(ruta);
        List<CentroSalud> resultado = new ArrayList<>();

        for (CentroSalud c : centros) {

            if (c.getMunicipio().equalsIgnoreCase(municipioBuscado)) {
                resultado.add(c);
            }
        }

        return resultado;
    }
}
