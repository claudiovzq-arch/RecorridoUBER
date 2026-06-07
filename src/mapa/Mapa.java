package mapa;

import grafos.grafoDirigido.GrafoDirigido;
import org.json.JSONArray;
import org.json.JSONObject;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mapa {
    private GrafoDirigido grafoPesos;
    private Map<String, Calle> calles;
    private Map<Integer, Interseccion> intersecciones;

    public Mapa(JSONObject jsonObject) {
        this.intersecciones = new HashMap<>();
        this.calles = new HashMap<>();

        cargar(jsonObject);

        this.grafoPesos = new GrafoDirigido(this.intersecciones.size());
        cargarGrafoPesos(jsonObject);
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
        if(jsonObject.getString("type").equals("LineString")) {
            JSONArray coordinates = jsonObject.getJSONArray("coordinates");
            for(int i = 0; i < coordinates.length(); i++) {
                String nodo = coordinates.getJSONArray(i).toString();
                aux.add(nodo);
            }
        }
        return aux;
    }


    // metodo para cargar el  map de calles <NombreCalle, Calle> y el map de intersecciones

    private void cargar(JSONObject jsonObject) {
        JSONArray features = jsonObject.getJSONArray("features");
        JSONObject feature;

        Map<Integer, Interseccion> aux = new HashMap<>();

        int contadorCalles = 0;
        int contadorCoordenadas = 0;

        for (int i = 0; i < features.length(); i++) {
            feature = features.getJSONObject(i);
            JSONObject geom = feature.getJSONObject("geometry");
            JSONObject properties = feature.getJSONObject("properties");
            String nombreCalle = properties.optString("name", "s/n");

            nombreCalle = normarlizarNombreCalle(nombreCalle);

            if (geom.getString("type").equals("LineString") && nombreCalle != null) { //Verificacion de que el feature sea lineString y la calle tenga nombre
                JSONArray coords = geom.getJSONArray("coordinates");
                for (int j = 0; j < coords.length(); j++) {
                    Coordenada coord = new Coordenada((coords.getJSONArray(j)).getDouble(0), (coords.getJSONArray(j)).getDouble(1));
                    String tipoCalle = properties.optString("highway", "residential");

                    // Lógica de DOBLE MANO:
                    // Si 'oneway' es 'yes', es mano única.
                    // Si es 'no' o no existe el tag, es doble mano.

                    boolean esManoUnica = properties.optString("oneway", "no").equals("yes");

                    Calle calle;

                    Interseccion interseccion = new Interseccion(coord);

                    if (!this.calles.containsKey(nombreCalle)) { // carga la cantidad de calles unicas
                        calle = new Calle(contadorCalles, nombreCalle, tipoCalle, esManoUnica); // si el mapa de calles no contiene una calle asociada a 'nombreCalle', crea una nueva
                        this.calles.put(nombreCalle, calle);
                        contadorCalles++;
                    } else {
                        calle = this.calles.get(nombreCalle); // si el mapa de calles  contiene una calle asociada a 'nombreCalle', la obtiene a partir de este ultimo
                    }

                    int pos = buscarCoordenada(aux, interseccion);


                    if (pos == -1) { // no existe una coordenada
                        interseccion.setID(contadorCoordenadas);
                        interseccion.addCalle(calle);
                        aux.put(contadorCoordenadas, interseccion);
                        contadorCoordenadas++;
                    } else {
                        aux.get(pos).addCalle(calle);
                    }

                }
            }
        }

        depurarIntersecciones(aux); // depura las intesercciones con menos de 2 calles asociadas y las carga en el mapa 'intersecciones'

    }

    private void depurarIntersecciones(Map<Integer, Interseccion> mapa) {

        int indiceIntersecciones = 0;

        for(Integer i : mapa.keySet()) {
            Interseccion interseccion = mapa.get(i);
            if(interseccion.getCantCalles() > 1) {
                interseccion.setID(indiceIntersecciones);
                this.intersecciones.put(indiceIntersecciones, interseccion);
                indiceIntersecciones++;
            }
        }

    }


    private int buscarCoordenada(Map<Integer, Interseccion> mapa, Interseccion busc){
        boolean detectado = false;
        int pos=0;

        for(Integer i : mapa.keySet()) {
            Interseccion interseccion = mapa.get(i);
            double dlon = interseccion.getCoordenada().getLongitud() - busc.getCoordenada().getLongitud();
            double dlat = interseccion.getCoordenada().getLatitud() - busc.getCoordenada().getLatitud();

            if(dlon == 0 && dlat == 0) {
                detectado=true;
                pos = i;
            }
        }

        if(detectado) {
            return pos;
        }

        return -1;

    }


    public void mostrarCalles() {
        for(String nombreCalle : this.calles.keySet()) {
            Calle calle = this.calles.get(nombreCalle);
            System.out.println(calle.getId() + " Calle: " + calle.getNombre() + ". Tipo: " + calle.getTipo());
        }

        System.out.println(this.calles.size());
    }

    public void mostrarIntersecciones() {
        for(int i = 0; i < this.intersecciones.size(); i++) {
            Interseccion interseccion = this.intersecciones.get(i);
            System.out.println(interseccion.toString());
        }
        System.out.println(this.intersecciones.size());
    }







    private void cargarGrafoPesos(JSONObject jsonObject) {
        this.grafoPesos.cargarGrafoVacio();

        JSONArray features = jsonObject.getJSONArray("features");




        for(int i = 0; i < features.length(); i++) {
            JSONObject f = features.getJSONObject(i); // obtiene la calle
            JSONArray coords = f.getJSONObject("geometry").getJSONArray("coordinates"); // obtiene las coordenadas de la calle

            int index = 0, u = -1, v = -1;
            Coordenada coordIni = null, coordFin = null;
            boolean encontroU = false;
            boolean encontroV = false;



            while(index<coords.length() && !encontroU) {
                coordIni = new Coordenada((coords.getJSONArray(index)).getDouble(0), (coords.getJSONArray(index)).getDouble(1));
                for(int j = 0; j < this.intersecciones.size(); j++) {
                    Interseccion interU = this.intersecciones.get(j);
                    Coordenada coordU = interU.getCoordenada();
                    if(coordU.equals(coordIni)) {
                        u = interU.getID();
                        encontroU = true;
                        break;
                    }
                }
                index++;
            }
            while(index<coords.length() && !encontroV) {
                coordFin = new Coordenada((coords.getJSONArray(index)).getDouble(0), (coords.getJSONArray(index)).getDouble(1));
                for(int j = 0; j < this.intersecciones.size(); j++) {
                    Interseccion interV = this.intersecciones.get(j);
                    Coordenada coordV = interV.getCoordenada();
                    if(coordV.equals(coordFin)) {
                        v = interV.getID();
                        encontroV = true;
                        break;
                    }
                }
                index++;
            }



            if(encontroU && encontroV) {
                //System.out.println(encontroU + " " + encontroV);
                Interseccion intIni=this.intersecciones.get(u), intFin=this.intersecciones.get(v);
                Calle calle = intIni.calleCompartida(intFin);

                double costo = coordIni.haversine(coordFin) / calle.getVelocidad();

                this.grafoPesos.actualizarArista(costo, u, v);

                if(!calle.isManoUnica()) {
                    this.grafoPesos.actualizarArista(costo, v, u);
                }

            }



        }




    }




    public void mostrarMatrizDePesos() {
        //this.grafoPesos.muestraGrafo();

        for(int i = 0; i < this.grafoPesos.getOrden(); i++) {

            for(int j = 0; j < this.grafoPesos.getOrden(); j++) {

                double costo = this.grafoPesos.getArista(i,j);


                if(i != j) {


                    if(costo != GrafoDirigido.getInfinito()) {

                        Interseccion origen = this.intersecciones.get(i);
                        Interseccion destino = this.intersecciones.get(j);


                        System.out.println(origen.getDescripcion() + " -> " + destino.getDescripcion() + " costo: " + costo);

                    }

                }

            }
        }

    }


    public void simularRecorrido(int origen, int destino) { // simulacion por IDs de intersecciones
        this.grafoPesos.muestraFloyd();

        this.grafoPesos.muestraCaminoFloyd(origen, destino);

        ArrayList<Integer> camino = this.grafoPesos.getCaminoFloyd(origen, destino);

        for(int i = 0; i < camino.size(); i++) {
            int idInterseccion = camino.get(i);
            System.out.println(this.intersecciones.get(idInterseccion).toString());
        }



    }
}
