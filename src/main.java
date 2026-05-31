import mapas.Lector;

import java.util.Map;

public class main {
    public static void main(String[] args) {
        Lector mapas = new Lector();

        mapas.setup();

        Map<String, Integer> aux = mapas.getMapaEsquinas();

        System.out.println(aux.size());


        /*for(String key : aux.keySet()) {
            System.out.println(aux.get(key));
        }*/


    }
}
