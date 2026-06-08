package servicio;

import grafos.grafoDirigido.GrafoDirigido;
import mapa.Interseccion;
import mapa.Mapa;

import java.util.ArrayList;
import java.util.Random;

public class Chofer {

    private int idChofer;
    private Interseccion posicion;


    public Chofer(int idChofer) {
        this.idChofer = idChofer;
    }


    public Interseccion getPosicion() {
        return this.posicion;
    }

    public void cargarPosicion(Mapa mapa) {
        GrafoDirigido grafoPesos = mapa.getGrafoPesos();

        Random random = new Random();

        int u = random.nextInt(grafoPesos.getOrden() + 1);

        this.posicion = mapa.getInterseccion(u);
    }

    public double calcularETA(Usuario usuario, Mapa mapa) {
        Interseccion posicionUsuario = usuario.getOrigen();
        int interseccionID = posicionUsuario.getID();

        GrafoDirigido grafoPesos = mapa.getGrafoPesos();

        return grafoPesos.getAristaFloyd(this.posicion.getID(), interseccionID);
    }







}
