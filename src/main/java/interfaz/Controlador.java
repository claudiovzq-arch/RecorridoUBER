package interfaz;

import grafos.contenedores.ColaLinkedList;
import grafos.contenedores.ColaSLinkedList;
import grafos.contenedores.PilaSLinkedList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import mapa.Interseccion;
import mapa.Mapa;
import servicio.*;

public class Controlador {

    private UberApp uberApp;

    public void setUberApp(UberApp uberApp) {
        this.uberApp = uberApp;
    }


    @FXML
    private Button btnGenUsuario;

    @FXML
    private Button btnGenChofer;

    @FXML
    private Button btnSimular;

    @FXML
    private Label lblEventos;

    @FXML
    private TextArea txtEventos;

    private void log(String mensaje) {
        txtEventos.appendText(mensaje + "\n");
    }

    @FXML
    private void generarUsuario() {
        uberApp.generarUsuario();
        System.out.println("usuario generado " + uberApp.getUltimoUsuario().getIdUsuario());
        log("Usuario " + uberApp.getUltimoUsuario().getIdUsuario() + " fue generado con exito.");
        //actualizarChoferes();
    }

    @FXML
    private void generarChofer() {
        uberApp.generarChofer();
        System.out.println("chofer generado " + uberApp.getUltimoChofer().getIdChofer());
        log("Chofer " + uberApp.getUltimoChofer().getIdChofer() + " fue generado con exito.");
    }


    //codigo para testear el movimiento de objetos en la interfaz.
    @FXML
    private Circle auto;

    @FXML
    private Circle destino;


    @FXML
    private void simularViaje() {
        //uberApp.simular();

        Usuario usuario = uberApp.getUltimoUsuario();
        Viaje viaje = usuario.pedirUber();
        log("El usuario " + usuario.getIdUsuario() + " pidio un uber en " + usuario.getOrigen().getDescripcion() + ".");
        if(viaje != null) {
            viaje.cargarCaminoDestino(uberApp.getMapa());
            viaje.cargarCaminoUsuario(uberApp.getMapa());
            viaje.setFinalizado(false);

            Chofer chofer = viaje.getChofer();
            log("El chofer " + chofer.getIdChofer() + " acepto el viaje del usuario " + usuario.getIdUsuario() + ".");
            comenzarRecogida(viaje, uberApp.getMapa(), chofer);

            if(chofer.getPosicion().equals(usuario.getOrigen())) { // Si el chofer llego a la posicion del usuario.
                log("El chofer " + chofer.getIdChofer() + " ha llegado a recoger al usuario " + usuario.getIdUsuario() + ".");
                comenzarViaje(viaje, uberApp.getMapa(), chofer);
                if(chofer.getPosicion().equals(viaje.getDestino())) {
                    log("El chofer " + chofer.getIdChofer() + " llevo a su destino " + viaje.getDestino().getDescripcion() + " al usuario " + usuario.getIdUsuario() + ".");
                    chofer.setEstaOcupado(false);
                }
            }

            /*int pasos = viaje.getCaminoAlUsuario().tamanio();
            double pasoVisual = (destino.getLayoutX() - auto.getLayoutX()) / (pasos - 1);
            System.out.println(pasoVisual);

            new Thread(() -> {
                chofer.comenzarRecogida(
                        viaje,
                        uberApp.getMapa(),
                        posicion -> {
                            Platform.runLater(() -> {
                                auto.setLayoutX(
                                        auto.getLayoutX() + pasoVisual
                                );
                            });
                        });
            }).start();*/

        } else {
            log("Ninguna unidad acepto el viaje del usuario " + usuario.getIdUsuario() + ".");
        }
    }

    /* comenzarRecogida(viaje, mapa, chofer)
     * Parámetros: "viaje" de tipo Viaje, "mapa" de tipo Mapa y "chofer" de tipo Chofer.
     * A partir de "viaje" obtiene el camino más barato para ir a recoger al usuario y con "mapa" va actualizando su posición.
     * Lo que hace el metodo es que el chofer primero se mueva desde la posición en la que agarró el viaje
     * hasta la posición del usuario al que le aceptó el viaje.
     * */

    private void comenzarRecogida(Viaje viaje, Mapa mapa, Chofer chofer) {
        PilaSLinkedList caminoRecogida = viaje.getCaminoAlUsuario();

        new Thread(() -> {

            log("El chofer " + chofer.getIdChofer() + " comenzo el recorrido para recoger al usuario " + viaje.getUsuario().getIdUsuario());
            while(!caminoRecogida.estaVacia()) {
                int interseccionID = (int) caminoRecogida.sacar();
                Interseccion nuevaPosicion = mapa.getInterseccion(interseccionID);
                chofer.mover(nuevaPosicion);


                //implementacion de mover el objeto en la interfaz


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    /* comenzarViaje(viaje, mapa, chofer)
     * Parámetros: "viaje" de tipo Viaje, "mapa" de tipo Mapa y "chofer" de tipo Chofer
     * A partir de "viaje" obtiene el camino más barato para ir al destino del usuario y con "mapa" va actualizando su posición.
     * El metodo va actualizando la posición del chofer, es decir, mueve al chofer y al usuario hasta el destino de este último.
     * */

    private void comenzarViaje(Viaje viaje, Mapa mapa, Chofer chofer) {
        PilaSLinkedList caminoViaje = viaje.getCaminoAlDestino();

        new Thread(() -> {

            log("El chofer " + chofer.getIdChofer() + " comenzo el recorrido para llevar al usuario " + viaje.getUsuario().getIdUsuario() + " a su destino " + viaje.getDestino().getDescripcion());
            while(!caminoViaje.estaVacia()) {
                int interseccionID = (int) caminoViaje.sacar();
                Interseccion nuevaPosicion = mapa.getInterseccion(interseccionID);
                chofer.mover(nuevaPosicion);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();





    }


    @FXML
    private Label lblUsuario;

    @FXML
    private ListView<String> listaChoferes;

    @FXML
    private void actualizarChoferes() {
        ColaChoferes colaChoferesUsuario = uberApp.getUltimoUsuario().getColaChoferes();
        ColaChoferes aux = new ColaChoferes();

        lblUsuario.setText("Usuario " + uberApp.getUltimoUsuario().getIdUsuario());

        ObservableList<String> items = FXCollections.observableArrayList();

        Chofer chofer = null;
        while(!colaChoferesUsuario.estaVacia()) {
            chofer = (Chofer) colaChoferesUsuario.sacar();
            items.add(chofer.getIdChofer() + " eta: " + chofer.getETA());
            aux.meter(chofer);
        }

        while(!aux.estaVacia()) {
            colaChoferesUsuario.meter(aux.sacar());
        }

        listaChoferes.setItems(items);
    }











}
