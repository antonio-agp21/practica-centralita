package es.uva.hilos;

public class ColaLlamadas implements Cola<Llamada> {

    protected Llamada array[];
    protected int fin, primero, tallaActual;
    protected static final int cap = 200;

    public ColaLlamadas() {
        array = new Llamada[cap];
        tallaActual = 0;
        primero = 0;
        fin = -1;
    }

    @Override
    public void encolar(Llamada x) {
        if (tallaActual == array.length) {
            duplicarArray();
        }
        fin = incrementa(fin);
        array[fin] = x;
        tallaActual++;
    }

    @Override
    public Llamada desencolar() {
        Llamada elPrimero = array[primero];
        if (elPrimero != null) {
            primero = incrementa(primero);
            tallaActual--;
        }
        return elPrimero;
    }

    @Override
    public Llamada primero() {
        return array[primero];
    }

    @Override
    public boolean esVacia() {
        return (tallaActual == 0);
    }

    private int incrementa(int indice) {
        if (++indice == array.length) {
            indice = 0;
        }
        return indice;
    }

    @Override
    public String toString() {
        String res = "";
        int aux = primero;
        for (int i = 0; i < tallaActual; i++, aux = incrementa(aux)) {
            res += array[aux] + " ";
        }
        return res;
    }

    private void duplicarArray() {
        Llamada nuevo[] = new Llamada[array.length * 2];
        for (int i = 0; i < tallaActual; i++, primero = incrementa(primero)) {
            nuevo[i] = array[primero];
        }
        array = nuevo;
        primero = 0;
        fin = tallaActual - 1;
    }

    public void vaciarCola() {
        for (int i = primero; i != obtenerLimite(); i = incrementa(i)) {
            array[i] = null;
        }
        primero = 0;
        fin = -1;
        tallaActual = 0;
    }

    private int obtenerLimite() {
        if (fin + 1 != array.length) {
            return fin + 1;
        } else {
            return 0;
        }
    }

    public void mostrarCola(int num) {
        if (num != 0) {
            System.out.println("COLA\n");
        }
        if (array[primero] != null) {
            for (int i = primero; i != incrementa(fin); i = incrementa(i)) {
                System.out.println(array[i]);
            }
        } else {
            System.out.println("La cola está vacía");
        }
    }

    public ColaLlamadas copiarCola(ColaLlamadas cola, int contador) {
        if (cola == null) {
            cola = new ColaLlamadas();
        }

        if (contador == this.fin + 1) {
            System.out.println("\nCola Original\n");
            this.mostrarCola(1);
            System.out.println("\nCola copiada\n");
            System.out.println("NUEVA_COLA\n");
            mostrarCola(0);
            return cola;
        }

        cola.encolar(this.array[contador]);
        return copiarCola(cola, ++contador);

    }

}