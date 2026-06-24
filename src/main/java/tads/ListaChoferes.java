package tads;

import grafos.contenedores.Lista2DLinkedL;
import servicio.Chofer;

public class ListaChoferes extends Lista2DLinkedL {


    @Override
    public boolean iguales(Object a, Object b) {
        Chofer choferA = (Chofer) a;
        Chofer choferB = (Chofer) b;

        return (choferA.getIdChofer() == choferB.getIdChofer());
    }

    @Override
    public boolean esMayor(Object a, Object b) {
        Chofer choferA = (Chofer) a;
        Chofer choferB = (Chofer) b;

        return (choferA.getIdChofer() > choferB.getIdChofer());
    }

    @Override
    public boolean esMenor(Object a, Object b) {
        Chofer choferA = (Chofer) a;
        Chofer choferB = (Chofer) b;

        return (choferA.getIdChofer() < choferB.getIdChofer());
    }
}
