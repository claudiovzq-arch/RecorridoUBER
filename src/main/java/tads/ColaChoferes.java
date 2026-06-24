package tads;

import grafos.contenedores.ColaPrioridad;
import servicio.Chofer;

public class ColaChoferes extends ColaPrioridad {
    @Override
    public boolean esMenor(Object objA, Object objB) {
        Chofer choferA = (Chofer) objA;
        Chofer choferB = (Chofer) objB;
        return (choferA.getETA()<choferB.getETA());//choferA.calcularETA();
    }

    @Override
    public boolean esMayor(Object objA, Object objB) {
        Chofer choferA = (Chofer) objA;
        Chofer choferB = (Chofer) objB;
        return (choferA.getETA()>choferB.getETA());
    }

    @Override
    public boolean iguales(Object objA, Object objB) {
        Chofer choferA = (Chofer) objA;
        Chofer choferB = (Chofer) objB;
        return (choferA.getETA()==choferB.getETA());
    }
}
