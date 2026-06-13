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
import javafx.scene.shape.Circle;
import javafx.util.Duration;
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
    private void generarUsuario() {
        uberApp.generarUsuario();
        System.out.println("usuario generado " + uberApp.getUltimoUsuario().getIdUsuario());
        //actualizarChoferes();
    }

    @FXML
    private void generarChofer() {
        uberApp.generarChofer();
        System.out.println("chofer generado " + uberApp.getUltimoChofer().getIdChofer());
    }


    //codigo para testear el movimiento de objetos en la interfaz.
    /*@FXML
    private Circle auto;

    @FXML
    private Circle destino;


    @FXML
    private void simularViaje() {
        //uberApp.simular();

        Usuario usuario = uberApp.getUltimoUsuario();
        Viaje viaje = usuario.pedirUber();
        viaje.cargarCaminoDestino(uberApp.getMapa());
        viaje.cargarCaminoUsuario(uberApp.getMapa());

        Chofer chofer = viaje.getChofer();

        int pasos = viaje.getCaminoAlUsuario().tamanio();
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

        }).start();


    }*/


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
