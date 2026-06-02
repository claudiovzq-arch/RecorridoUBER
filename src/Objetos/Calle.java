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

    public void agregarNodos(ArrayList<String> nodos) {
        for(int i = 0; i < nodos.size(); i++) {
            String nodo = nodos.get(i);
            if(!this.nodos.contains(nodo)) {
                this.nodos.add(nodo);
            }
        }
        System.out.println("Nodos de la calle " + this.nombre + " actualizados.");
    }
}
