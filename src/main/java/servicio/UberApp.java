package servicio;

import interfaz.MapaView;
import mapa.Mapa;
import tads.ListaChoferes;
import tads.ListaUsuarios;

import java.util.ArrayList;

public class UberApp {
    //variables estaticas de la clase
    private static int choferesID = 0;
    private static int usuariosID = 0;

    //arraylist
    private ListaChoferes choferes;
    private ListaUsuarios usuarios;
    private Mapa mapa;
    private ArrayList<Viaje> viajes;
    //---------------------------------------------
    private MapaView vistaMapa;
    //---------------------------------------------
    public UberApp (Mapa mapa) {
        this.mapa = mapa;
        this.choferes = new ListaChoferes();
        this.usuarios = new ListaUsuarios();
        this.viajes = new ArrayList<>();
        //-------------------------------------------
        this.vistaMapa = new MapaView(mapa);
        //-------------------------------------------
    }
    //---------------------------------------------
    public MapaView getVistaMapa(){ return this.vistaMapa; }
    //---------------------------------------------
    public Mapa getMapa() {
        return this.mapa;
    }

    public void generarUsuario() {
        Usuario nuevoUsuario = new Usuario(usuariosID);
        nuevoUsuario.cargarPosiciones(this.mapa);
        this.usuarios.insertar(nuevoUsuario);
        //----------------------------------------
        vistaMapa.agrgarUsuario(nuevoUsuario);
        //----------------------------------------
        usuariosID++;
    }

    public ListaChoferes getChoferesDisponibles() {
        ListaChoferes disponibles = new ListaChoferes();
        for(int i = 0; i < this.choferes.tamanio(); i++) {
            Chofer chofer = (Chofer) this.choferes.devolver(i);
            if(!chofer.estaOcupado()) {
                disponibles.insertar(chofer);
            }
        }

        return disponibles;
    }

    public void generarChofer() {
        Chofer nuevoChofer = new Chofer(choferesID);
        nuevoChofer.cargarPosicion(this.mapa);
        this.choferes.insertar(nuevoChofer);
        //----------------------------------------
        vistaMapa.agregarCoche(nuevoChofer);
        //----------------------------------------
        choferesID++;
    }


    public void addNuevoViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }

    public Viaje getUltimoViaje() {
        return this.viajes.get(this.viajes.size() - 1);
    }

    public Usuario getUltimoUsuario() {
        return (Usuario) this.usuarios.devolver(this.usuarios.tamanio() - 1);
    }

    public Chofer getUltimoChofer() {
        return (Chofer) this.choferes.devolver(this.choferes.tamanio() - 1);
    }



}
