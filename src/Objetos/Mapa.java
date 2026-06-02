package Objetos;

import grafos.grafoDirigido.GrafoDirigido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.AreaAveragingScaleFilter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mapa {
    private GrafoDirigido conexiones, pesos;
    private Map<Integer, Calle> calles;
    private int cantCalles;


    public Mapa() {
        this.conexiones = new GrafoDirigido(0);
        this.conexiones.cargarGrafoVacio();
        this.pesos = new GrafoDirigido(0);
        this.pesos.cargarGrafoVacio();
        this.calles = new HashMap<>();
        this.cantCalles = 0;
    }


    private String normarlizarNombreCalle(String nombreCalle) {
        nombreCalle = Normalizer.normalize(nombreCalle, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return nombreCalle
                .toLowerCase()
                .replaceFirst("^(avenida|av\\.|pasaje|paseo|doctor|dr\\.)\\s+", "")
                .trim();
    }

    private ArrayList<String> getNodos(JSONObject jsonObject) {
        ArrayList<String> aux = new ArrayList<>();

        JSONArray coordinates = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
        for(int i = 0; i < coordinates.length(); i++) {
            String nodo = coordinates.getString(i);
            aux.add(nodo);
        }

        return aux;
    }

    public void cargarCalles(JSONObject jsonObject) {
        JSONArray featuresArray = jsonObject.getJSONArray("features");
        int contadorCalles = 0;
        for(int i = 0; i < featuresArray.length(); i++) {
            JSONObject featureIndex = featuresArray.getJSONObject(i);

            JSONObject featureProperties = featureIndex.getJSONObject("properties");
            String nombreCalle = featureProperties.optString("name", "S/N");
            String tipoCalle = featureProperties.optString("highway", "residential");

            nombreCalle = normarlizarNombreCalle(nombreCalle);


            ArrayList<String> nodos = getNodos(featureIndex);

            if(!calles.containsKey(nombreCalle)) { // si la calle todavia no se cargo.
                this.calles.put(contadorCalles, new Calle(nombreCalle,tipoCalle, nodos));
                contadorCalles++;
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
