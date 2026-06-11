package servicio;

import grafos.grafoDirigido.GrafoDirigido;
import mapa.Interseccion;
import mapa.Mapa;

import java.util.ArrayList;
import java.util.Random;

public class Usuario {
    private int idUsuario;
    private ColaChoferes colaChoferes;
    private Interseccion origen, destino;
    private boolean abordado;
    private ArrayList<Double> listaETA;


    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
        this.colaChoferes = new ColaChoferes();
        this.listaETA = new ArrayList<>();
    }

    public void setAbordado(boolean abordado) {
        this.abordado = abordado;
    }

    public Interseccion getOrigen() {
        return origen;
    }

    public void setOrigen(Interseccion origen) {
        this.origen = origen;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void actualizarColaChoferes(ArrayList<Chofer> choferes, Mapa mapa) {
        GrafoDirigido grafoMapa = mapa.getGrafoPesos();

        grafoMapa.realizarDijkstra(this.origen.getID());

        for(Chofer chofer : choferes) {
            Interseccion posicionChofer = chofer.getPosicion();
            double costo = grafoMapa.getDistanciaDijkstra(posicionChofer.getID()); //para cada chofer, le calcula el ETA

            this.listaETA.add(costo);

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

    public void pedirUber() {
        Random random = new Random();
        boolean atentido = false;

        Chofer chofer = null;

        if(this.colaChoferes.estaVacia()) {
            System.out.println("Error: cola de choferes vacia");
        } else {
            while(!this.colaChoferes.estaVacia() && !atentido) {
                chofer = (Chofer) this.colaChoferes.sacar();

                // true: acepta / false: no acepto
                boolean acepta = random.nextBoolean();
                if(acepta && !chofer.estaOcupado()) {
                    chofer.setEstaOcupado(acepta);
                    atentido = true;
                }
            }
            if(!atentido && this.colaChoferes.estaVacia()) { // se recorrio la cola de choferes y no acepto ninguno
                System.out.println("no fuiste atendido");
            } else if (atentido && !this.colaChoferes.estaVacia()) { // fue atendido por un chofer y quedaron choferes en la cola
                System.out.println("fuiste atendido por el chofer " + chofer.getIdChofer());
                if(chofer != null) {
                    chofer.setDestino(this.origen); // actualiza el destino del uber para hacer el recorrido
                }
                borrarColaChoferes();
            }
        }

    }

    private void borrarColaChoferes() {
        this.colaChoferes.limpiar();
    }







}
