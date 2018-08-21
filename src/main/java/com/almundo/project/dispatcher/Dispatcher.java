package com.almundo.project.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.almundo.project.business.EmployeeBusiness;
import com.almundo.project.factory.EmployeeFactory;
import com.almundo.project.runnable.ThreadEmployee;

/**
 * Clase Encargada de crear los Threads junto con la validacion de cantidad maxima 
 * disponible de la aplicacion.
 * @author Johnattan
 *
 */
public class Dispatcher {

	private static EmployeeBusiness employeesChain;
	private static Logger logger = Logger.getLogger(Dispatcher.class.getName());
	private static final int MAX_THREADS;

	/**
	 *  Carga del .properties con el numero maximo de Hilos recibidos concurrentemente
	 */
	static {

		employeesChain = EmployeeFactory.sortEmployeesChain();
		
		Properties prop = new Properties();
		Dispatcher.class.getClassLoader();
		InputStream input = ClassLoader.getSystemResourceAsStream("prop.properties");
		
		try {
			prop.load(input);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error al cargar .properties", e);
		}
		
		MAX_THREADS = Integer.parseInt(prop.getProperty("MAX_THREAD"));
		
		

	}

	/**
	 * Constructor privado del Dispatcher para evitar su inicializacion
	 */
	private Dispatcher() {

	}

	/**
	 * Metodo principal de inicio de la cadena de empleados
	 * @throws InterruptedException
	 */
	public static void dispatchCall() {

		try {
			employeesChain.takeCall();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.log(Level.SEVERE, "El hilo ha sido interrumpido", e);
		}

	}

	/**
	 * Metodo encargado de la creacion de hilos junto con la validacion de cantidad maxima.
	 * @param numThreads
	 */
	public static void createRequests(final int numThreads) {
		Thread[] hilos = new ThreadEmployee[numThreads];

		for (int a = 0; a < hilos.length; a++) {
			hilos[a] = new ThreadEmployee();
		}

		for (int a = 0; a < hilos.length; a++) {
			if (Thread.activeCount() > (MAX_THREADS)) {
				System.out.println("SE HA SOBREPASADO EL NUMERO MAXIMO DE USUARIOS, EL NUMERO MAXIMO DE USUARIOS ES = " + (MAX_THREADS));
				break;
			}
			hilos[a].start();
		}
	}
}
