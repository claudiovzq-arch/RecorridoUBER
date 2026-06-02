package Objetos;

import grafos.grafoDirigido.GrafoDirigido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mapa {
    private GrafoDirigido conexiones, pesos;
    private Map<Integer, Calle> calles;


    public Mapa() {
        this.conexiones = new GrafoDirigido(0);
        this.conexiones.cargarGrafoVacio();
        this.pesos = new GrafoDirigido(0);
        this.pesos.cargarGrafoVacio();
        this.calles = new HashMap<>();
    }


    private String normarlizarNombreCalle(String nombreCalle) {
        nombreCalle = Normalizer.normalize(nombreCalle, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return nombreCalle
                .toLowerCase()
                .replaceFirst("^(avenida|av\\.|pasaje|paseo|doctor|dr\\.)\\s+", "")
                .trim();
    }

    public void cargarCalles(JSONObject jsonObject) {
        JSONArray featuresArray = jsonObject.getJSONArray("features");
        for(int i = 0; i < featuresArray.length(); i++) {
            JSONObject featureIndex = featuresArray.getJSONObject(i);
            JSONObject featureProperties = featureIndex.getJSONObject("properties");
            String nombreCalle = featureProperties.optString("name", "S/N");

            nombreCalle = normarlizarNombreCalle(nombreCalle);


            JSONArray nodos = featureIndex.getJSONObject("geometry").getJSONArray("coordinates");

            if(!calles.containsKey(nombreCalle)) { // si la calle todavia no se cargo.
                this.calles.put(nombreCalle, nodos);
            } else {
                JSONArray aux = calles.get(nombreCalle); // si se detecto de nuevo una calle existente, carga de nuevo el array agregando los nuevos nodos
                for(int j = 0; j < nodos.length(); j++) {
                    aux.put(nodos.get(j));
                }
                this.calles.put(nombreCalle, aux);
            }


        }




    }



}
