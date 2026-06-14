package servicio;

import grafos.grafoDirigido.GrafoDirigido;
import mapa.Interseccion;
import mapa.Mapa;

import java.util.Random;




public class Chofer {

    private int idChofer;
    private Interseccion posicion;
    private boolean estaOcupado;
    private double ETA;


    public Chofer(int idChofer) {
        this.idChofer = idChofer;
        this.estaOcupado = false;
        this.ETA = -1;
    }

    public boolean decidirAceptarViaje() {
        Random random = new Random();

        boolean aceptarViaje = random.nextInt(10) < 8;
        return aceptarViaje;
    }



    public int getIdChofer() {
        return this.idChofer;
    }

    public double getETA() {
        return ETA;
    }

    public void setETA(double ETA) {
        this.ETA = ETA;
    }

    public void setEstaOcupado(boolean estaOcupado) {
        this.estaOcupado = estaOcupado;
    }

    public void setPosicion(Interseccion posicion) {
        this.posicion = posicion;
    }

    public boolean estaOcupado() {
        return this.estaOcupado;
    }

    public Interseccion getPosicion() {
        return this.posicion;
    }

    //pasarle como atribulo solo la lista de intersecciones del mapa
    public void cargarPosicion(Mapa mapa) {
        GrafoDirigido grafoPesos = mapa.getGrafoPesos();

        Random random = new Random();

        int u = random.nextInt(grafoPesos.getOrden() + 1);

        this.posicion = mapa.getInterseccion(u);
    }

    public void mover(Interseccion nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }


}
