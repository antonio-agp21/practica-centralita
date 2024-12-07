package es.uva.hilos;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Centralita {
	// Desde a la centralita pueden llegar llamadas que despues
	// se asignan a los empleados disponibles según prioridad

	// TODO: Harán falta más atriubutos ...
	private final ArrayList<Empleado> empleados = new ArrayList<>();
	private ArrayList<Empleado> ocupados = new ArrayList<>();
	private ColaLlamadas cola = new ColaLlamadas();
	private final static Logger logger = LoggerFactory.getLogger(Centralita.class);
	private ExecutorService executorService = Executors.newCachedThreadPool();; // Pool de tamaño variable de hilos

	public void conEmpleado(Empleado e) {

		synchronized (this) {
			empleados.add(e);
			logger.debug("Anadido el empleado " + e.getNombre());
			notifyAll();
		}

	}

	public Runnable atenderLlamadaConEmpleado(Empleado empleado, Llamada llamada) {
		// TODO: Obligatorio devolver un Runnable. Se recomienda utilizar
		// funciones lambda.
		return () -> {
			try {
				empleado.atenderLlamada(llamada);
			} catch (InterruptedException e) {
				logger.warn("El hilo fue interrumpido mientras el empleado " + empleado.getNombre()
						+ " atendía la llamada " + llamada.getId());

			}
			synchronized (this) {
				ocupados.remove(empleado);
				notifyAll(); // Notificar a todos los hilos en espera
			}

		};
	}

	public void atenderLlamada(Llamada llamada) {
		// TODO: Este método debería seleccionar un empleado disponible según prioridad
		// y correr en un nuevo hilo atenderLlamadaConEmpleado.
		// Este método no bloquea la ejecución si hay empleados disponibles para atender
		// la llamada
		// si no hay empleados disponibles tendremos que esperar a que haya uno.
		int indice = -1;
		cola.encolar(llamada);
		logger.info("Hemos recibido la llamada " + llamada.getId());
		synchronized (this) {

			while (ocupados.size() >= empleados.size()) {

				try {
					logger.debug("Esperando a que haya empleados disponibles");
					wait();
				} catch (InterruptedException e) {
				}
			}

			for (int i = 0; i < empleados.size(); i++) {
				if (!ocupados.contains(empleados.get(i))
						&& (indice == -1 || empleados.get(i).getPrioridad() < empleados.get(indice).getPrioridad()))
					indice = i;
			}

		}

		synchronized (this) {
			ocupados.add(empleados.get(indice));
			executorService.submit(atenderLlamadaConEmpleado(empleados.get(indice), cola.desencolar()));
		}
	}
}
