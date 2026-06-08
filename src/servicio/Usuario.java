package servicio;

import grafos.contenedores.ColaPrioridad;
import mapa.Interseccion;
import mapa.Mapa;

public class Usuario {
    private int idUsuario;
    private ColaChoferes colaChoferes;
    private Interseccion origen, destino;

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
        this.colaChoferes = new ColaChoferes(this);
    }

    public void cargarPosiciones(Mapa mapa) {

    }

    public Interseccion getOrigen() {
        return origen;
    }

    public void setOrigen(Interseccion origen) {
        this.origen = origen;
    }

    public Interseccion getDestino() {
        return destino;
    }

    public void setDestino(Interseccion destino) {
        this.destino = destino;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
