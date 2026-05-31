package mapas;


import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Lector {
    String contenido;
    JSONObject json;
    JSONArray features;
    Map<String, Integer> mapaEsquinas;

    public void setup() {
        try {
            contenido = Files.readString(Path.of("src/archivos/CentroyMacroSALTA.geojson"));
            json = new JSONObject(contenido);
            features = json.getJSONArray("features");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        cargarEsquinas();

    }

    private void cargarEsquinas() {
        this.mapaEsquinas = new HashMap<>();

        int contadorId = 0;

        for(int i = 0; i < features.length(); i++) {
            JSONObject geom = features.getJSONObject(i).getJSONObject("geometry");
            if(geom.getString("type").equals("LineString")) {
                JSONArray coords = geom.getJSONArray("coordinates");
                for(int j = 0; j < coords.length(); j++) {
                    String punto = coords.getJSONArray(j).toString();
                    if(!mapaEsquinas.containsKey(punto)) {
                        mapaEsquinas.put(punto, contadorId);
                        contadorId++;
                    }
                }
            }
        }

    }

    public Map<String, Integer> getMapaEsquinas() {
        return mapaEsquinas;
    }
}
