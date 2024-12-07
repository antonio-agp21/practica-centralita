package es.uva.hilos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

public class Empleado {
	// Los empleados atenderán llamadas
	// En un orden de prioridad, si algún empleado de
	// prioridad más baja está disponible, este tendrá
	// que atender la llamada.

	private final int prioridad;
	private final String nombre;

	// Un logger es una mejor manera de hacer prints!
	private final static Logger logger = LoggerFactory.getLogger(Empleado.class);

	public Empleado(int prioridad, String nombre) {
		this.prioridad = prioridad;
		this.nombre = nombre;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public String getNombre() {
		return nombre;
	}

	// Simulamos que atender la llamada es algo esperando un tiempo
	public void atenderLlamada(Llamada llamada) throws InterruptedException {
		logger.info(nombre + " está atendiendo la llamada " + llamada.getId() + "...");
		try {
			TimeUnit.SECONDS.sleep(llamada.getDuracion());
		} catch (InterruptedException e) {

		}
		logger.info(nombre + " atendió la llamada " + llamada.getId());

	}

}
