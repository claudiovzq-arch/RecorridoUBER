package servicio;

import grafos.contenedores.PilaSLinkedList;
import grafos.grafoDirigido.GrafoDirigido;
import mapa.Interseccion;
import mapa.Mapa;
import tads.ColaChoferes;
import tads.ListaChoferes;

import java.util.Random;

public class Usuario {
    private int id;
    private ColaChoferes colaChoferes;
    private Interseccion origen, destino;


    public Usuario(int id) {
        this.id = id;
        this.colaChoferes = new ColaChoferes();

    }

    public ColaChoferes getColaChoferes() {
        return this.colaChoferes;
    }

    public Interseccion getOrigen() {
        return origen;
    }

    public void setOrigen(Interseccion origen) {
        this.origen = origen;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /* actualizarColaChoferes(ArrayList<Chofer> choferes, Mapa mapa)
    * Parámetros: "choferes" de tipo arraylist, "mapa" de tipo Mapa.
    * Este metodo permite cargar la cola de choferes de un usuario, en el se realiza el algoritmo de Dijkstra
    * desde la posicion del usuario hacia el resto de nodos del grafo del mapa.
    * Luego de realizar el algoritmo antes mencionado, obtiene el costo del camino desde la posicion del usuario
    * hasta las respectivas posiciones del chofer. Actualiza el ETA de todos los choferes antes de meterlos en la cola de choferes.
    * */
    private void actualizarColaChoferes(ListaChoferes choferes, Mapa mapa) {
        GrafoDirigido grafoMapa = mapa.getGrafoPesos();
        grafoMapa.realizarDijkstra(this.origen.getID());

        this.colaChoferes.limpiar();
        for(int i = 0; i < choferes.tamanio(); i++) {

            //grafoMapa.realizarDijkstra(chofer.getPosicion().getID());
            Chofer chofer = (Chofer) choferes.devolver(i);

            Interseccion posicionChofer = chofer.getPosicion();
            double costo = grafoMapa.getDistanciaDijkstra(posicionChofer.getID()); //para cada chofer, le calcula el ETA

            PilaSLinkedList camino = grafoMapa.retornaCaminoDijkstra(this.origen.getID(), posicionChofer.getID());

            //ETA = (costo * cant de nodos del camino) / 60
            double eta = (costo * camino.tamanio()) / 60;

            chofer.setETA(eta);

            this.colaChoferes.meter(chofer);
        }
    }

    public void cargarPosiciones(Mapa mapa) {
        GrafoDirigido grafoPesos = mapa.getGrafoPesos();

        Random random = new Random();

        int origen = random.nextInt(grafoPesos.getOrden() + 1);
        int destino = random.nextInt(grafoPesos.getOrden() + 1);
        this.origen = mapa.getInterseccion(origen);

        if(origen != destino) {
            this.destino = mapa.getInterseccion(destino);
        } else {
            while(origen == destino) {
                destino = random.nextInt(grafoPesos.getOrden() + 1);
            }
            this.destino = mapa.getInterseccion(destino);
        }
    }

    public Viaje pedirUber(ListaChoferes choferes, Mapa mapa) {
        Viaje viaje = null;

        boolean atendido = false;
        actualizarColaChoferes(choferes, mapa);
        viaje = new Viaje(this, null, origen, destino);

        return viaje;
    }









}
