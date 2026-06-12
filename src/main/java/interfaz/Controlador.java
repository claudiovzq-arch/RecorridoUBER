package interfaz;

import grafos.contenedores.ColaLinkedList;
import grafos.contenedores.ColaSLinkedList;
import grafos.contenedores.PilaSLinkedList;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import servicio.Chofer;
import servicio.ColaChoferes;
import servicio.UberApp;
import servicio.Viaje;

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
        actualizarChoferes();
    }

    @FXML
    private void generarChofer() {
        uberApp.generarChofer();
        System.out.println("chofer generado " + uberApp.getUltimoChofer().getIdChofer());
    }

    @FXML
    private void simularViaje() {
        uberApp.simular();
        moverAuto(uberApp.getUltimoViaje());
        Chofer chofer = uberApp.getUltimoViaje().getChofer();

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


    @FXML
    private Circle auto;

    @FXML
    private Circle destino;

    private void moverAuto(Viaje viaje) {
        PilaSLinkedList caminoAlDestino = viaje.getCaminoAlDestino();
        int cantidadIntersecciones = caminoAlDestino.tamanio();

        double segundos = cantidadIntersecciones - 1;

        double desplazamientoX =
                destino.getCenterX() - auto.getCenterX();

        double desplazamientoY =
                destino.getCenterY() - auto.getCenterY();

        TranslateTransition movimiento =
                new TranslateTransition(
                        Duration.seconds(segundos),
                        auto);

        movimiento.setToX(desplazamientoX);
        movimiento.setToY(desplazamientoY);

        movimiento.play();



    }



}
