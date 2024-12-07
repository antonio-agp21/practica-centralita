package es.uva.hilos;

public interface Cola<E> {
    void encolar(E x);

    E desencolar();

    E primero();

    boolean esVacia();
}
