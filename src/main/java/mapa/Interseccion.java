package mapa;

import tads.ListaCalles;

public class Interseccion {
    private int ID;
    private ListaCalles calles;
    private Coordenada coordenada;

    public Interseccion(Coordenada coordenada) {
        this.calles = new ListaCalles();

        this.coordenada = coordenada;
    }

    public ListaCalles getCalles() {
        return this.calles;
    }



    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return this.ID;
    }

    public int getCantCalles() {
        return this.calles.tamanio();
    }

    public void addCalle(Calle calle) {
        if(this.calles.estaVacia()) {
            this.calles.insertar(calle, 0);
        } else {
            this.calles.insertar(calle, this.calles.tamanio());
        }
    }

    /*public Calle calleCompartida(Interseccion inter2) {
        Calle c=null;
        for(int i=0; i < this.calles.size(); i++) {
            Calle calle1 = this.calles.get(i);
            ArrayList<Calle> inter2Calles = inter2.getCalles();
            for(int j=0; j < inter2Calles.size(); j++) {
                Calle calle2 = inter2Calles.get(j);
                if(calle1.equals(calle2) ) {
                    c=calle1;
                }
            }
        }
        return c;
    }*/

    public Coordenada getCoordenada() {
        return this.coordenada;
    }

    @Override
    public String toString() {
        String cad = "";

        cad += "Interseccion " + this.ID + " " + this.coordenada + " entre " + getStringCalles() + "cant " + this.calles.tamanio();

        return cad;
    }

    public String getDescripcion() {

        StringBuilder sb = new StringBuilder("(");

        for(int i = 0; i < calles.tamanio(); i++) {
            Calle calle = (Calle) calles.devolver(i);

            sb.append(calle.getNombre());

            if(i < calles.tamanio()-1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    private String getStringCalles() {
        String nombreCalles = "";
        for(int i = 0; i < this.calles.tamanio(); i++) {
            nombreCalles += ((Calle)this.calles.devolver(i)).getNombre() + " / ";
        }
        return nombreCalles;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Interseccion)) return false;
        else {
            Interseccion int2 = (Interseccion) obj;
            return (this.coordenada.equals(int2.getCoordenada()));
        }
    }
}
