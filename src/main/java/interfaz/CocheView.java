package interfaz;

import javafx.animation.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mapa.ConversorCoordenadas;
import mapa.Coordenada;
import servicio.Chofer;

import java.util.ArrayList;

public class CocheView extends StackPane {
    Chofer chofer;
    ConversorCoordenadas conversor;
    Rectangle view;
    private Text id;

    public CocheView(Chofer chofer) {
        this(chofer, null);
    }

    public CocheView(Chofer chofer, ConversorCoordenadas conversor) {
        this.chofer = chofer;
        this.conversor = conversor;

        this.view = new Rectangle(12, 10);
        view.setArcWidth(5);
        view.setArcHeight(5);
        view.setFill(Color.GREEN);

        id = new Text(String.valueOf(chofer.getIdChofer()));
        id.setFill(Color.WHITE);

        getChildren().addAll(view, id);

        if (conversor != null) {
            setLayoutX(conversor.toX(chofer.getPosicion().getCoordenada().getLongitud()) - 6);
            setLayoutY(conversor.toY(chofer.getPosicion().getCoordenada().getLatitud()) - 8);
        }
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void actualizar(){
        if (chofer.estaOcupado()) {
            view.setFill(Color.RED);
        } else {
            view.setFill(Color.GREEN);
        }

        Coordenada coord = chofer.getPosicion().getCoordenada();

        double nuevoX = conversor.toX(coord.getLongitud()) - 6;
        double nuevoY = conversor.toY(coord.getLatitud()) - 8;

        Timeline animacion = new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        new KeyValue(layoutXProperty(), nuevoX),
                        new KeyValue(layoutYProperty(), nuevoY)
                )
        );

        animacion.play();
    }


    public void mover(ArrayList<Coordenada> recorrido, Runnable alTerminar) {
        Path path = new Path();
        if(recorrido.size()!=0){
            Coordenada coord = recorrido.get(0);
            path.getElements().add(new MoveTo(conversor.toX(coord.getLongitud()), conversor.toY(coord.getLatitud())));
            for(int i=1; i<recorrido.size(); i++){
                coord = recorrido.get(i);
                path.getElements().add(new LineTo(conversor.toX(coord.getLongitud()), conversor.toY(coord.getLatitud())));
            }
        }
        PathTransition movimiento = new PathTransition(Duration.seconds(recorrido.size()/2.0), path, this);
        movimiento.setOnFinished(e -> {
            if (alTerminar != null) {
                alTerminar.run();
            }
        });
        movimiento.play();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CocheView){
            return this.chofer.equals(((CocheView)obj).getChofer());
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.chofer.hashCode();
    }
}
