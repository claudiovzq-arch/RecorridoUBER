package Objetos;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Interseccion {
    private int ID;
    private ArrayList<Calle> calles;
    private String coordenada;

    public Interseccion(String coordenada) {
        this.calles = new ArrayList<>();
        this.coordenada = coordenada;
    }

    public void addCalle(Calle calle) {
        this.calles.add(calle);
    }





}
