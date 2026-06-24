package tads;

import grafos.contenedores.Lista2DLinkedL;
import servicio.Usuario;

public class ListaUsuarios extends Lista2DLinkedL {


    @Override
    public boolean iguales(Object a, Object b) {
        Usuario usuarioA = (Usuario) a;
        Usuario usuarioB = (Usuario) b;

        return (usuarioB.getId() == usuarioB.getId());
    }

    @Override
    public boolean esMayor(Object a, Object b) {
        Usuario usuarioA = (Usuario) a;
        Usuario usuarioB = (Usuario) b;

        return (usuarioB.getId() > usuarioB.getId());
    }

    @Override
    public boolean esMenor(Object a, Object b) {
        Usuario usuarioA = (Usuario) a;
        Usuario usuarioB = (Usuario) b;

        return (usuarioB.getId() < usuarioB.getId());
    }
}
