package grafos.recursos;

public interface OperacionesSLL {
    public void insertar(Object elemento);
    public void eliminar(Object elmento);
    public boolean estaVacia();
    public int tamano();
    public void reemplazar(Object elemento, int posicion);
    public int buscar(Object elemento);
}
