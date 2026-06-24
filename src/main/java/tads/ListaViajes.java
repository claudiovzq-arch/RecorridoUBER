package tads;

import grafos.contenedores.Lista2DLinkedL;
import servicio.Viaje;

public class ListaViajes extends Lista2DLinkedL {


    @Override
    public boolean iguales(Object a, Object b) {
        Viaje viajeA = (Viaje) a;
        Viaje viajeB = (Viaje) b;

        return viajeA.getCodigo() == viajeB.getCodigo();
    }

    @Override
    public boolean esMayor(Object a, Object b) {
        Viaje viajeA = (Viaje) a;
        Viaje viajeB = (Viaje) b;

        return viajeA.getCodigo() > viajeB.getCodigo();
    }

    @Override
    public boolean esMenor(Object a, Object b) {
        Viaje viajeA = (Viaje) a;
        Viaje viajeB = (Viaje) b;

        return viajeA.getCodigo() < viajeB.getCodigo();
    }
}
