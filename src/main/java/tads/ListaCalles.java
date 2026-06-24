package tads;

import grafos.contenedores.Lista1DLinkedL;
import mapa.Calle;

public class ListaCalles extends Lista1DLinkedL {

    @Override
    public boolean iguales(Object elementoL, Object elemento) {
        Calle calle1 = (Calle) elementoL;
        Calle calle2 = (Calle) elemento;

        return(calle1.getNombre().equals(calle2.getNombre()));
    }
}
