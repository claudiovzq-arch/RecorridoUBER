package grafos.contenedores;


import grafos.recursos.NodoDoble;
import grafos.recursos.OperacionesCL4;

public abstract class Lista2DLinkedL extends Lista1DLinkedL implements OperacionesCL4 {

    public int buscar(Object elemento) {
        if (estaVacia()) {
            return -1;
        }

        if (iguales(elemento, this.frenteL.getNodoInfo())) {
            return 0;
        }

        if (iguales(elemento, this.finalL.getNodoInfo())) {
            return tamanio() - 1;
        }

        NodoDoble temp = this.frenteL.getNextNodo();
        int contador = 1;

        while (temp != null && esMayor(elemento, temp.getNodoInfo())) {
            temp = temp.getNextNodo();
            contador++;
        }

        if (temp != null && iguales(elemento, temp.getNodoInfo())) {
            return contador;
        }

        return -1; //no lo encontro
    }

    /*public int buscar(Object elemento) {
        int posicion = -1;
        if(estaVacia()) {
            System.out.println("Error: lista vacia");
        } else {
            if(iguales(elemento, this.frenteL.getNodoInfo())) {
                posicion = 0;
            } else {
                if(iguales(elemento, this.finalL.getNodoInfo())) {
                    posicion = tamanio() - 1;
                } else {
                    NodoDoble temp = this.frenteL.getNextNodo();
                    int contador = 1;
                    while(temp != null && esMayor(elemento, temp.getNodoInfo())) {
                        temp = temp.getNextNodo();
                        contador++;
                    }

                    if(temp != null && iguales(elemento, temp.getNodoInfo())) {
                        posicion = contador;
                    }

                }
            }
        }
        return posicion;
    }*/

    @Override
    public void insertar(Object elemento) {
        NodoDoble nodo;

        if(estaVacia()) {
            this.frenteL = this.finalL = new NodoDoble(elemento);
        } else {
            if(esMenor(elemento, this.frenteL.getNodoInfo())) { // insercion al frente
                this.frenteL = new NodoDoble(elemento, null, this.frenteL);
                this.frenteL.getNextNodo().setPrevNodo(this.frenteL);
            } else {
                if(esMayor(elemento, this.finalL.getNodoInfo()) || iguales(elemento, this.finalL.getNodoInfo())) { // insercion al final
                    this.finalL = new NodoDoble(elemento, this.finalL, null);
                    this.finalL.getPrevNodo().setNextNodo(this.finalL);
                } else { // insercion al medio
                    NodoDoble temp = this.frenteL;
                    boolean flag = false;
                    while(temp.getNextNodo() != null && !flag) {
                        if(esMayor(elemento, temp.getNextNodo().getNodoInfo()) || iguales(elemento, temp.getNextNodo().getNodoInfo())) {
                            temp = temp.getNextNodo();
                        } else {
                            flag = true;
                        }
                    }

                    nodo = new NodoDoble(elemento, temp, temp.getNextNodo());
                    temp.getNextNodo().setPrevNodo(nodo);
                    temp.setNextNodo(nodo);
                 }
            }
        }

        this.ultimo++;
    }


    @Override
    public abstract boolean iguales(Object a, Object b);
    public abstract boolean esMayor(Object a, Object b);
    public abstract boolean esMenor(Object a, Object b);

}
