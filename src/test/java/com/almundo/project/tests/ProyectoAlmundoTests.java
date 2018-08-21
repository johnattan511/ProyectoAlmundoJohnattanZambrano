package com.almundo.project.tests;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.almundo.project.dispatcher.Dispatcher;
import com.almundo.project.factory.EmployeeFactory;
import com.almundo.project.runnable.ThreadEmployee;

/**
 * 
 * @author Johnattan
 *
 */
public class ProyectoAlmundoTests {
	
	private final int NUMERO_MAX;
	private Logger logger = Logger.getLogger(EmployeeFactory.class.getName());
	
	public ProyectoAlmundoTests(){
		
		Properties prop = new Properties();
		Dispatcher.class.getClassLoader();
		InputStream input = ClassLoader.getSystemResourceAsStream("prop.properties");
		
		try {
			prop.load(input);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error al cargar .properties", e);
		}
		
		NUMERO_MAX = Integer.parseInt(prop.getProperty("MAX_THREAD"));
	}

	
	/**
	 * Prueba encargada de valodar si los hilos finalizaron su ejecucion. En caso que exista algun error
	 * (mas de 20 segundos de vida en un hilo) se considera que existio un error en la ejecucion de
	 * alguno de los hilos. No se valida numero maximo de ocurrencias.
	 * @throws InterruptedException
	 */
	@Test
	public void test10Employees() throws InterruptedException {
		
		boolean salida = true;
		
		Thread[] hilos = new ThreadEmployee[10];

		for (int a = 0; a < hilos.length; a++) {
			hilos[a] = new ThreadEmployee();
		}

		for (int a = 0; a < hilos.length; a++) {
			hilos[a].start();
		}
		
		for (int a = 0; a < hilos.length; a++) {
			hilos[a].join(20000);
		}
		
		for (int a = 0; a < hilos.length; a++) {
			if(hilos[a].isAlive()){
				salida = false;
			}
		}
		
		assertTrue("Finalizacion exitosa Hilos", salida);

	}
	
	/**
	 * Test de prueba encargado de validar que el numero maximo de solicitudes permitidas no vaya
	 * a exceder el numero de hilos que se estan ejecutando. Esta no termina la solicitud del proceso
	 * solo valida lo referente a cantidad de hilos
	 * @throws InterruptedException
	 */
	@Test
	public void testNumberRequests() throws InterruptedException{
		
		boolean condition = true;
		
		Dispatcher.createRequests(15);
		if((Thread.activeCount()-1) > NUMERO_MAX){
			condition = false;
		}
		
		logger.warning("PROCESO FINALIZADO DE ACUERDO AL TEST REALIZADO");
		assertTrue(condition);
		
	}
	
}