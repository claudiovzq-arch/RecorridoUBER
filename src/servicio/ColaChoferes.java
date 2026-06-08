package servicio;

import grafos.contenedores.ColaPrioridad;

public class ColaChoferes extends ColaPrioridad {

    private Usuario usuario;


    public ColaChoferes(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean esMenor(Object objA, Object objB) {
        Chofer choferA = (Chofer) objA;
        Chofer choferB = (Chofer) objB;
        return choferA.calcularETA();
    }

    @Override
    public boolean esMayor(Object objA, Object objB) {
        return false;
    }

    @Override
    public boolean iguales(Object objA, Object objB) {
        return false;
    }
}
