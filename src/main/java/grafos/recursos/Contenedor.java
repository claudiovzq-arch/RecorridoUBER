package grafos.recursos;

public interface Contenedor {
    public void meter(Object a);
    public Object sacar();
    public void limpiar();
    public boolean estaVacia();
}
