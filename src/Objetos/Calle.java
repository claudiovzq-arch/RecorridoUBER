package Objetos;

import java.util.ArrayList;

public class Calle {
    private String nombre, tipo;
    private ArrayList<String> nodos;

    public Calle(String nombre, String tipo, ArrayList<String> nodos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nodos = nodos;
    }

    public ArrayList<String> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<String> nodos) {
        this.nodos = nodos;
    }
}
