package servicio;

import grafos.contenedores.PilaSLinkedList;
import grafos.grafoDirigido.GrafoDirigido;
import mapa.Interseccion;
import mapa.Mapa;

public class Viaje {
    private Usuario usuario;
    private Chofer chofer;
    private Interseccion origen, destino;
    private boolean estado;
    private PilaSLinkedList caminoAlUsuario, caminoAlDestino;


    public Viaje(Usuario usuario, Chofer chofer) {
        this.usuario = usuario;
        this.chofer = chofer;
        this.caminoAlDestino = this.caminoAlUsuario = new PilaSLinkedList();
    }

    public Viaje(Usuario usuario, Chofer chofer, Interseccion origen, Interseccion destino) {
        this(usuario, chofer);
        this.origen = origen;
        this.destino = destino;
        this.estado = false;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void cargarCaminoUsuario(Mapa mapa) {
        PilaSLinkedList aux = new PilaSLinkedList();

        mapa.


    }

    public void cargarCaminoDestino(Mapa mapa) {
        GrafoDirigido grafoMapa = mapa.getGrafoPesos();

        /* Como el mapa ya hizo el el alg. de Dijkstra desde la posición del usuario cuando
        * este pidió el uber, entonces no hace falta volver a realizarlo y se aprovecha que desde
        * esa posición ya se conocen los caminos a el resto de nodos del mapa y se cargan en el
        * camino al destino.
        * */

        this.caminoAlDestino = grafoMapa.retornaCaminoDijkstra(this.origen.getID(), this.destino.getID());

    }



}
